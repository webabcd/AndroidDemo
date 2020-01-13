/**
 * Touch 简单的涂鸦板
 *
 *
 * 本例演示了如何实现一个简单的涂鸦板控件（参见 view/input/TouchDemo5_CustomView.java）
 */

package com.webabcd.androiddemo.input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class TouchDemo5 extends AppCompatActivity {

    private TouchDemo5_CustomView mCustomView1;

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo5);

        mCustomView1 = findViewById(R.id.customView1);
        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除涂鸦板控件中的内容
                mCustomView1.clear();
            }
        });
    }
}
