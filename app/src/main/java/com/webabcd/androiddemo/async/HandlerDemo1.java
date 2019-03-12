/**
 * Handler 的使用（Handler 用于在线程之间传递信息）
 *     本例演示后台线程向主线程发送信息
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
 * 通过 Handler 的 send 发送信息到 MessageQueue，以及接收 send 过来的信息
 *     sendMessage(Message msg) - 通过 send 发送 Message 对象
 *     sendMessageAtFrontOfQueue(Message msg) - 通过 send 发送 Message 对象，并将其放入 MessageQueue 队列的第一个
 *     sendMessageDelayed(Message msg, long delayMillis) - 通过 send 延迟指定的时间（相对于 SystemClock.elapsedRealtime）后发送 Message 对象
 *     sendMessageAtTime(Message msg, long uptimeMillis) - 通过 send 延迟指定的时间（相对于 SystemClock.uptimeMillis）后发送 Message 对象
 *     sendEmptyMessageDelayed(int what, long delayMillis) - 通过 send 延迟指定的时间（相对于 SystemClock.elapsedRealtime）后发送一个只有 what 值的空信息
 *     sendEmptyMessageAtTime(int what, long uptimeMillis) - 通过 send 延迟指定的时间（相对于 SystemClock.uptimeMillis）后发送一个只有 what 值的空信息
 *     通过重写 handleMessage(Message msg) 来处理 send 过来的信息
 *
 * 通过 Handler 的 post 发送信息到 MessageQueue，以及接收 post 过来的信息
 *     post(Runnable r) - 通过 post 发送消息，在指定的 Runnable（这个 Runnable 运行在 handler 所属线程）直接处理
 *     postAtFrontOfQueue(Runnable r) - 通过 post 发送消息，并将其放入 MessageQueue 队列的第一个，在指定的 Runnable（这个 Runnable 运行在 handler 所属线程）直接处理
 *     postDelayed(Runnable r, long delayMillis) - 通过 post 延迟指定的时间（相对于 SystemClock.elapsedRealtime）后发送消息，在指定的 Runnable（这个 Runnable 运行在 handler 所属线程）直接处理
 *     postAtTime(Runnable r, long uptimeMillis) - 通过 post 延迟指定的时间（相对于 SystemClock.uptimeMillis）后发送消息，在指定的 Runnable（这个 Runnable 运行在 handler 所属线程）直接处理
 *     postAtTime(Runnable r, Object token, long uptimeMillis) - token 用于信息的删除，其他和 postAtTime(Runnable r, long uptimeMillis) 一样
 *
 * 从 MessageQueue 移除指定的信息
 *     removeMessages(int what) - 移除指定 what 的 send 信息
 *     removeMessages(int what, Object object) - 移除指定 what 和 obj 的 send 信息
 *     removeCallbacks(Runnable r) - 移除 post 的指定 Runnable 的回调
 *     removeCallbacks(Runnable r, Object token) - 移除 post 的指定 Runnable 和 token 的回调
 *     removeCallbacksAndMessages(Object token) - 移除 post 的指定 token 的信息和回调
 *
 *
 * 注：
 * 1、主线程已经启动 Looper 了，不用再 Looper.prepare()/Looper.loop() 了
 * 2、新开线程需要自己写 Looper.prepare()/Looper.loop()/myLooper.quit()
 */

package com.webabcd.androiddemo.async;

import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class HandlerDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private TextView _textView2;
    private Button _button1;
    private Button _button2;

    private Handler _handler;

    private final int NORMAL_MESSAGE = 100;
    private final int DELAY_MESSAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_handlerdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // 在 UI 线程实例化 Handler（默认与 Looper.myLooper() 形成绑定关系，也可以与指定的 Looper 形成绑定关系）
        _handler = new MyHandler();
        // _handler = new MyHandler(looper);

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // 启动一个后台线程
                Thread myThread = new MyThread();
                myThread.setName("myThread");
                myThread.setDaemon(true);
                myThread.start();

                _textView1.setText("后台线程开始执行\n");
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                writeMessage(String.format("取消队列中的 what 值为 %d 的 send 信息的发送", DELAY_MESSAGE));
                // 取消队列中的 what 值为 200 的 send 信息的发送
                _handler.removeMessages(DELAY_MESSAGE);
            }
        });
    }

    // 自定义 Handler
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 本例的 Handler 是在 UI 线程上实例化的，所以这里的 handleMessage 也是在 UI 线程上执行的
            _textView1.append(String.format("收到通过 handler 的 send 发送的信息，what:%d, arg1:%d, arg2:%d, obj:%s\n", msg.what, msg.arg1, msg.arg2, msg.obj));
        }
    }

    // 自定义 Thread
    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                writeMessage("耗时任务开始执行");
                Thread.sleep(3 * 1000);
                writeMessage("耗时任务执行完毕");
            } catch (InterruptedException e) {

            }

            // 用 handler 的 obtainMessage() 来构造 Message 对象，而不是 new 一个。因为 obtainMessage() 会从 Message 池中拿一个不用的过来，这样会省去创建对象的开销
            Message msgNormal = _handler.obtainMessage();
            msgNormal.what = NORMAL_MESSAGE; // 信息类型（用于在 handleMessage 中区分不同类型的信息）
            msgNormal.arg1 = 1; // 自定义整型参数
            msgNormal.arg2 = 2; // 自定义整型参数
            msgNormal.obj = "信息 A"; // 具体的信息对象
            writeMessage(String.format("send 一个信息，what:%d, obj:%s", msgNormal.what, msgNormal.obj));
            // send 信息到 handler 所属线程
            _handler.sendMessage(msgNormal);

            Message msgDelay = _handler.obtainMessage();
            msgDelay.what = DELAY_MESSAGE;
            msgDelay.arg1 = 1;
            msgDelay.arg2 = 2;
            msgDelay.obj = "信息 B";
            writeMessage(String.format("延迟 3000 毫秒 send 一个信息，what:%d, obj:%s", msgDelay.what, msgDelay.obj));
            // 延迟 3000 毫秒后 send 信息到 handler 所属线程
            _handler.sendMessageDelayed(msgDelay, 3000);

            writeMessage("延迟 5000 毫秒 post 一个信息");
            // 延迟 5000 毫秒后 post 信息到 handler 所属线程
            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 这里是在 handler 所属线程执行的
                    _textView1.append("收到通过 handler 的 post 发送的信息\n");
                }
            }, 5000);
        }
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView2.append(String.format("%s\n", message));
            }
        });
    }
}