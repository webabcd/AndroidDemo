/**
 * 监听配置变化
 * 配置变化（configuration change）包括横竖屏切换，屏幕尺寸变化，语言变化，插入/拔出外接键盘等
 *
 * 本例以监听横竖屏变化为例
 * 1、正常来说，横竖屏切换是需要 activity 销毁并重建的（参见：/activity/ActivityDemo2.java）
 * 2、在 AndroidManifest.xml 的指定的 activity 节点中做如下配置，则横竖屏切换时会回调 onConfigurationChanged() 方法，且不需要 activity 销毁并重建
 * android:configChanges="orientation|screenSize"
 * 注：上面的配置用于说明哪些配置的变化需要回调 onConfigurationChanged() 方法，如果你需要横竖屏切换时回调此方法，则要配置为 orientation|screenSize
 */

package com.webabcd.androiddemo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Date;

public class ConfigurationChangeDemo1 extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_configurationchangedemo1);

        mTextView1 = findViewById(R.id.textView1);
        Button mButton1 = findViewById(R.id.button1);
        Button mButton2 = findViewById(R.id.button2);

        mButton1.setOnClickListener(v -> {
            // 强制横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });

        mButton2.setOnClickListener(v -> {
            // 强制竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });

        mTextView1.append("onCreate: " + Helper.formatDate(new Date(), "HH:mm:ss\n"));
    }

    // 配置变化后的回调，其中的 newConfig 参数是变化后的配置对象
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mTextView1.append("onConfigurationChanged, newConfig.orientation: " + newConfig.orientation + "\n");
    }
}