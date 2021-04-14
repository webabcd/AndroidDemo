/*
 * 强引用 - 宁可 oom（out of memory）也不回收
 * 软引用 - 快 oom（out of memory）的时候将被回收
 * 弱引用 - 遇到 gc（garbage collection）就被回收
 */

package com.webabcd.androiddemo.optimize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Demo1 extends AppCompatActivity {

    private final String LOG_TAG = "optimize_Demo1";

    private Button _button1;
    private Button _button2;
    private Button _button3;

    private List<byte[]> _listStrong = new ArrayList<>();
    private List<SoftReference<byte[]>> _listSoft = new ArrayList<>();
    private List<WeakReference<byte[]>> _listWeak = new ArrayList<>();

    private Thread _threadStrong;
    private Thread _threadSoft;
    private Thread _threadWeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_demo1);

        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sampleStrong();
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sampleSoft();
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sampleWeak();
            }
        });

    }

    // 强引用的示例
    private void sampleStrong() {
        stopAllThread();

        _threadStrong = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        byte[] buffer = new byte[10 * 1024 * 1024];
                        _listStrong.add(buffer);

                        int countNull = 0;
                        int countObject = 0;
                        for (byte[] b : _listStrong) {
                            if (b == null) { // 为 null 则说明被回收了
                                countNull ++;
                            } else {
                                countObject ++;
                            }
                        }

                        // 强引用 - 宁可 oom（out of memory）也不回收
                        Helper.printMemoryLog(LOG_TAG);
                        Log.d(LOG_TAG, String.format(Locale.US, "强引用示例, 集合数据条数:%d, 有对象的条数:%d, 无对象的条数:%d", _listStrong.size(), countObject, countNull));

                        if (Thread.interrupted()) {
                            break;
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {

                }
            }
        });
        _threadStrong.setName("thread_strong");
        _threadStrong.setDaemon(true);
        _threadStrong.start();
    }

    // 软引用的示例
    private void sampleSoft() {
        stopAllThread();

        _threadSoft = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        byte[] buffer = new byte[10 * 1024 * 1024];
                        SoftReference<byte[]> bufferSoft = new SoftReference(buffer);
                        _listSoft.add(bufferSoft);

                        int countNull = 0;
                        int countObject = 0;
                        for (SoftReference<byte[]> sr : _listSoft) {
                            if (sr.get() == null) { // 为 null 则说明被回收了
                                countNull ++;
                            } else {
                                countObject ++;
                            }
                        }

                        // 软引用 - 快 oom（out of memory）的时候将被回收
                        Helper.printMemoryLog(LOG_TAG);
                        Log.d(LOG_TAG, String.format(Locale.US, "软引用示例, 集合数据条数:%d, 有对象的条数:%d, 无对象的条数:%d", _listSoft.size(), countObject, countNull));

                        if (Thread.interrupted()) {
                            break;
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {

                }
            }
        });
        _threadSoft.setName("thread_soft");
        _threadSoft.setDaemon(true);
        _threadSoft.start();
    }

    // 弱引用的示例
    private void sampleWeak() {
        stopAllThread();

        _threadWeak = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        byte[] buffer = new byte[10 * 1024 * 1024];
                        WeakReference<byte[]> bufferWeak = new WeakReference(buffer);
                        _listWeak.add(bufferWeak);

                        int countNull = 0;
                        int countObject = 0;
                        for (WeakReference<byte[]> wr : _listWeak) {
                            if (wr.get() == null) { // 为 null 则说明被回收了
                                countNull ++;
                            } else {
                                countObject ++;
                            }
                        }

                        // 弱引用 - 遇到 gc（garbage collection）就被回收
                        Helper.printMemoryLog(LOG_TAG);
                        Log.d(LOG_TAG, String.format(Locale.US, "弱引用示例, 集合数据条数:%d, 有对象的条数:%d, 无对象的条数:%d", _listWeak.size(), countObject, countNull));

                        if (Thread.interrupted()) {
                            break;
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {

                }
            }
        });
        _threadWeak.setName("thread_weak");
        _threadWeak.setDaemon(true);
        _threadWeak.start();
    }

    private void stopAllThread() {
        if (_threadStrong != null) {
            _threadStrong.interrupt();
            _threadStrong = null;
        }
        if (_threadSoft != null) {
            _threadSoft.interrupt();
            _threadSoft = null;
        }
        if (_threadWeak != null) {
            _threadWeak.interrupt();
            _threadWeak = null;
        }

        _listStrong.clear();
        _listSoft.clear();
        _listWeak.clear();
    }
}