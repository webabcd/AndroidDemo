/**
 * Switch - 状态切换控件
 *     setChecked(boolean checked) - 设置当前控件的选中状态
 *     isChecked() - 获取当前控件的选中状态
 *     setOnCheckedChangeListener(OnCheckedChangeListener listener) - 状态切换控件的选中状态发生变化时的回调
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class SwitchDemo1 extends AppCompatActivity {

    private Switch _switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_switchdemo1);

        _switch1 = (Switch)findViewById(R.id.switch1);

        sample();
    }

    private void sample() {
        _switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SwitchDemo1.this, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
