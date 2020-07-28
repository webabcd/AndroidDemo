/**
 * ThreadPool 的关闭（线程池用于控制最大线程数，以及复用空闲线程）
 *
 * shutdown() - 将线程池的 isShutdown 状态设置为 true，不允许再向线程池中添加任务，等线程池中的所有任务都执行完毕后线程池就会被关闭
 * shutdownNow() - 将线程池的 isShutdown 状态设置为 true，不允许再向线程池中添加任务，未执行的任务不会再执行，调用每个线程的 interrupt() 方法，等线程池中的所有任务都执行完毕后线程池就会被关闭
 * awaitTermination() - 阻塞并等待指定的时间，然后判断线程池中的所有任务是否都执行完毕了，如果线程池中的所有任务都执行完毕了则 isTerminated 状态为 true
 *
 * 注：如果线程中有 io 阻塞的话，先要把这个 io 干掉
 */

package com.webabcd.androiddemo.async;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo2 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threadpooldemo2);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);

        sample_shutdown();
        sample_shutdownNow();
        sample_awaitTermination();
    }

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                writeMessage(String.format("pool thread id:%d, task name:%s", Thread.currentThread().getId(), this.getName()));
            } catch (InterruptedException e) {
                writeMessage(this.getName() + ": " + e.toString());
            }
        }
    }

    private void sample_shutdown() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个大小为 3 的线程池
                ExecutorService pool = Executors.newFixedThreadPool(3);

                // 创建需要处理的 5 个任务，并添加到线程池中
                for (int i = 0; i < 5; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();

                try {
                    // shutdown() 后如果再添加任务到线程池就会引发异常
                    pool.execute(new Thread(new MyThread("myThread")));
                }
                catch (Exception e) {
                    writeMessage(e.toString());
                }

                writeMessage("isShutdown: " + pool.isShutdown() + " isTerminated: " + pool.isTerminated());
            }
        });
    }

    private void sample_shutdownNow() {
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个大小为 3 的线程池
                ExecutorService pool = Executors.newFixedThreadPool(3);

                // 创建需要处理的 5 个任务，并添加到线程池中
                for (int i = 0; i < 5; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    thread.setName("task " + i);
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdownNow()（调用每个线程的 interrupt() 方法，未执行的任务不会再执行）
                pool.shutdownNow();

                // 获取 shutdownNow() 后未执行的任务列表（这些任务不会再执行）
                List<Runnable> list = pool.shutdownNow();
                for (Runnable runnable : list) {
                    Thread thread = (Thread) runnable;
                    writeMessage(thread.getName() + ": 未执行");
                }

                try {
                    // shutdownNow() 后如果再添加任务到线程池就会引发异常
                    pool.execute(new Thread(new MyThread("myThread")));
                }
                catch (Exception e) {
                    writeMessage("myThread: " + e.toString());
                }

                writeMessage("isShutdown: " + pool.isShutdown() + " isTerminated: " + pool.isTerminated());
            }
        });
    }

    private void sample_awaitTermination() {
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个大小为 3 的线程池
                final ExecutorService pool = Executors.newFixedThreadPool(3);

                // 创建需要处理的 5 个任务，并添加到线程池中
                for (int i = 0; i < 5; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();

                try {
                    // shutdown() 后如果再添加任务到线程池就会引发异常
                    pool.execute(new Thread(new MyThread("myThread")));
                }
                catch (Exception e) {
                    writeMessage(e.toString());
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 如果线程池中的所有任务都执行完毕了则 isTerminated 状态为 true
                        while (!pool.isTerminated()) {
                            try {
                                // 阻塞并等待指定的时间，返回值为 isTerminated 状态
                                boolean terminated = pool.awaitTermination(1, TimeUnit.SECONDS);
                                writeMessage("terminated: " + terminated);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        writeMessage("isShutdown: " + pool.isShutdown() + " isTerminated: " + pool.isTerminated());
                    }
                }).start();

                writeMessage("isShutdown: " + pool.isShutdown() + " isTerminated: " + pool.isTerminated());
            }
        });
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(String.format("%s\n", message));
            }
        });
    }

    private void clearMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.setText("");
            }
        });
    }
}
