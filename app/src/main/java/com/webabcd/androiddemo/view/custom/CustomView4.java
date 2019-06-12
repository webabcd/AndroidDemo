/**
 * 演示自定义控件的自定义属性
 *
 * 属性定义在 res/values/attrs.xml
 */

package com.webabcd.androiddemo.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class CustomView4 extends View {

    private final String LOG_TAG = "CustomView4";

    // 对应 attrs.xml 中定义的枚举
    public static final int ENUM1 = 0;
    public static final int ENUM2 = 1;
    public static final int ENUM3 = 2;

    // 对应 attrs.xml 中定义的支持 flag 的枚举
    public static final int FLAG1 = 1;
    public static final int FLAG2 = FLAG1 << 1;
    public static final int FLAG3 = FLAG1 << 2;
    public static final int FLAG4 = FLAG1 << 3;
    public static final int FLAG5 = FLAG1 << 4;

    public CustomView4(Context context) {
        this(context, null);
    }

    public CustomView4(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取用户在 xml 端设置的自定义属性的值
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView4, 0, 0);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomView4_android_text:
                        Log.d(LOG_TAG, String.format("text: %s", a.getString(attr)));
                        break;
                    case R.styleable.CustomView4_attr01:
                        Log.d(LOG_TAG, String.format("attr01: %s", a.getString(attr)));
                        break;
                    case R.styleable.CustomView4_attr02:
                        Log.d(LOG_TAG, String.format("attr02: %d", a.getInteger(attr, 0)));
                        break;
                    case R.styleable.CustomView4_attr03:
                        Log.d(LOG_TAG, String.format("attr03: %b", a.getBoolean(attr, false)));
                        break;
                    case R.styleable.CustomView4_attr04:
                        Log.d(LOG_TAG, String.format("attr04: %f", a.getFloat(attr, 0f)));
                        break;
                    case R.styleable.CustomView4_attr05:
                        // 获取一个百分数类型属性的浮点值，第 2 个参数和第 3 个参数都传 1 即可
                        Log.d(LOG_TAG, String.format("attr05: %f", a.getFraction(attr, 1, 1, 0f)));
                        break;
                    case R.styleable.CustomView4_attr06:
                        // 颜色值是一个整型值，可以将其转换成一个十六进制字符串
                        Log.d(LOG_TAG, String.format("attr06: %s", Helper.int2Hex(a.getColor(attr, 0x00ff00))));
                        break;
                    case R.styleable.CustomView4_attr07:
                        // 获取尺寸类型的值，并将其转换为像素值
                        Log.d(LOG_TAG, String.format("attr07: %d", a.getDimensionPixelSize(attr, 0)));
                        break;
                    case R.styleable.CustomView4_attr08:
                        // 获取枚举类型的属性，一般来说要在 attrs.xml 端和 java 端分别定义
                        String attr08Result = "";
                        int enumValue = a.getInteger(attr, 0);
                        attr08Result += String.format("attr08: %d", enumValue);
                        if (enumValue == CustomView4.ENUM1) {
                            attr08Result += ", enum1";
                        }
                        else if (enumValue == CustomView4.ENUM2) {
                            attr08Result += ", enum2";
                        }
                        else if (enumValue == CustomView4.ENUM3) {
                            attr08Result += ", enum3";
                        }
                        Log.d(LOG_TAG, attr08Result);
                        break;
                    case R.styleable.CustomView4_attr09:
                        // 获取支持 flag 的枚举类型的属性，一般来说要在 attrs.xml 端和 java 端分别定义
                        String attr09Result = "";
                        int flagValue = a.getInteger(attr, 0);
                        attr09Result += String.format("attr09: %d", flagValue);
                        if (CustomView4.FLAG1 == (flagValue & CustomView4.FLAG1)) {
                            attr09Result += ", flag1";
                        }
                        if (CustomView4.FLAG2 == (flagValue & CustomView4.FLAG2)) {
                            attr09Result += ", flag2";
                        }
                        if (CustomView4.FLAG3 == (flagValue & CustomView4.FLAG3)) {
                            attr09Result += ", flag3";
                        }
                        if (CustomView4.FLAG4 == (flagValue & CustomView4.FLAG4)) {
                            attr09Result += ", flag4";
                        }
                        if (CustomView4.FLAG5 == (flagValue & CustomView4.FLAG5)) {
                            attr09Result += ", flag5";
                        }
                        Log.d(LOG_TAG, attr09Result);
                        break;
                    case R.styleable.CustomView4_attr10:
                        // 获取引用类型的值，实际上就是一个资源 id
                        Log.d(LOG_TAG, String.format("attr10: %d", a.getResourceId(attr, 0)));
                        break;
                    case R.styleable.CustomView4_attr11:
                        // 获取一个可支持多个类型的属性的值，需要自行判断是什么类型，以及要做好可能的异常处理
                        Log.d(LOG_TAG, String.format("attr11(string): %s", a.getString(attr)));
                        Log.d(LOG_TAG, String.format("attr11(reference): %d", a.getResourceId(attr, 0)));
                        break;
                    case R.styleable.CustomView4_myAttr01:
                        Log.d(LOG_TAG, String.format("myAttr01: %s", a.getString(attr)));
                        break;
                }
            }
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
