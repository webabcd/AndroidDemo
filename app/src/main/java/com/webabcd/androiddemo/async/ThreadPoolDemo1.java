/**
 * ThreadPool 的基础
 *
 * Executors - 用于创建线程池
 *     newFixedThreadPool(int nThreads) - 创建一个指定大小的线程池
 *     newCachedThreadPool() - 创建一个无固定大小的线程池，如果池中线程超过 60 秒未被使用则会被移除
 *     newScheduledThreadPool(int corePoolSize) - 创建一个指定大小的线程池，支持定时执行任务或周期执行任务
 *     newSingleThreadExecutor() - 创建一个单线程线程池
 *     newSingleThreadScheduledExecutor() - 创建一个单线程线程池，支持定时执行任务或周期执行任务
 *         其实就是 newSingleThreadExecutor 和 newScheduledThreadPool 的结合体
 *
 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) - 创建自定义线程池（上面介绍的线程池其实都是通过这个类实现的）
 *     corePoolSize - 核心线程数，需要则创建，创建后就不会释放
 *     maximumPoolSize - 最大线程数，此值要大于等于 corePoolSize，比 corePoolSize 多出来的数就是允许的最大非核心线程数
 *     keepAliveTime - 非核心线程的空闲时间超过此值指定的时间后就会被释放
 *     unit - 用于指定 keepAliveTime 的时间单位
 *     workQueue - 任务队列
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;
    private Button _button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threadpooldemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);
        _button4 = (Button) findViewById(R.id.button4);
        _button5 = (Button) findViewById(R.id.button5);

        sampleFixedThreadPool();
        sampleCachedThreadPool();
        sampleScheduledThreadPool();
        sampleSingleThreadExecutor();

        sampleCustomThreadPool();
    }

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
            writeMessage(String.format("pool thread id:%d, task name:%s", Thread.currentThread().getId(), this.getName()));
        }
    }

    // 运行此示例可以发现，线程池中有 3 个线程来处理 10 个任务
    private void sampleFixedThreadPool() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个大小为 3 的线程池
                ExecutorService pool = Executors.newFixedThreadPool(3);

                // 创建需要处理的 10 个任务，并添加到线程池中
                for (int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();
            }
        });
    }

    // 运行此示例可以发现，线程池中有 10 个线程来处理 10 个任务（注：如果池中线程超过 60 秒未被使用则会被移除）
    private void sampleCachedThreadPool() {
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个无固定大小的线程池
                ExecutorService pool = Executors.newCachedThreadPool();

                // 创建需要处理的 10 个任务，并添加到线程池中
                for (int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();
            }
        });
    }

    // 运行此示例可以发现，延迟执行的任务和周期执行的任务
    private void sampleScheduledThreadPool() {
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个大小为 3 的线程池（支持定时执行任务或周期执行任务）
                ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

                // 创建需要处理的任务
                Thread t1 = new Thread(new MyThread("thread 1"));
                Thread t2 = new Thread(new MyThread("thread 2"));
                Thread t3 = new Thread(new MyThread("thread 3"));
                Thread t4 = new Thread(new MyThread("thread 4"));
                Thread t5 = new Thread(new MyThread("thread 5"));

                // 将指定的任务添加到线程池
                pool.execute(t1);
                pool.execute(t2);
                // 1 秒后执行
                pool.schedule(t3, 1000, TimeUnit.MILLISECONDS);
                // 1 秒后执行，然后每 2 秒执行一次
                pool.scheduleWithFixedDelay(t4, 1000, 2000, TimeUnit.MILLISECONDS);
                // 1 秒后执行，然后每 2 秒执行一次
                // 如果到了执行时间点，且本循环的上一个任务执行完毕了，则立即执行（此逻辑同 scheduleWithFixedDelay）
                // 如果到了执行时间点，且本循环的上一个任务没有执行完，则等待上一个任务执行完毕后再立即执行
                pool.scheduleAtFixedRate(t5, 1000, 2000, TimeUnit.MILLISECONDS);

                // shutdown()
                pool.shutdown();
            }
        });
    }

    // 运行此示例可以发现，线程池中只有 1 个线程来处理 10 个任务（任务按照 FIFO 顺序执行）
    private void sampleSingleThreadExecutor() {
        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个单线程线程池
                ExecutorService pool = Executors.newSingleThreadExecutor();

                // 创建需要处理的 10 个任务，并添加到线程池中
                for (int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();
            }
        });
    }

    // 自定义线程池（上面介绍的几个线程池其实都是通过 ThreadPoolExecutor 实现的）
    private void sampleCustomThreadPool() {
        _button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessage();

                // 创建一个 BlockingQueue 队列（实现了“生产者/消费者”模式，且线程安全）
                BlockingQueue<Runnable> bQueue = new LinkedBlockingQueue<Runnable>();

                // 创建自定义线程池
                // corePoolSize - 核心线程数，需要则创建，创建后就不会释放
                // maximumPoolSize - 最大线程数，此值要大于等于 corePoolSize，比 corePoolSize 多出来的数就是允许的最大非核心线程数
                // keepAliveTime - 非核心线程的空闲时间超过此值指定的时间后就会被释放
                // unit - 用于指定 keepAliveTime 的时间单位
                // workQueue - 任务队列
                ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, bQueue);

                // 创建需要处理的 10 个任务，并添加到线程池中
                for (int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new MyThread("thread " + i));
                    // 将指定的任务添加到线程池
                    pool.execute(thread);
                }

                // shutdown()
                pool.shutdown();
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
