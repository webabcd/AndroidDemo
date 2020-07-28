/**
 * 演示 Thread 的 join 的用法
 *
 * t.join() 方法会阻塞调用此方法的线程，直到线程 t 完成后（或者达到了 join() 指定的超时时间后），此线程再继续
 */

package com.webabcd.androiddemo.async;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

public class ThreadDemo3 extends AppCompatActivity {

    private final String LOG_TAG = "ThreadDemo3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo3);

        sample1();
        sample2();
    }

    // 执行结果如下：
    // thread1 执行完了
    // sample1 执行完了
    private void sample1() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "thread1 执行完了");
            }
        });
        thread1.setDaemon(true);
        thread1.setName("thread1");
        thread1.start();

        try {
            thread1.join(); // 这里 sample1 线程会阻塞，等线程 thread1 执行完后再继续
        } catch (InterruptedException e) {

        }

        Log.d(LOG_TAG, "sample1 执行完了");
    }

    // 执行结果如下：
    // sample2 执行完了
    // thread2 执行完了
    private void sample2() {
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
                Log.d(LOG_TAG, "thread2 执行完了");
            }
        });
        thread2.setDaemon(true);
        thread2.setName("thread2");
        thread2.start();

        try {
            thread2.join(1000); // 这里 sample2 线程会阻塞，等待 1 秒后再继续（此时线程 thread2 还没有执行完）
        } catch (InterruptedException e) {

        }

        Log.d(LOG_TAG, "sample2 执行完了");
    }
}
