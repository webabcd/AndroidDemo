/**
 * NumberPicker - 滑动选择框
 *
 * 本例演示如何自定义 NumberPicker 的样式（需要继承 NumberPicker 以便实现自定义逻辑，详见：NumberPickerCustom.java）
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class NumberPickerDemo2 extends AppCompatActivity {

    // 自定义的 NumberPicker
    private NumberPickerCustom _numberPicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_numberpickerdemo2);

        _numberPicker1 = (NumberPickerCustom)findViewById(R.id.numberPicker1);

        sample();
    }

    private void sample() {
        _numberPicker1.setMinValue(0);
        _numberPicker1.setMaxValue(24);
        _numberPicker1.setValue(12);

        // 设置 NumberPicker 的分隔线的颜色
        _numberPicker1.setDividerColor(getResources().getColor(R.color.blue));
        // 设置 NumberPicker 的分隔线的粗细
        _numberPicker1.setDividerHeight(Helper.dp2px(this, 5));
    }
}
