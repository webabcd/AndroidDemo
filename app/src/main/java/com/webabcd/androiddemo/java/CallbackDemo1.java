/**
 * 本例用于演示如何使用支持回调的类
 *
 * 定义回调接口参见：CallbackDemo1_CallbackInterface.java
 * 实现支持回调的类参见：CallbackDemo1_CallbackClass.java
 */

package com.webabcd.androiddemo.java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class CallbackDemo1 extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_callbackdemo1);

        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        CallbackDemo1_CallbackClass xxx = new CallbackDemo1_CallbackClass(new CallbackDemo1_CallbackInterface() {
            @Override
            public void ok() {
                mTextView1.setText("ok");
            }

            @Override
            public void error() {
                mTextView1.setText("error");
            }
        });
        xxx.execute();
    }
}

