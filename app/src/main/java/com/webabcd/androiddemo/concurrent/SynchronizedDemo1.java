/**
 * synchronized 锁方法
 */

package com.webabcd.androiddemo.concurrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedDemo1 extends AppCompatActivity {

    private int _number = 0;
    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_lockdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    // 启 2 个线程对 _number 不停的做 +1 的操作，有锁则结果正确，无锁则结果错误
    private void sample() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(new MyRunnable());
        pool.execute(new MyRunnable());
        pool.shutdown();

        while (true) {
            try {
                boolean terminated = pool.awaitTermination(10, TimeUnit.MILLISECONDS);
                // 线程池中的线程都执行完毕后则退出此循环
                if (terminated) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _textView1.setText(String.valueOf(_number));
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                execTask();
            }
        }
    }

    // 锁方法
    private synchronized void execTask() {
        _number++;
    }
}
