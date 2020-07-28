/**
 * ReadWriteLock 基础
 *
 * ReadWriteLock 是一个接口，ReentrantReadWriteLock 实现了这个接口
 *     readLock() - 获取读锁的 Lock 对象
 *     writeLock() - 获取写锁的 Lock 对象
 *
 *
 * 注：关于 Lock 的相关知识点请参见“concurrent.LockDemo1”, “concurrent.LockDemo2”, “concurrent.LockDemo3”
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo1 extends AppCompatActivity {

    private TextView _textView1;

    private ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();
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
                    writeMessage("read ok");
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
