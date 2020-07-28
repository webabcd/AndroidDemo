/**
 * EditText - 文本编辑控件（继承自 TextView）
 *
 * 演示 EditText 的常用属性的使用
 */

package com.webabcd.androiddemo.view.text;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

import com.webabcd.androiddemo.R;

public class EditTextDemo1 extends AppCompatActivity {

    private EditText _editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_edittextdemo1);

        _editText1 = (EditText) findViewById(R.id.editText1);

        sample();
    }

    // 在 java 中设置 EditText 的常用属性（更多的说明参见 TextView 吧，因为 EditText 继承自 TextView）
    private void sample() {
        _editText1.setText("我是 EditText，我继承自 TextView");

        // 相当于 xml 中的 android:maxLength="100"
        _editText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
    }
}
