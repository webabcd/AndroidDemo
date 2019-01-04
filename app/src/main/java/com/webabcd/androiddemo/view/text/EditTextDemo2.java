/**
 * EditText - 文本编辑控件（继承自 TextView）
 *
 * 演示 EditText 的选中和光标相关的知识点（获取焦点后自动选中全部内容，选中指定位置的内容，获取选中内容或光标的位置，隐藏光标，指定光标样式）
 */

package com.webabcd.androiddemo.view.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class EditTextDemo2 extends AppCompatActivity {

    private EditText _editText1;
    private EditText _editText2;
    private EditText _editText3;
    private EditText _editText4;
    private Button _button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_edittextdemo2);

        _editText1 = (EditText) findViewById(R.id.editText1);
        _editText2 = (EditText) findViewById(R.id.editText2);
        _editText3 = (EditText) findViewById(R.id.editText3);
        _editText4 = (EditText) findViewById(R.id.editText4);
        _button2 = (Button) findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        _editText1.setText("我是 EditText，我继承自 TextView");
        _editText2.setText("我是 EditText，我继承自 TextView");
        _editText3.setText("我是 EditText，我继承自 TextView");
        _editText4.setText("我是 EditText，我继承自 TextView");

        // 获取到焦点后自动选中全部内容
        _editText1.setSelectAllOnFocus(true);

        // 获取焦点
        _editText2.requestFocus();
        // 失去焦点
        // _editText2.clearFocus();
        // 选中指定位置的文本
        _editText2.setSelection(3,11);
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选中文本的起始位置（如果没有选中文本，则为当前光标所在位置）
                int selectionStart = _editText2.getSelectionStart();
                // 选中文本的结束位置（如果没有选中文本，则为当前光标所在位置）
                int selectionEnd = _editText2.getSelectionEnd();
                Toast.makeText(EditTextDemo2.this, String.format("selectionStart:%d, selectionEnd:%d", selectionStart, selectionEnd), Toast.LENGTH_SHORT).show();
            }
        });

        // 隐藏光标
        _editText3.setCursorVisible(false);
    }
}
