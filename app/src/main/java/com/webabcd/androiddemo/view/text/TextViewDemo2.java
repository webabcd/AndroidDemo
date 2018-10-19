/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的字体相关的使用
 */

package com.webabcd.androiddemo.view.text;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo2 extends AppCompatActivity {

    private TextView _textView6;
    private TextView _textView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo2);

        _textView6 = (TextView) findViewById(R.id.textView6);
        _textView8 = (TextView) findViewById(R.id.textView8);

        sample();
    }

    private void sample() {
        // 指定自定义字体：方式一
        // 将字体文件保存到 assets 文件夹（例：assets/fonts/myfont.ttf），然后通过如下方式指定字体
        // Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myfont.ttf");
        // _textView6.setTypeface(typeface);

        // 指定自定义字体：方式二（推荐方式，因为这种方式也可以在 xml 中使用）
        // 将字体文件保存到类似这样的路径 res/font/myfont.ttf，然后通过如下方式指定字体
        @SuppressLint("RestrictedApi")
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(this, this.getResources(), R.font.myfont, "", 0);
        _textView6.setTypeface(typeface);

        // 指定字体样式（Typeface.DEFAULT 相当于 xml 中的 typeface="normal"）
        _textView8.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
    }
}
