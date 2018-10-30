/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的字体相关的使用
 */

package com.webabcd.androiddemo.view.text;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo2 extends AppCompatActivity {

    private TextView _textView6;
    private TextView _textView8;
    private TextView _textView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo2);

        _textView6 = (TextView) findViewById(R.id.textView6);
        _textView8 = (TextView) findViewById(R.id.textView8);
        _textView9 = (TextView) findViewById(R.id.textView9);

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
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(this, this.getResources(), R.font.myfont, "", Typeface.NORMAL);
        _textView6.setTypeface(typeface);

        // 指定字体样式（Typeface.DEFAULT 相当于 xml 中的 typeface="normal"；Typeface.NORMAL 相当于 xml 中的 textStyle="normal"）
        _textView8.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);

        // 指定字体样式的高级用法（如果你的字体不支持粗体斜体之类的话，就可以尝试下面这样做）
        TextPaint tp  = _textView9.getPaint();
        tp.setFakeBoldText(true); // 粗体
        tp.setTextSkewX(-0.5f); // 斜体，float 类型，负数表示右斜，整数表示左斜
        tp.setUnderlineText(true); // 下划线
        tp.setStrikeThruText(true); // 删除线
    }
}
