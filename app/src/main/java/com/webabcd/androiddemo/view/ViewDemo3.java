/**
 * 演示布局文件中的 include 的用法
 */

package com.webabcd.androiddemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ViewDemo3 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_viewdemo3);

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
