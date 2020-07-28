/**
 * 自定义主题，动态更换主题
 *
 *
 * 自定义的可根据主题来决定样式的属性的定义参见 res/values/attrs.xml
 * 自定义的主题参见 res/values/styles.xml
 *
 *
 * 自定义主题以及动态更换主题的说明
 * 1、在 attrs.xml 中定义属性的名字和类型
 * 2、在 styles.xml 中定义主题，设置在 attrs.xml 中定义的属性的样式
 * 3、在 activity 的 xml 中，将需要跟随主题变化的属性的值设置为在 attrs.xml 中定义的属性
 * 4、通过 setTheme() 指定主题，需要在 setContentView() 前指定才会生效
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ThemeDemo2 extends AppCompatActivity {

    static int _themeResourceId = R.style.ThemeDemo2Theme1;

    private TextView _textView1;
    private Button _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定 activity 的主题（需要在 setContentView() 之前）
        setTheme(_themeResourceId);

        // 指定 application 级别的主题
        // getApplication().setTheme(_themeResourceId);

        setContentView(R.layout.activity_ui_themedemo2);

        _textView1 = findViewById(R.id.textView1);
        _button1 = findViewById(R.id.button1);

        sample();
    }

    private void sample(){
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_themeResourceId == R.style.ThemeDemo2Theme1) {
                    _themeResourceId = R.style.ThemeDemo2Theme2;
                } else {
                    _themeResourceId = R.style.ThemeDemo2Theme1;
                }

                // 重新创建此 activity 以便更换后的主题生效
                recreate();
            }
        });
    }
}
