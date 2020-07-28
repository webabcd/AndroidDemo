/**
 * Activity 之间的跳转动画
 * 本例用于演示如何全局指定一个 activity 关闭后，从堆栈中打开之前的 activity 时的跳转动画
 *
 *
 * 一、全局指定
 * 关于全局指定 activity 之间的跳转动画请参见 res/values/styles.xml 的 MyTheme_MyActivityAnimation
 */

package com.webabcd.androiddemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ActivityDemo4_3 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全局指定跳转动画
        setTheme(R.style.MyTheme_MyActivityAnimation);

        setContentView(R.layout.activity_activity_activitydemo4_3);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
