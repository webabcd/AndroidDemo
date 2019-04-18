/**
 * ReentrantLock 实现了 Lock 接口，关于 Lock 的相关知识点请参见“concurrent.LockDemo1”, “concurrent.LockDemo2”, “concurrent.LockDemo3”
 *
 * ReentrantLock
 *     ReentrantLock(boolean fair) - 实例化 ReentrantLock 并指定其是否为公平锁，默认是非公平锁
 *         公平锁 - 按照请求锁的顺序来获取锁
 *         非公平锁 - 是否能获取到锁与请求锁的顺序无关
 *     isFair() - 是否是公平锁
 *     isLocked() - 是否持有锁
 *     hasQueuedThreads() - 锁等待队列中是否有线程在等待锁
 *     hasQueuedThread(Thread thread) - 指定的线程是否存在于锁等待队列中
 *     getQueueLength() - 锁等待队列中的等待线程数
 *     isHeldByCurrentThread() - 当前线程是否持有此锁
 *     getHoldCount() - 当前线程持有此锁的数量（是数量，而不是次数。比如嵌套锁时，当前线程持有此锁的数量就可能会大于 1）
 *         // 举个例子如下：
 *         try {
 *             _reentrantLock.lock();
 *             _reentrantLock.getHoldCount(); // 当前线程持有此锁的数量为 1
 *             try {
 *                 _reentrantLock.lock();
 *                 _reentrantLock.getHoldCount(); // 当前线程持有此锁的数量为 2
 *             } finally {
 *                 _reentrantLock.unlock();
 *             }
 *         } finally {
 *             _reentrantLock.unlock();
 *         }
 *     hasWaiters(Condition condition) - 是否有线程在等待指定的 condition
 *     getWaitQueueLength(Condition condition) - 返回等待指定的 condition 的线程数
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 extends AppCompatActivity {

    private TextView _textView1;

    private ReentrantLock _reentrantLock = new ReentrantLock(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_lockdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new MyThread("thread" + i);
            thread.setDaemon(true);
            thread.start();
        }
    }

    private class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                SystemClock.sleep(10);
                _reentrantLock.lock();
                try {
                    SystemClock.sleep(10);
                    writeMessage(String.format("%b, %b, %b, %d, %b, %d",
                            _reentrantLock.isFair(),
                            _reentrantLock.isLocked(),
                            _reentrantLock.hasQueuedThreads(),
                            _reentrantLock.getQueueLength(),
                            _reentrantLock.isHeldByCurrentThread(),
                            _reentrantLock.getHoldCount()));
                } catch (Exception ex) {

                } finally {
                    _reentrantLock.unlock();
                }
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
