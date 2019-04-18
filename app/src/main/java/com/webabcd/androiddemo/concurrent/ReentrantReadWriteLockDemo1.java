/**
 * ReentrantReadWriteLock 实现了 ReadWriteLock 接口，关于 ReadWriteLock 的相关知识点请参见“concurrent.ReadWriteLockDemo1”
 *     顾名思义，ReentrantReadWriteLock 是可重入读写锁，也就是说 ReentrantReadWriteLock 可以递归调用，即支持嵌套
 *
 * ReentrantReadWriteLock
 *     new ReentrantReadWriteLock(boolean fair) - 实例化 ReentrantReadWriteLock 并指定其是否为公平锁，默认是非公平锁
 *         公平锁 - 按照请求锁的顺序来获取锁
 *         非公平锁 - 是否能获取到锁与请求锁的顺序无关
 *     isFair() - 是否是公平锁
 *     getReadLockCount() - 所有线程持有读锁的数量
 *     getReadHoldCount() - 当前线程持有读锁的数量（嵌套锁时，此值可能会大于 1）
 *     getWriteHoldCount() - 当前线程持有写锁的数量（嵌套锁时，此值可能会大于 1）
 *     isWriteLocked() - 写锁是否被某一个线程持有
 *     isWriteLockedByCurrentThread() - 写锁是否被当前线程持有
 *     hasQueuedThreads() - 锁等待队列中是否有线程在等待锁
 *     hasQueuedThread(Thread thread) - 指定的线程是否存在于锁等待队列中
 *     getQueueLength() - 锁等待队列中的等待线程数
 *     hasWaiters(Condition condition) - 是否有线程在等待写锁的指定 condition
 *     getWaitQueueLength(Condition condition) - 返回等待写锁的指定 condition 的线程数
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo1 extends AppCompatActivity {

    private TextView _textView1;

    private ReentrantReadWriteLock _readWriteLock = new ReentrantReadWriteLock(true);
    private Lock _readLock = _readWriteLock.readLock();
    private Lock _writeLock = _readWriteLock.writeLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_readwritelockdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    /**
     * 读写锁就是读锁被持有时，可以读但不可以写；写锁被持有时，不可以读也不可以写
     */
    private void sample() {
        for (int i = 0; i < 10; i++) {
            Thread threadRead = new Thread(new Runnable() {
                @Override
                public void run() {
                    read();
                    writeMessage("read ok " + _readWriteLock.getReadLockCount());
                }
            });
            threadRead.setDaemon(true);
            threadRead.setName("threadRead" + i);
            threadRead.start();
        }

        for (int i = 0; i < 10; i++) {
            Thread threadWrite = new Thread(new Runnable() {
                @Override
                public void run() {
                    write();
                    writeMessage("write ok");
                }
            });
            threadWrite.setDaemon(true);
            threadWrite.setName("threadWrite" + i);
            threadWrite.start();
        }
    }

    private void read() {
        _readLock.lock();
        try {
            SystemClock.sleep(100);
        } finally {
            _readLock.unlock();
        }
    }

    private void write() {
        _writeLock.lock();
        try {
            SystemClock.sleep(100);
        } finally {
            _writeLock.unlock();
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
