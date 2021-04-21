/**
 * CheckBox - 复选框控件
 *     setChecked(boolean checked) - 设置当前复选框的选中状态
 *     isChecked() - 获取当前复选框的选中状态
 *     setOnCheckedChangeListener(OnCheckedChangeListener listener) - 复选框的选中状态发生变化时的回调
 *         手动使选中状态发生变化或用程序使选中状态发生变化都会触发此事件
 *         如果不希望用程序使选中状态发生变化时会触发事件，则请使用 setOnClickListener()
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class CheckBoxDemo1 extends AppCompatActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private CheckBox _checkBox1;
    private CheckBox _checkBox2;
    private Button _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_checkboxdemo1);

        _checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        _checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        _button1 = (Button) findViewById(R.id.button1);

        // 将 _checkBox2 设置为选中状态
        _checkBox2.setChecked(true);

        // 复选框的选中状态发生变化时的回调（手动使其发生变化或用程序使其发生变化都会触发此事件）
        _checkBox1.setOnCheckedChangeListener(this);

        // 复选框被单击时的回调（有的时候会用到这个，而不用 setOnCheckedChangeListener，比如你不希望用程序使选中状态发生变化时会触发事件）
        _checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckBoxDemo1.this, _checkBox2.getText() + " " + _checkBox2.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });

        _button1.setOnClickListener(this);
    }

    // 任何时候都可以通过此方法来获取复选框的选中状态
    @Override
    public void onClick(View v) {
        String result = "选中项 ";
        if (_checkBox1.isChecked()) {
            result += _checkBox1.getText() + " ";
        }
        if (_checkBox2.isChecked()) {
            result += _checkBox2.getText() + " ";
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    // 复选框的选中状态发生变化时的回调
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, buttonView.getText() + " " + isChecked, Toast.LENGTH_SHORT).show();
    }
}
