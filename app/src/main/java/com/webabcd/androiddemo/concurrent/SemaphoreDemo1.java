/**
 * Semaphore - 信号量
 *     Semaphore(int permits, boolean fair) - 实例化 Semaphore 并指定许可证数量，以及指定是否是公平许可证中心
 *         公平 - 出现阻塞后，按照申请的顺序来获取许可证
 *         非公平 - 出现阻塞后，获取许可证的顺序与申请许可证的顺序无关。默认值
 *     acquire(), acquire(int permits) - 申请一个许可证，或指定数量的许可证
 *     acquireUninterruptibly(int permits) - 申请指定数量的许可证，阻塞时不可被 interrupted
 *     boolean tryAcquire(), boolean tryAcquire(int permits) - 尝试申请一个或指定数量的许可证，不阻塞，如果获取到了许可证则返回 true
 *     boolean tryAcquire(long timeout, TimeUnit unit), boolean tryAcquire(int permits, long timeout, TimeUnit unit) - 尝试申请一个或指定数量的许可证，在超时时间之内如果获取到了许可证则返回 true
 *     release(), release(int permits) - 归还一个许可证，或指定数量的许可证
 *     availablePermits() - 获取当前许可证中心可用的许可证数量
 *     isFair() - 是否是公平许可证中心
 *     hasQueuedThreads() - 许可证申请队列中是否有线程正在等待
 *     getQueueLength() - 许可证申请队列中的等待线程数
 *
 *
 * 注：
 * 直译 Semaphore 的话不太好理解，可以将 Semaphore 理解为一个许可证中心，该许可证中心的许可证数量是有限的
 * 线程想要执行就要先从许可证中心获取一个许可证（如果许可证中心的许可证已经发完了，那就等着，等着其它线程归还许可证），执行完了再还回去
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo1 extends AppCompatActivity {

    private TextView _textView1;

    // 实例化一个许可证中心，该中心拥有的许可证数量为 5 个
    private Semaphore _semaphore = new Semaphore(5);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_semaphoredemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 申请许可证，如果申请不到则阻塞直到申请到为止
                        _semaphore.acquire();
                    } catch (InterruptedException e) {
                        // 在阻塞过程中，如果此线程被 interrupted 了则抛出此异常
                    }

                    SystemClock.sleep(random.nextInt(3000));
                    writeMessage(String.format("%s %d %d, ok", Thread.currentThread().getName(), _semaphore.availablePermits(), _semaphore.getQueueLength()));

                    // 归还许可证
                    _semaphore.release();
                }
            });
            thread.setName("thread" + i);
            thread.setDaemon(true);
            thread.start();
        }
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
