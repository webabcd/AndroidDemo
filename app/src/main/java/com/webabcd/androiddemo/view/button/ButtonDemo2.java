/**
 * Button - 按钮控件（继承自 TextView）
 *
 *
 * 本例介绍 Button 的样式
 */

package com.webabcd.androiddemo.view.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ButtonDemo2 extends AppCompatActivity {

    private Button _button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_button_buttondemo2);

        _button3 = (Button) findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        // 将 button 设置为不可用
        _button3.setEnabled(false);

        // 设置 button 的各种状态下的背景样式（详见 drawable/selector_button_background.xml）
        _button3.setBackgroundResource(R.drawable.selector_button_background);

        // 设置 button 的各种状态下的文字颜色（详见 color/selector_button_textcolor.xml）
        _button3.setTextColor(getResources().getColorStateList(R.color.selector_button_textcolor));
    }
}
