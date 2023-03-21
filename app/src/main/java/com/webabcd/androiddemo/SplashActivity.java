/**
 * 闪屏页（启动页）
 *
 * 程序启动前的白屏是因为在完成布局文件的加载之前，显示的是 window 窗口背景，这个白屏就是 window 窗口背景
 * 所以做闪屏页的时候尽量不用 setContentView()
 * 但是如果闪屏页和 window 窗口背景的颜色一样，并且布局也不复杂的话，其实也基本看不出白屏的
 *
 * 本例的闪屏页的实现方法：
 * 1、先定义一个闪屏页主题，其中主要是定义 window 窗口背景，参见 res/values/styles.xml
 * 2、在 AndroidManifest.xml 的闪屏页 activity 中指定此主题，类似 android:theme="@style/SplashTheme"
 */

package com.webabcd.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 状态栏上显示深色图标
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);

                    // 跳转到主页
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    // 销毁闪屏页
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}