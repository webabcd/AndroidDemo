/**
 * 本例用于演示如何捕获未处理异常
 *
 * 自定义的未处理异常处理器请参见 MyUncaughtExceptionHandler.java
 */

package com.webabcd.androiddemo.optimize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class Demo4 extends AppCompatActivity {

    private Button _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_demo4);

        _button1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        // 触发一个异常
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt("abc");
            }
        });
    }
}