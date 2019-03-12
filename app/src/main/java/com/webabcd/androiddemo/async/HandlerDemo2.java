/**
 * Handler 和 Looper 的使用（Handler 用于在线程之间传递信息）
 *     本例演示主线程向后台线程发送信息
 *
 *
 * Message - 用于封装需要传递的信息（一般通过 handler 的 obtainMessage() 来构造 Message 对象，而不是 new 一个。因为 obtainMessage() 会从 Message 池中拿一个不用的过来，这样会省去创建对象的开销）
 *     what - 信息类型（以便接收方区分不同类型的信息）
 *     arg1, arg2 - 自定义整型参数
 *     obj - 具体的信息对象
 * Handler - 与相关的 MessageQueue 和 Looper 形成绑定关系，有两个作用
 *     1、将信息发送到 Handler 所属线程的 MessageQueue
 *     2、接收 Looper 获取到的信息，以便在 Handler 所属线程进行处理
 * MessageQueue - 用于保存 Message 对象的消息队列
 * Looper - 线程的 MessageQueue 管理者，循环从 MessageQueue 获取消息，并交给 Handler 处理
 *
 *
 * 注：
 * 1、主线程已经启动 Looper 了，不用再 Looper.prepare()/Looper.loop() 了
 * 2、新开线程需要自己写 Looper.prepare()/Looper.loop()/myLooper.quit()
 */

package com.webabcd.androiddemo.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class HandlerDemo2 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;

    private Handler _handler;

    private final int MESSAGE_QUIT_LOOPER = -1;
    private final int MESSAGE_GET_MAINLOOPER = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_handlerdemo2);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        // 启动一个后台线程
        final Thread myThread = new MyThread();
        myThread.setName("myThread");
        myThread.setDaemon(true);
        myThread.start();

        // 主线程发送消息到后台线程
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 本例中，线程启动后在 Looper 循环阻塞，其状态是 RUNNABLE
                // 如果退出了后台线程的 Looper，则线程被释放，即线程的状态会变为 TERMINATED
                writeMessage("后台线程的状态: " + myThread.getState());

                Message msg = _handler.obtainMessage();
                msg.what = 0;
                msg.arg1 = 1;
                msg.arg2 = 2;
                msg.obj = "我是信息";

                // send 信息到 handler 所属线程
                _handler.sendMessage(msg);
            }
        });

        // 在后台线程获取主线程的 Looper，以便在主线程上执行逻辑
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeMessage("后台线程的状态: " + myThread.getState());

                // 可以发送一个特定的 what 值给后台线程，后台线程收到此值后通过获取主线程的 Looper 的方式，在主线程上执行逻辑
                _handler.sendEmptyMessage(MESSAGE_GET_MAINLOOPER);
            }
        });

        // 退出后台线程的 Looper
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 可以发送一个特定的 what 值给后台线程，后台线程收到此值后则退出后台线程的 Looper
                _handler.sendEmptyMessage(MESSAGE_QUIT_LOOPER);
            }
        });
    }

    // 自定义 Thread
    class MyThread extends Thread {
        @Override
        public void run() {
            // 初始化当前线程的 Looper 对象
            Looper.prepare();

            // 在当前线程实例化 Handler（默认与 Looper.myLooper() 形成绑定关系）
            _handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MESSAGE_QUIT_LOOPER) {
                        // 获取当前线程的 Looper 对象
                        Looper myLooper = Looper.myLooper();
                        if (myLooper !=  null){
                            // 退出 Looper 循环（如果不用此线程了，请退出线程的 Looper，否则 Looper 会阻塞着这个线程导致线程不会被释放）
                            // myLooper.quit() - 清空 MessageQueue 中的全部消息，并退出 Looper
                            // myLooper.quitSafely() - 将 MessageQueue 中的非延迟消息发出去，同时清空全部延迟消息，并退出 Looper（在 api level 18 或以上支持）
                            myLooper.quit();
                            writeMessage("looper quit");
                        }
                    }
                    else if (msg.what == MESSAGE_GET_MAINLOOPER) {
                        // 实例化 Handler 并与指定的 Looper 形成绑定关系（本例是与主线程的 Looper 形成绑定关系）
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                _textView1.append("通过在子线程获取主线程 Looper 的方式，实现在主线程运行逻辑的目的\n");
                            }
                        });
                    }
                    else {
                        // 判断当前线程是否是主线程
                        // Looper.getMainLooper() - 获取主线程的 Looper 对象
                        // Looper.myLooper() - 获取当前线程的 Looper 对象
                        boolean isMainThread = Looper.getMainLooper() == Looper.myLooper();

                        // 本例的 Handler 是在后台线程上实例化的，所以这里的 handleMessage 也是在后台线程上执行的
                        writeMessage(String.format("thread name:%s，isMainThread:%b，收到通过 handler 的 send 发送的信息，what:%d, arg1:%d, arg2:%d, obj:%s", Thread.currentThread().getName(), isMainThread, msg.what, msg.arg1, msg.arg2, msg.obj));
                    }
                }
            };

            // 启动 Looper，开始循环从 MessageQueue 获取消息。获取到了消息则提交给 handler 处理，获取不到消息就阻塞（此后如果有任何需要此线程执行的逻辑就发消息过来）
            Looper.loop();
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