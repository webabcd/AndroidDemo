/**
 * Touch 在自定义控件中处理触摸事件
 *
 * 参见 view/input/TouchDemo4_CustomView.java（演示了如何在自定义控件内部监听 touch 事件）
 */

package com.webabcd.androiddemo.input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class TouchDemo4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo4);
    }
}
