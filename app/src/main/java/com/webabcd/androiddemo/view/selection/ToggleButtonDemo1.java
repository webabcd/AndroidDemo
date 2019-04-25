/**
 * ToggleButton - 双状态按钮
 *     setChecked(boolean checked) - 设置当前双状态按钮的选中状态
 *     isChecked() - 获取当前双状态按钮的选中状态
 *     setTextOn() - 选中状态时，按钮上显示的文本
 *     setTextOff() - 未选中状态时，按钮上显示的文本
 *     setOnCheckedChangeListener(OnCheckedChangeListener listener) - 双状态按钮的选中状态发生变化时的回调
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.webabcd.androiddemo.R;

public class ToggleButtonDemo1 extends AppCompatActivity {

    private ToggleButton _toggleButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_togglebuttondemo1);

        _toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton1);

        sample();
    }

    private void sample() {
        _toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(ToggleButtonDemo1.this, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
