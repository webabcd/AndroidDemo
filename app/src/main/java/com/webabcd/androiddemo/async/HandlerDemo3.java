/**
 * Handler 导致的 Activity 内存泄漏
 *
 *
 * Message - 用于封装需要传递的信息（一般通过 handler 的 obtainMessage() 来构造 Message 对象，而不是 new 一个。因为 obtainMessage() 会从 Message 池中拿一个不用的过来，这样会省去创建对象的开销）
 *     what - 信息类型（以便接收方区分不同类型的信息）
 *     arg1, arg2 - 自定义整型参数
 *     obj - 具体的信息对象
 * Handler - 与相关的 MessageQueue 和 Looper 形成绑定关系，有两个作用
 *     1、将信息发送到 Handler 所属线程的 MessageQueue
 *     2、接收 Looper 获取到的信息，以便在 Handler 所属线程进行处理
 * MessageQueue - 用于保存 Message 对象的消息队列
 * Looper - 线程的 MessageQueue 管理者，循环从 MessageQueue 获取消息，并交给 Handler 处理
 *
 *
 * 注：测试时可以通过 DDMS 来观察 heap 的占用，需要 GC 的时候在 DDMS 中就可以手动 GC，DDMS 在我的环境中的位置在 C:\Android\sdk\tools\lib\monitor-x86_64\monitor.exe
 */

package com.webabcd.androiddemo.async;

import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.lang.ref.WeakReference;

public class HandlerDemo3 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;

    private byte[] mBuffer;

    private Handler mHandler1;
    private Handler mHandler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_handlerdemo3);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);

        // 开 10MB 的内存
        // 在 DDMS 观察内存的占用
        mBuffer = new byte[10 * 1024 * 1024];

        // 演示“Handler 导致的 Activity 内存泄漏”
        sample1();

        // 演示如何解决“Handler 导致的 Activity 内存泄漏”
        sample2();
    }

    private void sample1() {
        // 在 UI 线程实例化 Handler（默认与 Looper.myLooper() 形成绑定关系）
        mHandler1 = new MyHandler1();

        // 此例用于演示 Handler 引起的 Activity 内存泄漏
        // 进入页面后通过 DDMS 观察内存的占用，点击此按钮后，然后点击返回（销毁此 activity），然后手动 GC 后再观察内存的占用（内存未降，activity 未被 GC 回收）
        // 等待消息队列中的所有信息都被 looper 获取并被 handler 处理后（对于本例来说就是 60 秒后），再观察内存的占用（内存下降，activity 被 GC 回收了）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 发送一个延迟 60 秒的信息到消息队列，如果消息队列中存在未被处理的信息，则此 handler 中就会存在 activity 的引用
                        // 此时 activity 引用了 handler，而 handler 又引用了 activity
                        // 此时如果销毁了 activity，由于 handler 中存在 activity 的引用，所以 activity 是不会被 GC 回收的
                        // 当消息队列中的所有信息都被 looper 获取并被 handler 处理后，handler 中就不再有 activity 的引用了，此时 activity 就会被 GC 回收
                        mHandler1.sendMessageDelayed(mHandler1.obtainMessage(), 60 * 1000);
                    }
                }).start();
                mTextView1.setText("后台线程开始执行，请退出此页面后观察内存的泄漏情况（有泄漏）\n");
            }
        });
    }
    // 自定义 Handler（有内存泄漏）
    // 非静态内部类和匿名内部类，会隐式的持有其外部类的引用（对本例来说就是会持有 Activity 的引用）
    class MyHandler1 extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 因为通过非静态内部类或匿名内部类创建的线程会隐式持有其外部类（此例中就是 Activity），所以这里可以调用 Activity 中的控件
            mTextView1.append("收到通过 handler 的 send 发送的信息");
        }
    }


    private void sample2() {
        // 在 UI 线程实例化 Handler（默认与 Looper.myLooper() 形成绑定关系）
        mHandler2 = new MyHandler2(this);

        // 此例用于演示如何解决 Handler 引起的 Activity 内存泄漏
        // 进入页面后通过 DDMS 观察内存的占用，点击此按钮后，然后点击返回（销毁此 activity），然后手动 GC 后再观察内存的占用（内存下降，activity 被 GC 回收了）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 发送一个延迟 60 秒的信息到消息队列，如果消息队列中存在未被处理的信息，则此 handler 中就会显式的存在 activity 的弱引用（在 handler 中声明并赋值了 activity 的 week 弱引用）
                        // 此时 activity 引用了 handler，而 handler 又引用了 activity（弱引用）
                        // 此时如果销毁了 activity，由于 handler 中存在的 activity 引用是弱引用，所以 activity 是可以被 GC 回收的
                        mHandler2.sendMessageDelayed(mHandler2.obtainMessage(), 60 * 1000);
                    }
                }).start();
                mTextView1.setText("后台线程开始执行，请退出此页面后观察内存的泄漏情况（无泄漏）\n");
            }
        });
    }
    // 自定义 Handler（无内存泄漏）
    // 静态内部类，不会持有其外部类的引用（对本例来说就是不会持有 Activity 的引用，但是我们又需要用到 Activity，又不想引起 Activity 的内存溢出，此时就可以使用“弱引用”）
    static class MyHandler2 extends Handler {
        WeakReference<HandlerDemo3> mActivityReference;
        MyHandler2(HandlerDemo3 activity) {
            // 自定义实现 activity 的弱引用
            mActivityReference = new WeakReference<HandlerDemo3>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerDemo3 activity = mActivityReference.get();
            if (activity != null) {
                activity.mTextView1.append("收到通过 handler 的 send 发送的信息");
            }
        }
    }
}
