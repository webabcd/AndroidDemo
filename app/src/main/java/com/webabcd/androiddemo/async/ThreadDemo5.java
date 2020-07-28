/**
 * 演示 Thread 的异常处理
 */

package com.webabcd.androiddemo.async;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ThreadDemo5 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo5);

        _textView1 = (TextView) findViewById(R.id.textView1);

        // 在 run 内部 try catch
        sample1();

        // 捕获指定 thread 对象的未处理异常
        sample2();

        // 捕获全部 thread 对象的未处理异常
        sample3();
    }

    private void sample1() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 在 run 内部 try catch
                try {
                    int x = 10 / 0;
                } catch (Exception ex) {
                    writeMessage("thread1: " + ex.toString());
                }
            }
        });
        thread1.setDaemon(true);
        thread1.setName("thread1");
        thread1.start();
    }

    private void sample2() {
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 10 / 0;
            }
        });
        thread2.setDaemon(true);
        thread2.setName("thread2");
        // 捕获指定 thread 对象的未处理异常
        thread2.setUncaughtExceptionHandler(new MyThreadUncaughtExceptionHandler());
        thread2.start();

    }
    class MyThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            writeMessage("MyThreadUncaughtExceptionHandler " + t.getName() + ": " + e.toString());
        }
    }

    private void sample3() {
        // 捕获全部 thread 对象的未处理异常
        Thread.setDefaultUncaughtExceptionHandler(new GlobalThreadUncaughtExceptionHandler());

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 10 / 0;
            }
        });
        thread3.setDaemon(true);
        thread3.setName("thread3");
        thread3.start();
    }
    class GlobalThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            writeMessage("GlobalThreadUncaughtExceptionHandler " + t.getName() + ": " + e.toString());
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
