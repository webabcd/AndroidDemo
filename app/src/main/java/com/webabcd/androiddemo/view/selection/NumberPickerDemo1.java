/**
 * NumberPicker - 滑动选择框
 *     setMinValue(int minValue) - 最小值
 *     setMaxValue(int maxValue) - 最大值
 *     setValue(int value) - 当前值
 *     setDisplayedValues(String[] displayedValues) - 设置 value 对应的显示文本
 *     setWrapSelectorWheel(boolean wrapSelectorWheel) - 选择项是否循环显示
 *         true - 选择项循环显示，也就是说不会滑动到头
 *         false - 选择项不会循环显示，也就是说可以滑动到头
 *     setDescendantFocusability() - 当前项的可编辑行为
 *         DatePicker.FOCUS_BEFORE_DESCENDANTS - 当前项可编辑，编辑后自动滚动到对应的位置，且当前项自动失去焦点
 *         DatePicker.FOCUS_AFTER_DESCENDANTS - 当前项可编辑，编辑后自动滚动到对应的位置，且当前项自动获得焦点
 *         DatePicker.FOCUS_BLOCK_DESCENDANTS - 当前项不可编辑
 *     setFormatter(Formatter formatter) - 格式化每一个选择项的显示内容
 *     setOnValueChangedListener(OnValueChangeListener onValueChangedListener) - 当前选择项发生变化时的回调
 *     setOnScrollListener(OnScrollListener onScrollListener) - 发生滚动时的回调
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class NumberPickerDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "NumberPickerDemo1";

    private NumberPicker _numberPicker1;
    private NumberPicker _numberPicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_numberpickerdemo1);

        _numberPicker1 = (NumberPicker)findViewById(R.id.numberPicker1);
        _numberPicker2 = (NumberPicker)findViewById(R.id.numberPicker2);

        // 滑动选择框的示例
        sample1();

        // 滑动选择框的选项显示文本的示例
        sample2();
    }

    private void sample1() {
        // 滑动选择框的最小值为 0，最大值为 24，当前选中值为 12
        _numberPicker1.setMinValue(0);
        _numberPicker1.setMaxValue(24);
        _numberPicker1.setValue(12);

        // 当前选择项可编辑
        _numberPicker1.setDescendantFocusability(DatePicker.FOCUS_BEFORE_DESCENDANTS);
        // 选择项循环显示
        _numberPicker1.setWrapSelectorWheel(true);

        // 格式化每一个选择项的显示内容
        _numberPicker1.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                if (value < 10) {
                    return "0" + value;
                } else {
                    return String.valueOf((value));
                }
            }
        });

        // 当前选择项发生变化时的回调
        _numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(NumberPickerDemo1.this, String.format("old:%d, new:%d", oldVal, newVal), Toast.LENGTH_SHORT).show();
            }
        });

        // 发生滚动时的回调
        _numberPicker1.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                switch (scrollState) {
                    case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                        Log.d(LOG_TAG, "SCROLL_STATE_FLING: 惯性滑动中（没有手触）");
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                        Log.d(LOG_TAG, "SCROLL_STATE_IDLE: 没有滑动");
                        break;
                    case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.d(LOG_TAG, "SCROLL_STATE_TOUCH_SCROLL: 手触滑动中");
                        break;
                }
            }
        });
    }


    private void sample2() {
        // 滑动选择框的选项显示文本的示例
        String[] cityList = {"北京", "上海", "广州", "深圳", "天津", "重庆"};
        _numberPicker2.setDisplayedValues(cityList);
        _numberPicker2.setMinValue(0);
        _numberPicker2.setMaxValue(cityList.length - 1);
        _numberPicker2.setValue(3); // 当前选中项为“深圳”

        // 当前选择项不可编辑
        _numberPicker2.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        // 选择项不会循环显示
        _numberPicker2.setWrapSelectorWheel(false);
    }
}
