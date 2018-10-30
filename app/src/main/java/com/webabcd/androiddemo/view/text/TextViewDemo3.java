/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的阴影效果和图文效果
 */

package com.webabcd.androiddemo.view.text;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo3 extends AppCompatActivity {

    private TextView _textView2;
    private TextView _textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo3);

        _textView2 = (TextView) findViewById(R.id.textView2);
        _textView3 = (TextView) findViewById(R.id.textView3);

        sample1();
        sample2();
    }

    // 调整图片的位置和图片的大小
    private void sample1() {
        // 获取 TextView 的四个方向上的图片
        // 0 - 3 分别代表左上右下
        Drawable[] drawable = _textView2.getCompoundDrawables();

        // 修改 TextView 上侧的图片的大小和位置
        // setBounds() 函数的 4 个参数分别代表：左偏移量、上偏移量、宽、高
        drawable[1].setBounds(0, 0, 40, 20);

        // 设置 TextView 的四个方向上的图片
        _textView2.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
    }

    // 在 java 中设置图文效果，并为 png 图标着色
    private void sample2() {
        // 获取 Drawable 对象
        Drawable d1 = getResources().getDrawable(R.drawable.ic_expand_more).mutate();
        // 设置图片的位置和大小
        // setBounds() 函数的 4 个参数分别代表：左偏移量、上偏移量、宽、高
        d1.setBounds(0,0,32,32);
        // 为 png 图标着色
        d1.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        Drawable d2 = getResources().getDrawable(R.drawable.ic_expand_more);
        d2.setBounds(0,0,32,32);
        d2.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        // 设置 TextView 的四个方向上的图片
        Drawable[] drawables = _textView3.getCompoundDrawables();
        _textView3.setCompoundDrawables(d1, drawables[1], d2, drawables[3]);

        // 设置文本与四周图片间的间距
        _textView3.setCompoundDrawablePadding(0);
    }
}
