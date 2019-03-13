/**
 * ThreadLocal 的使用
 *     用于在线程外创建一个只有本线程可以操作的变量（每个线程只能操作自己的变量）
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ThreadLocalDemo1 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threadlocaldemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>() {
        // 初始值
        @Override
        protected Object initialValue()
        {
            return "初始值";
        }
    };

    private class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }

        @Override
        public void run() {
            // threadLocal.get() - 获取本线程变量，如果之前没有为本线程变量赋值，则返回的是 initialValue() 指定的值
            writeMessage((String) threadLocal.get());
            // threadLocal.set() - 为本线程变量赋值
            threadLocal.set(super.getName());
            // threadLocal.get() - 获取本线程变量
            writeMessage((String) threadLocal.get());
            // threadLocal.remove() - 移除本线程变量（不再使用的时候一定要把它移除，否则会出现内存泄漏）。移除后如果又需要的话可以继续 set()/get()，然后记得不再使用的时候要 remove()
            threadLocal.remove();
        }
    }

    private void sample() {
        for (int i = 0; i < 10; i++) {
            MyThread thread = new MyThread("thread: " + i);
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
