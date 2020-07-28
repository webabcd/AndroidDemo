/**
 * CheckBox - 复选框控件
 *     setButtonDrawable() - 设置复选框图片在各种状态下的样式
 *
 *
 * 本例主要介绍 CheckBox 的样式
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.webabcd.androiddemo.R;

public class CheckBoxDemo2 extends AppCompatActivity {

    private CheckBox _checkBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_checkboxdemo2);

        _checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        _checkBox3.setButtonDrawable(R.drawable.selector_checkbox_button);
    }
}
