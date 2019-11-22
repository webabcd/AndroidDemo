/**
 * 通过主题修改控件的默认样式
 */

package com.webabcd.androiddemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class ThemeDemo3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定 activity 的主题（需要在 setContentView() 之前）
        setTheme(R.style.ThemeDemo3Theme);

        setContentView(R.layout.activity_ui_themedemo3);
    }
}
