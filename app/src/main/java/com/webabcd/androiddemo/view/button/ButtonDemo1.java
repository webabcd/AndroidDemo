/**
 * Button - 按钮控件（继承自 TextView）
 *     setClickable(boolean clickable) - 指定按钮是否可单击（如果要指定为 false 的话，需要在 setOnClickListener() 方法之后指定才会生效）
 *     setOnClickListener(OnClickListener l) - 指定单击事件的回调
 *
 *
 * 本例介绍 Button 的响应单击事件的方法
 */

package com.webabcd.androiddemo.view.button;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ButtonDemo1 extends AppCompatActivity implements OnClickListener {

    private Button _button2;
    private Button _button3;
    private Button _button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_button_buttondemo1);

        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);
        _button4 = (Button) findViewById(R.id.button4);

        sample();
    }

    private void sample() {
        // 内部类的方式响应 button 的单击事件（也可以把下面的 new 后的结果放到一个变量里，然后这里指定这个变量）
        _button2.setOnClickListener(new MyClickListener());

        // 匿名类的方式响应 button 的单击事件（也可以把下面的 new 后的结果放到一个变量里，然后这里指定这个变量）
        _button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ButtonDemo1.this, "button3 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 当前类的方式响应 button 的单击事件
        _button4.setOnClickListener(this);
    }

    // xml 的方式响应 button 的单击事件
    // 在 xml 中设置 button 的 android:onClick="onClick_fromXml" 就会执行到这里
    public void onClick_fromXml(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Toast.makeText(ButtonDemo1.this, "button1 clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    class MyClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(ButtonDemo1.this, "button2 clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4:
                Toast.makeText(ButtonDemo1.this, "button4 clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
