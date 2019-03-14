/**
 * Thread 基础
 *
 * Thread - 线程（实现了 Runnable 接口）
 *     setName() - 指定线程的名字
 *     start() - 启动线程
 *     setPriority() - 设置线程的优先级
 *         Thread.MIN_PRIORITY(1), Thread.NORM_PRIORITY(5), Thread.MAX_PRIORITY(10)
 *     setDaemon() - 指定是否为守护线程
 *         用户线程 - 主线程结束了，用户线程也不会退出，相当于前台线程，此值为默认值
 *         守护线程 - 主线程结束了，守护线程会自动退出，相当于后台线程
 *     getId(), getName()， getPriority()， isDaemon() - 获取线程的标识、名字、优先级、是否是守护进程
 *     isAlive() - 获取线程的存活状态（准备运行或者正在运行，则返回 true）
 *     getState() - 获取线程的状态（Thread.State 枚举）
 *         NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED - 具体说明参见 /res/drawable/img_thread_state.png 图例
 *     Thread.currentThread() - 获取当前 Thread 对象
 *     Thread.sleep() - 让当前 Thread 等待指定的时间
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ThreadDemo1 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private void sample()
    {
        // 通过继承 Thread 类的方式启动线程
        MyThread thread1 = new MyThread("thread1");
        thread1.setDaemon(true);
        thread1.start();

        // 通过实现 Runnable 接口的方式启动线程（注：Thread 实现了 Runnable 接口）
        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable, "thread2");
        thread2.setDaemon(true);
        thread2.start();

        // 通过 Thread 的匿名类的方式启动线程
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                writeMessage(String.format("线程%s（thread id: %d）启动了", Thread.currentThread().getName(), Thread.currentThread().getId()));
            }
        });
        thread3.setName("thread3");
        thread3.setDaemon(true);
        thread3.start();
    }

    private class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }

        @Override
        public void run() {
            writeMessage(String.format("线程%s（thread id: %d）启动了", super.getName(), super.getId()));
        }
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            writeMessage(String.format("线程%s（thread id: %d）启动了", Thread.currentThread().getName(), Thread.currentThread().getId()));
        }
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(String.format("%s（ui thread id: %d）\n", message, Thread.currentThread().getId()));
            }
        });
    }
}
