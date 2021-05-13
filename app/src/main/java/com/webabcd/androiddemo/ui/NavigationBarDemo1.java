/**
 * 演示导航栏的相关操作
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Locale;

public class NavigationBarDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_navigationbardemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);

        sample();


        // 获取当前 activity 的根布局
        ViewGroup parent =  getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup root = (ViewGroup)parent.getChildAt(0);
        // 监听 view 的布局变化
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 注意：无论 navigationBar 是显示还是隐藏，它的高度都是不变的，但是根布局的高度是会变的
                Toast.makeText(NavigationBarDemo1.this, String.format(Locale.US, "navigationBarHeight:%d, layoutHeight:%d",
                        Helper.getNavigationBarHeight(NavigationBarDemo1.this), root.getHeight()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示 navigationBar
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 navigationBar（屏幕上有操作会重新显示）
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 navigationBar 的背景色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setNavigationBarColor(Color.RED);
                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 navigationBar 的前景色（第 1 种颜色）
                // 这个只有 2 种颜色，要么黑要么白
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改变 navigationBar 的前景色（第 2 种颜色）
                // 这个只有 2 种颜色，要么黑要么白
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        });
    }
}