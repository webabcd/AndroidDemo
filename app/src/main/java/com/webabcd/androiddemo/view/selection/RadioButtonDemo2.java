/**
 * RadioGroup - 单选框组
 * RadioButton - 单选框
 *     setButtonDrawable() - 设置单选框图片在各种状态下的样式
 *
 *
 * 本例主要介绍 RadioButton 的样式
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import com.webabcd.androiddemo.R;

public class RadioButtonDemo2 extends AppCompatActivity {

    private RadioButton _radioButton3;
    private RadioButton _radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_radiobuttondemo2);

        _radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        _radioButton4 = (RadioButton)findViewById(R.id.radioButton4);

        sample();
    }

    private void sample() {
        _radioButton3.setButtonDrawable(R.drawable.selector_radiobutton_button);
        _radioButton4.setButtonDrawable(R.drawable.selector_radiobutton_button);
    }
}
