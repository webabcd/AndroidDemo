/**
 * TextViewMarquee - 允许一个页面内存在多个跑马灯的 TextView
 */

package com.webabcd.androiddemo.view.text;

import android.content.Context;
import android.util.AttributeSet;

// xml 中的 TextView 在编译时自动替换为 AppCompatTextView
// 代码中的 TextView 在 Android 8.0 系统及以上与 AppCompatTextView 相同
public class TextViewMarquee extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewMarquee(Context context) {
        super(context);
    }

    public TextViewMarquee(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextViewMarquee(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 告诉系统我是焦点，从而支持一个页面存在多个跑马灯 TextView
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}