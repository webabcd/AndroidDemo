/**
 * Thread 导致的内存泄漏
 *
 * 1、Thread 本身导致的内存泄漏：启动一个耗时的后台线程，此时如果退出 Activity 且不做其他处理，那么这个后台线程仍然会继续执行的，从而导致内存泄漏
 * 2、Thread 导致 Activity 的内存泄漏：除了第 1 条的说明外，如果此后台线程是通过非静态内部类或匿名内部类创建的，则因为其会隐式持有 Activity 从而导致 Activity 不会被 GC 回收以至发生内存泄漏
 *
 * 解决办法：
 * 1、解决 Thread 导致 Activity 的内存泄漏的问题：使用静态内部类创建线程（不会持有其外部类的引用），如果需要引用外部类则使用弱引用
 * 2、最好的解决办法就是当你退出 Activity 的时候关闭所有相关的 Thread，具体方法参见 ThreadDemo4.java 中的示例
 *
 * 注：
 * 1、对于其他异步方式（比如 ThreadPool, AsyncTask, Timer 等）也都会有类似的内存泄漏问题，解决方法是类似的
 * 2、测试时可以通过 DDMS 来观察 heap 的占用，需要 GC 的时候在 DDMS 中就可以手动 GC，DDMS 在我的环境中的位置在 C:\Android\sdk\tools\lib\monitor-x86_64\monitor.exe
 *
 *
 * 建议：
 * 1、对于与 Activity 无关的后台线程使用静态内部类创建，因为其不会持有其外部类的引用，不会导致其外部类的内存溢出
 * 2、对于与 Activity 有关的后台线程，在退出 Activity 的同时手动关闭后台线程
 */

package com.webabcd.androiddemo.async;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.lang.ref.WeakReference;

public class ThreadDemo6 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private byte[] mBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo6);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);

        // 开 10MB 的内存
        // 在 DDMS 观察内存的占用
        mBuffer = new byte[10 * 1024 * 1024];

        // 后台线程导致内存泄漏
        sample1();

        // 通过静态内部类的方式解决 Thread 导致 Activity 的内存泄漏的问题
        sample2();

        // 解决 Thread 的内存泄漏的最好的办法
        sample3();
    }

    // 单击按钮运行此示例，然后通过返回键退出此 Activity
    // 由于线程不会自动停止所以线程本身会内存泄漏，由于线程隐式持有 Activity 所以 Activity 不会被 GC 回收，所以 Activity 也会内存泄漏
    private void sample1() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 因为通过非静态内部类或匿名内部类创建的线程会隐式持有其外部类（此例中就是 Activity），所以这里可以运行 Activity 中的方法
                        writeMessage("后台线程运行中，60 秒后退出");
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }
        });
    }


    // 单击按钮运行此示例，然后通过返回键退出此 Activity
    // 由于线程不会自动停止所以线程本身会内存泄漏，而线程是采用静态内部类创建的，所以不会持有其外部类（在本例中就是不会持有 Activity）
    // 由于需要用到 Activity 所以本例用到了弱引用，也就是说 Activity 退出后是应该是可以被 GC 回收的（但是实测发现并不会被回收，不知道为什么，其实最好的解决办法还是本例中的第 3 个示例中介绍的方法）
    private void sample2() {
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Thread thread = new Thread(new MyRunnable2(ThreadDemo6.this));
                thread.setDaemon(true);
                thread.start();
            }
        });
    }
    // 静态内部类，不会持有其外部类的引用
    // 注：如果线程在某 activity 中是通过非静态内部类或匿名内部类创建的，则这个线程会隐式持有这个 activity
    static class MyRunnable2 implements Runnable {
        // 由于是静态内部类，所以不会持有外部类的引用，但是我们又需要用到 Activity，又不想 Thread 造成 Activity 的内泄漏，此时就可以用“弱引用”
        WeakReference<ThreadDemo6> mActivityReference;
        MyRunnable2(ThreadDemo6 activity) {
            mActivityReference = new WeakReference<ThreadDemo6>(activity);
        }

        @Override
        public void run() {
            // 退出 activity 后，如果 GC 了，那么 activity 就会被清理掉
            while (true) {
                Log.i("ThreadDemo6", mActivityReference.get() == null ? "activity 被销毁了" : "activity 还活着呢");

                if (mActivityReference.get() != null) {
                    SystemClock.sleep(1000);
                } else {
                    break;
                }
            }
        }
    }


    // 单击按钮运行此示例，然后通过返回键退出此 Activity
    // 主动关闭后台线程，线程本身不会内存泄漏，同时也不会导致 Activity 内存泄漏
    // 建议：
    // 1、对于与 Activity 无关的后台线程使用静态内部类创建，因为其不会持有其外部类的引用，不会导致其外部类的内存溢出
    // 2、对于与 Activity 有关的后台线程，在退出 Activity 的同时手动关闭后台线程
    private Thread mThread = null;
    private void sample3() {
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mThread != null) {
                    mThread.interrupt();
                }

                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writeMessage("后台线程运行中，60 秒后退出");
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mThread.setDaemon(true);
                mThread.start();
            }
        });
    }
    @Override
    public void onDestroy() {
        // 退出后主动关闭后台线程
        if (mThread != null) {
            mThread.interrupt();
        }

        super.onDestroy();
    }


    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView1.append(String.format("%s\n", message));
            }
        });
    }
}
