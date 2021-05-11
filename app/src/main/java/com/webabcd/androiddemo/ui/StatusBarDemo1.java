/**
 * 演示状态栏的相关操作
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class StatusBarDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_statusbardemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);

        sample();

        // 状态栏全透明
        setStatusBarTransparent();
        // 当前布局与状态栏重合，状态栏在前，当前布局在后
        // setFitSystemWindow(false);
        // 当前布局与状态栏重合，状态栏在前，当前布局在后（但是当前布局的内容会根据状态栏的高度自动下移）
        setFitSystemWindow(true);

        // 获取状态栏的高度（单位是 px）
        int statusBarHeight = 0;
        int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        Toast.makeText(this, "状态栏的高度：" + statusBarHeight, Toast.LENGTH_LONG).show();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示 statusBar
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 statusBar 的背景色
                getWindow().setStatusBarColor(getResources().getColor(R.color.green));
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 statusBar 的前景色（第 1 种颜色）
                // 这个只有 2 种颜色，要么黑要么白
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 statusBar 的前景色（第 2 种颜色）
                // 这个只有 2 种颜色，要么黑要么白
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        });
    }

    protected void setStatusBarTransparent() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private View _contentViewGroup;
    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (_contentViewGroup == null) {
            _contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        _contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }
}