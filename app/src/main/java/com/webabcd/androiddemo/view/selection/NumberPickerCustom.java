/**
 * 本例演示如何自定义 NumberPicker 的样式
 */

package com.webabcd.androiddemo.view.selection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class NumberPickerCustom extends NumberPicker {
    private final String LOG_TAG = "NumberPickerCustom";

    public NumberPickerCustom(Context context) {
        super(context);
    }

    public NumberPickerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    // 设置选择项的的文字颜色和文字大小
    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextColor(Color.parseColor("#ff0000"));
            ((EditText) view).setTextSize(14);
        }
    }

    // 通过反射设置 NumberPicker 的分隔线的颜色
    public void setDividerColor(@ColorInt int color) {
        Field field = null;
        try {
            field = NumberPicker.class.getDeclaredField("mSelectionDivider");
            if (field != null) {
                field.setAccessible(true);
                field.set(this, new ColorDrawable(color));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    // 通过反射设置 NumberPicker 的分隔线的粗细
    public void setDividerHeight(int height) {
        Field[] fields = NumberPicker.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals("mSelectionDividerHeight")) {
                f.setAccessible(true);
                try {
                    f.set(this, height);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.toString());
                }
                break;
            }
        }
    }
}
