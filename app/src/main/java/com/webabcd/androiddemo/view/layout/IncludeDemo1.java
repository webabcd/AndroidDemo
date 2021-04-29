/**
 * 演示如何通过 include 静态加载布局文件（参见 /res/layout/activity_view_layout_includedemo1.xml）
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class IncludeDemo1 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_includedemo1);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);

        sample();
    }

    private void sample() {
        mTextView1.setBackgroundColor(Color.RED);
        mTextView2.setBackgroundColor(Color.GREEN);
        mTextView3.setBackgroundColor(Color.BLUE);
    }
}
