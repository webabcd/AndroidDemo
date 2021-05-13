/**
 * 演示沉浸式 UI
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Locale;

public class ImmersiveDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_immersivedemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();


        // 获取当前 activity 的根布局
        ViewGroup parent =  getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup root = (ViewGroup)parent.getChildAt(0);
        // 监听 view 的布局变化
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 注意：无论 statusBar/navigationBar 是显示还是隐藏，它们的高度都是不变的，但是根布局的高度是会变的
                Toast.makeText(ImmersiveDemo1.this, String.format(Locale.US, "statusBarHeight:%d, navigationBarHeight:%d, layoutHeight:%d",
                        Helper.getStatusBarHeight(ImmersiveDemo1.this), Helper.getNavigationBarHeight(ImmersiveDemo1.this), root.getHeight()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示 statusBar 和 navigationBar
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar 和 navigationBar（屏幕上有操作会重新显示）
                // View.SYSTEM_UI_FLAG_FULLSCREEN - 隐藏 statusBar
                // View.SYSTEM_UI_FLAG_HIDE_NAVIGATION - 隐藏 navigationBar
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar 和 navigationBar（屏幕上有操作也不会重新显示，通过手势从底部上滑时会重新显示）
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar 和 navigationBar（屏幕上有操作也不会重新显示，通过手势从底部上滑时会重新显示，且均为半透明显示，且不会影响当前布局，且过一会会自动隐藏）
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        });
    }
}