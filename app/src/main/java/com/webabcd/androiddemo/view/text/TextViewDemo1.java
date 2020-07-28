/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的常用属性的使用
 */

package com.webabcd.androiddemo.view.text;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo1 extends AppCompatActivity {

    private TextView _textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo1);

        _textView5 = (TextView) findViewById(R.id.textView5);

        sample();
    }

    // 在 java 中设置 TextView 的常用属性
    private void sample() {
        _textView5.setTextColor(Color.BLUE);
        _textView5.setGravity(Gravity.CENTER);
        _textView5.setMaxLines(3);
        _textView5.setEllipsize(TextUtils.TruncateAt.END);
        /**
         * setText(CharSequence text) - 指定显示的文本
         * setText(int resid) - 指定显示的资源（会自动将资源转换为文本）
         */
        _textView5.setText(R.string.text_long);
        /**
         * TypedValue.COMPLEX_UNIT_PX - px
         * TypedValue.COMPLEX_UNIT_DIP - dp
         * TypedValue.COMPLEX_UNIT_SP - sp
         */
        _textView5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
    }
}
