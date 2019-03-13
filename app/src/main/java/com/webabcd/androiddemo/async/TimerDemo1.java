/**
 * Timer 和 TimerTask 的使用
 *     Timer 用于延迟或循环执行 TimerTask
 *     一个 Timer 对象只会有一个关联的 Thread
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;

    private Timer mTimer;
    private TimerTask mTimerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_timerdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                stopTimer();
                // Timer(boolean isDaemon) - 实例化 Timer
                //     isDaemon 设置为 false - 用户线程，主线程结束了，用户线程也不会退出，相当于前台线程，此值为默认值
                //     isDaemon 设置为 true - 守护线程，主线程结束了，守护线程会自动退出，相当于后台线程
                mTimer = new Timer(true);

                // schedule(TimerTask task, long delay) - 延迟指定的时间（单位：毫秒）后执行指定的 TimerTask 任务
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        writeMessage("delay");
                    }
                }, 1000);

                // 实例化一个 TimerTask
                mTimerTask = new TimerTask()
                {
                    @Override
                    public void run() {
                        writeMessage("period");
                    }
                };
                // schedule(TimerTask task, long delay, long period)
                //     延迟 delay 指定的时间（单位：毫秒）后执行 task 任务，并且每隔 period 指定的时间（单位：毫秒）后循环执行 task 任务
                //     注：period 间隔时间指的是相对于上一次 task 任务执行完成之后的时间
                // scheduleAtFixedRate(TimerTask task, long delay, long period)
                //     延迟 delay 指定的时间（单位：毫秒）后执行 task 任务，并且每隔 period 指定的时间（单位：毫秒）后循环执行 task 任务
                //     注：period 间隔时间指的尽量按此时间为周期循环执行 task 任务
                mTimer.schedule(mTimerTask, 0, 2000);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 停止计时器
                stopTimer();
            }
        });
    }

    // 停止计时器
    private void stopTimer() {
        if (mTimerTask != null)
        {
            // 把当前 TimerTask 任务从任务队列中取消
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null)
        {
            // 把当前任务队列中的所有 TimerTask 任务都取消
            mTimer.cancel();
            // 把当前任务队列中的所有被标记为取消的 TimerTask 任务的引用都设置为 null
            mTimer.purge();
            mTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        stopTimer();

        super.onDestroy();
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeString = df.format(new Date());

                _textView1.append(String.format("%s %s\n", timeString, message));
            }
        });
    }
}
