/**
 * 演示 Thread 的 interrupt() 的用法
 *
 * 1、关于停止线程的方法，如 stop(), destroy(), suspend(), resume() 都是有问题的，不要再用了
 * 2、需要停止线程的话请用 interrupt()，然后自己写相关的逻辑
 * 3、调用了线程的 interrupt() 后，分两种情况，一种是会触发 InterruptedException 异常，另一种则不会，参见下面的 sample1 和 sample2 示例
 * 4、注：如果线程中有 io 阻塞的话，先要把这个 io 干掉
 */

package com.webabcd.androiddemo.async;

import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

public class ThreadDemo4 extends AppCompatActivity {

    private final String LOG_TAG = "ThreadDemo4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo4);

        // 如果线程在调用 Object 的 wait() 方法，或者自己的 join(), sleep() 方法阻塞的过程中，调用了 interrupt() 方法，则会触发 InterruptedException 异常
        // 注：调用 interrupt() 方法后，线程的 interrupted 状态会被置为 true，而在触发了 InterruptedException 异常后，线程的 interrupted 状态会被置为 false
        sample1();

        // 非 wait(), join(), sleep() 阻塞中，调用了 interrupt() 方法，则不会触发 InterruptedException 异常，而是将线程的 interrupted 置为 true（只是设置这个状态而已，没有别的逻辑）
        // 此之后通过线程对象 thread.isInterrupted() 获取到的值为 true
        // 再之后第一次通过线程类 Thread.interrupted() 获取到的值为 true，与此同时会将 interrupted 置为 false
        // 再之后通过 thread.isInterrupted() 和 Thread.interrupted() 获取到的值均为 false
        //sample2();
    }

    // 本示例的执行结果如下：
    /**
     * thread1 isInterrupted:true
     * sample1 执行完了
     * thread1:java.lang.InterruptedException
     * thread1 isInterrupted: false
     * thread1 interrupted: false
     * thread1 执行完了
     */
    private void sample1() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // sleep 过程中被 interrupt() 则触发 InterruptedException 异常
                        // catch 到 InterruptedException 异常后退出线程即可
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(LOG_TAG, "thread1:" + e.toString());
                        // 触发了 InterruptedException 异常后，线程的 interrupted 状态会被置为 false
                        Log.d(LOG_TAG, "thread1 isInterrupted: " + Thread.currentThread().isInterrupted());
                        Log.d(LOG_TAG, "thread1 interrupted: " + Thread.interrupted());
                        break;
                    }
                }

                Log.d(LOG_TAG, "thread1 执行完了");
            }
        });
        thread1.setDaemon(true);
        thread1.setName("thread1");
        thread1.start();

        try {
            Thread.sleep(100);
            // 调用 thread1 的 interrupt() 方法后，thread1 的 interrupted 状态会被置为 true
            thread1.interrupt();
            Log.d(LOG_TAG, "thread1 isInterrupted:" + thread1.isInterrupted());
        } catch (InterruptedException e) {

        }

        Log.d(LOG_TAG, "sample1 执行完了");
    }

    // 本示例的执行结果如下：
    /**
     * sample2 执行完了
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 interrupted: false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 interrupted: false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:true // interrupt() 之后，isInterrupted 为 true
     * thread2 isInterrupted:true
     * thread2 isInterrupted:true
     * thread2 isInterrupted:true
     * thread2 interrupted: true //  interrupt() 之后，第一次 Thread.interrupted() 的值为 true，与此同时会将 interrupted 置为 false
     * thread2 isInterrupted:false // 再之后 thread.isInterrupted() 和 Thread.interrupted() 值均为 false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 interrupted: false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 interrupted: false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     * thread2 isInterrupted:false
     *  ......
     */
    private void sample2() {
        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 非 wait(), join(), sleep() 阻塞中，调用了 interrupt() 方法，则将线程的 interrupted 置为 true
                long timePrev = SystemClock.elapsedRealtime() / 1000;
                while (true) {
                    long timeCurrent = SystemClock.elapsedRealtime() / 1000;
                    if (timePrev != timeCurrent) {
                        // 在 interrupt() 之后
                        // thread.isInterrupted() 的值为 true
                        // 第一次 Thread.interrupted() 的值为 true，与此同时会将 interrupted 置为 false
                        // 再之后 thread.isInterrupted() 和 Thread.interrupted() 值均为 false
                        Log.d(LOG_TAG, "thread2 interrupted: " + Thread.interrupted()); // 通过 Thread.interrupted() 判断，如果是 true，则退出线程即可
                        timePrev = timeCurrent;
                    }
                }
            }
        });
        thread2.setDaemon(true);
        thread2.setName("thread2");
        thread2.start();

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(200);
                        if (i++ == 9) {
                            thread2.interrupt();
                        }
                    } catch (InterruptedException e) {

                    }

                    Log.d(LOG_TAG, "thread2 isInterrupted:" + thread2.isInterrupted());
                }
            }
        });
        myThread.setDaemon(true);
        myThread.setName("myThread");
        myThread.start();

        Log.d(LOG_TAG, "sample2 执行完了");
    }
}