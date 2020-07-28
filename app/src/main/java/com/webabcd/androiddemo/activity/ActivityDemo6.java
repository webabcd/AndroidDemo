/**
 * Activity 样式（隐藏状态栏，对话框样式）
 *
 * 本例演示如何隐藏 activity 的 statusBar
 * 关于对话框样式的 activity 请参见 activity/ActivityDemo6_2
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ActivityDemo6 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo6);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开对话框样式的 activity（activity/ActivityDemo6_2）
                Intent intent = new Intent(ActivityDemo6.this, ActivityDemo6_2.class);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示 statusBar
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });
    }
}
