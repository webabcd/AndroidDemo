/**
 * Touch 在自定义控件中处理触摸事件；处理 Activity 的触摸事件
 *
 * 本例演示
 * 1、在自定义控件中处理触摸事件，参见 view/input/TouchDemo4_CustomView.java（演示了如何在自定义控件内部监听 touch 事件）
 * 2、处理 Activity 的触摸事件，参见本 activity 的 @Override boolean onTouchEvent()
 */

package com.webabcd.androiddemo.input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TouchDemo4 extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo4);

        mTextView1 = findViewById(R.id.textView1);
    }

    // 处理 activity 的触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTextView1.append("onTouchEvent");

        return true;
    }
}
