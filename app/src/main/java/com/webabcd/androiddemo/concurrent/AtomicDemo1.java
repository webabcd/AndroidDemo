/**
 * atomic 原子操作
 *
 * 在 java.util.concurrent.atomic 包内有很多支持多线程场景下的原子操作的类，比如 AtomicBoolean, AtomicInteger, AtomicLong, AtomicReference 等，下面只简单介绍用法，详细的看文档吧
 */

package com.webabcd.androiddemo.concurrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicDemo1 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_atomicdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample1();
        sample2();
    }

    // AtomicInteger - 支持原子操作的整型（懒得写多线程下的示例了，这里只演示简单用法）
    private void sample1() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.set(100);
        int result1 = atomicInteger.getAndAdd(100);
        int result2 = atomicInteger.get();

        _textView1.append(String.format("%d, %d", result1, result2));
        _textView1.append("\n");
    }

    // AtomicReference - 支持原子操作的引用类型（懒得写多线程下的示例了，这里只演示简单用法）
    private void sample2() {
        AtomicReference<String> atomicReference = new AtomicReference<String>();
        atomicReference.set("webabcd");
        String result = atomicReference.get();

        _textView1.append(result);
    }
}
