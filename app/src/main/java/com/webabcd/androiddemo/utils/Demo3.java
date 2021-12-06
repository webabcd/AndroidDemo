/**
 * 演示如何监听 logcat 日志
 */

package com.webabcd.androiddemo.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Demo3 extends AppCompatActivity {

    private final String LOG_TAG = "utils_Demo3";

    private TextView _textView1;
    private Thread _thread1;
    private Thread _thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils_demo3);

        _textView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        // 监听 logcat 日志
        _thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Process process = null;
                BufferedReader reader = null;
                try {
                    // 定义 logcat 命令
                    String[] cmd = new String[] { "logcat", "-v time *:E" };
                    // 执行指定的命令
                    process = Runtime.getRuntime().exec(cmd);
                    // 获取响应的数据
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (_thread1.isInterrupted()) {
                            break;
                        }

                        writeMessage(line);
                    }
                } catch (Exception ex) {
                    Log.e(LOG_TAG, ex.toString());
                }
            }
        });
        _thread1.start();

        // 循环写入 logcat 日志
        _thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (_thread1.isInterrupted()) {
                        break;
                    }

                    Log.e(LOG_TAG, "it is a test");
                    SystemClock.sleep(1000);
                }
            }
        });
        _thread2.start();
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(message + "\n");
                int offset = _textView1.getLineCount() * _textView1.getLineHeight();
                if (offset > (_textView1.getHeight())) {
                    _textView1.scrollTo(0, offset - _textView1.getHeight());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (_thread1 != null) {
            _thread1.interrupt();
        }
        if (_thread2 != null) {
            _thread2.interrupt();
        }

        super.onDestroy();
    }
}