/**
 * RadioGroup - 单选框组
 *     setOnCheckedChangeListener(OnCheckedChangeListener listener) - 组内的选中状态发生变化时的回调
 *     getChildCount() - 组内单选框的数量
 *     getChildAt(int index) - 获取组内指定索引位置的单选框对象
 *     clearCheck() - 将组内的每个单选框都设置为未选中状态
 * RadioButton - 单选框
 *     setChecked(boolean checked) - 设置当前单选框的选中状态
 *     isChecked() - 获取当前单选框的选中状态
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class RadioButtonDemo1 extends AppCompatActivity {

    private RadioGroup _radioGroup1;
    private RadioButton _radioButton1;
    private RadioButton _radioButton2;
    private Button _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_radiobuttondemo1);

        _radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
        _radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        _radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        _button1 = (Button)findViewById(R.id.button1);

        // 将 _radioButton2 设置为选中状态（组内其他单选框会自动设置为未选中状态）
        _radioButton2.setChecked(true);

        sample();
    }

    private void sample() {
        // 单选框组内的选中状态发生变化时
        _radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId 为被选中的单选框的 id
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), "组内的选中状态发生变化，现在的选中项为：" + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // 任何时候都可以通过此方法来获取指定的单选框组内每个单选框的选中状态
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < _radioGroup1.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) _radioGroup1.getChildAt(i);
                    if (radioButton.isChecked()) {
                        Toast.makeText(getApplicationContext(), "现在的选中项为：" + radioButton.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }
}
