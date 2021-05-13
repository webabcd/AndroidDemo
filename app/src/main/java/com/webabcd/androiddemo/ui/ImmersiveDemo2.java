/**
 * 演示沉浸式 UI（关于 statusBar 和 navigationBar 的常用效果）
 *
 *
 * 注：在本页面连续点击不同的按钮查看不同的效果时可能会有问题，如果想要查看不同的效果的话，请先退出此页面，然后再进来，然后再点击想要查看的效果
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ImmersiveDemo2 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_immersivedemo2);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 statusBar 和 navigationBar（屏幕上有操作也不会重新显示，通过手势从底部上滑时会重新显示，且均为半透明显示，且不会影响当前布局，且过一会会自动隐藏）
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // statusBar 透明，且使用根布局的背景，且不遮挡根布局的内容，navigationBar 黑色
                setStatusBarColor(Color.parseColor("#00000000"), false);
                setNavigationBarColor(Color.parseColor("#ff000000"), false);
                setFitSystemWindow(true);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // statusBar 和 navigationBar 均半透明，且浮在根布局的前面
                setStatusBarColor(Color.parseColor("#8800ff00"), false);
                setNavigationBarColor(Color.parseColor("#8800ff00"), false);
                setFitSystemWindow(false);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // statusBar 透明，且使用根布局的背景，且不遮挡根布局的内容，navigationBar 隐藏（通过手势从底部上滑时会重新显示，且为半透明显示，且不会影响当前布局，且过一会会自动隐藏）
                setStatusBarColor(Color.parseColor("#00000000"), false);
                getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                setFitSystemWindow(true);
            }
        });
    }

    /**
     * @param backgroundColor
     *        状态栏的背景色（可以带透明度）
     * @param isLightStatusBar
     *        状态栏是否显示黑色前景色
     */
    protected void setStatusBarColor(int backgroundColor, boolean isLightStatusBar) {
        int options = getWindow().getDecorView().getSystemUiVisibility();
        options |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; // 配合 getWindow().setStatusBarColor(); 设置状态栏的背景色（可以带透明度）
        if (isLightStatusBar) {
            options |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // 状态栏前景色为黑色（无此值则为白色）
        }
        getWindow().getDecorView().setSystemUiVisibility(options);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(backgroundColor);
        }
    }

    /**
     * @param backgroundColor
     *        导航栏的背景色（可以带透明度）
     * @param isLightNavigationBar
     *        导航栏是否显示黑色前景色
     */
    protected void setNavigationBarColor(int backgroundColor, boolean isLightNavigationBar) {
        int options = getWindow().getDecorView().getSystemUiVisibility();
        options |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; // 配合 getWindow().setNavigationBarColor(); 设置导航栏的背景色（可以带透明度）
        if (isLightNavigationBar) {
            options |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR; // 导航栏前景色为黑色（无此值则为白色）
        }
        getWindow().getDecorView().setSystemUiVisibility(options);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(backgroundColor);
        }
    }

    /**
     * @param fitSystemWindow
     *        true - 根布局背景全屏，根布局中的内容在 statusBar 下沿和 navigationBar 上沿中间
     *        false - 根布局背景和内容都是全屏的，即 statusBar 和 navigationBar 会浮在根布局的前面
     */
    protected void setFitSystemWindow(boolean fitSystemWindow) {
        // 获取当前 activity 的根布局
        View root = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        // 自动适应有透明度的 statusBar 和 navigationBar（前提是 statusBar 和 navigationBar 必须是有透明度的）
        // 如果设置为 true 的话，则根布局背景是全屏的，而根布局中的内容则是在 statusBar 下沿和 navigationBar 上沿中间的
        // 如果设置为 false 的话，则根布局背景和内容都是全屏的，即 statusBar 和 navigationBar 会浮在根布局的前面
        root.setFitsSystemWindows(fitSystemWindow);
    }
}