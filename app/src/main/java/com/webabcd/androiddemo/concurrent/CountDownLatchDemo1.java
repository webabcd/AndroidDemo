/**
 * CountDownLatch - 通过信号数量实现线程同步
 *     new CountDownLatch(int count) - 实例化 CountDownLatch 并指定初始信号数量
 *     getCount() - 获取当前的信号数量
 *     countDown() - 减少 1 个信号
 *     await() - 阻塞当前线程，直到信号数量变为 0
 *     boolean await(long timeout, TimeUnit unit) - 阻塞当前线程，直到信号数量变为 0（可以指定超时时间）。如果在超时时间之内，信号数量变为 0 了则返回 true
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo1 extends AppCompatActivity {

    private TextView _textView1;

    // 初始信号数量为 10 个
    private CountDownLatch _countDownLatch = new CountDownLatch(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_countdownlatchdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(random.nextInt(3000));
                    writeMessage(String.format("%s %d ok", Thread.currentThread().getName(), _countDownLatch.getCount()));

                    // 减少 1 个信号
                    _countDownLatch.countDown();
                }
            });
            thread.setName("thread" + i);
            thread.setDaemon(true);
            thread.start();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞当前线程，直到信号数量变为 0（在 5000 毫秒内如果信号数量变为 0 则返回 true）
                    boolean ok = _countDownLatch.await(5000, TimeUnit.MILLISECONDS);
                    writeMessage(String.format("%s %d %b", Thread.currentThread().getName(), _countDownLatch.getCount(), ok));
                } catch (InterruptedException e) {
                    // 在阻塞过程中，如果此线程被 interrupted 了则抛出此异常
                }
            }
        });
        thread.setName("myThread");
        thread.setDaemon(true);
        thread.start();
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(String.format("%s\n", message));
            }
        });
    }
}
