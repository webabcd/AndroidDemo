/**
 * Activity 之间的跳转动画（本例用于演示一个 activity 打开另一个 activity 时的跳转动画）
 *
 *
 * 一、单独指定
 * 在 startActivity() 之后通过 overridePendingTransition(int enterAnim, int exitAnim) 指定跳转动画，假设是从 A 跳转到 B，那么
 *     enterAnim - B 的出现动画的资源 id（传 0 代表无动画）
 *     exitAnim - A 的消失动画的资源 id（传 0 代表无动画）
 *
 *
 * 二、全局指定
 * 关于全局指定 activity 之间的跳转动画请参见 res/values/styles.xml
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ActivityDemo4 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo4);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDemo4.this, ActivityDemo4_2.class));

                overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
            }
        });
    }
}
