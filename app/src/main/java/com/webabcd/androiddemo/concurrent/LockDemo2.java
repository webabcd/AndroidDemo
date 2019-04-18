/**
 * Lock（可 interrupt 的 Lock 的使用）
 *
 * Lock 是一个接口，ReentrantLock 实现了这个接口
 *     lockInterruptibly() - 在线程获取锁的过程中，如果线程被 interrupt() 则抛出 InterruptedException 异常
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo2 extends AppCompatActivity {

    private TextView _textView1;

    private Lock _lock = new ReentrantLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_lockdemo2);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    /**
     * 本例（使用 lockInterruptibly() 时）的运行结果类似如下：
     * ready（thread1）
     * running（thread1）
     * ready（thread2）
     * interrupted from thread（thread1）
     * interrupted from _lock（thread2）
     *
     * 本例（把 lockInterruptibly() 改成 lock() 时）的运行结果类似如下：
     * ready（thread1）
     * running（thread1）
     * ready（thread2）
     * interrupted from thread（thread1）
     * running（thread2）
     * interrupted from thread（thread2）
     */
    private void sample() {
        Thread thread1 = new Thread(new MyRunnable(), "thread1");
        thread1.setDaemon(true);
        thread1.start();

        Thread thread2 = new Thread(new MyRunnable(), "thread2");
        thread2.setDaemon(true);
        thread2.start();

        // 用于保证上面的 2 个线程都起来了
        SystemClock.sleep(1000);

        thread1.interrupt();
        thread2.interrupt();
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                execTask();
            } catch (InterruptedException e) {
                // 在通过 lockInterruptibly() 获取锁的过程中，线程被 interrupt() 了
                writeMessage("interrupted from lock");
            }
        }

        private void execTask() throws InterruptedException {
            writeMessage("ready");
            _lock.lockInterruptibly(); // 可以把此处改成 _lock.lock(); 之后再运行一下，以便对比一下区别
            try {
                writeMessage("running");
                Thread.sleep(3000);
                writeMessage("complete");
            } catch (InterruptedException e) {
                writeMessage("interrupted from thread");
            } finally {
                _lock.unlock();
            }
        }
    }

    private void writeMessage(final String message) {
        final String threadName = Thread.currentThread().getName();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(String.format("%s（%s）\n", message, threadName));
            }
        });
    }
}
