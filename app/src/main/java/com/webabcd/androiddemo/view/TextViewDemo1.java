/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的常用属性的使用
 */

package com.webabcd.androiddemo.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo1 extends AppCompatActivity {

    private TextView _textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_textview_demo1);

        _textView6 = (TextView) findViewById(R.id.textView6);

        sample();
    }

    // 在 java 中设置 TextView 的常用属性
    private void sample() {
        // 将字体文件保存到 assets 文件夹，然后通过如下方式指定字体
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myfont.ttf");
        _textView6.setTypeface(typeface);

        _textView6.setTextColor(Color.BLUE);
        _textView6.setGravity(Gravity.CENTER);
        _textView6.setText(R.string.text_short);
        /**
         * TypedValue.COMPLEX_UNIT_PX - px
         * TypedValue.COMPLEX_UNIT_DIP - dp
         * TypedValue.COMPLEX_UNIT_SP - sp
         */
        _textView6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
    }
}
