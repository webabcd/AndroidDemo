/**
 * 单例模式的调用示例
 */

package com.webabcd.androiddemo.designpattern;

import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.designpattern.singleton.SingletonPattern;

public class Singleton extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpattern_singleton);

        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        mTextView1.append(String.valueOf(SingletonPattern.getInstance().getTimestamp()));

        SystemClock.sleep(100);
        mTextView1.append("\n");

        mTextView1.append(String.valueOf(SingletonPattern.getInstance().getTimestamp()));
    }
}
