/**
 * 颜色和不透明度
 */

package com.webabcd.androiddemo.ui;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ColorDemo1 extends AppCompatActivity {

    private TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_colordemo1);

        mTextView3 = findViewById(R.id.textView3);

        sample();
    }

    private void sample() {
        // Color.argb() - 通过指定 argb 的方式创建颜色对象
        mTextView3.setTextColor(Color.argb(0xff, 0xff, 0x00, 0x00));

        // 从资源文件中获取颜色值
        mTextView3.setBackgroundColor(getResources().getColor(R.color.blue));
    }
}