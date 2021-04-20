/**
 * 后台服务已经被事实上弃用了，因为 app 进入空闲状态后，后台服务会被自动停掉
 * 如果有后台服务需求，请使用 WorkManager 实现吧
 *
 *
 * 本例用于演示前台服务的用法，服务逻辑请参见 /service/Service1.java
 *
 * 注：
 * 1、如果是 android 10（api level 29）或以上系统，需要在 AndroidManifest.xml 配置权限 <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 */

package com.webabcd.androiddemo.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ServiceDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_servicedemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {

        // 指定后台服务的类为 Service1（参见 /service/Service1.java）
        Intent intent = new Intent(this, Service1.class);
        // 构造传递给后台服务的数据
        intent.putExtra("param1", "value1");
        intent.putExtra("param2", "value2");

        // 启动前台服务
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // android 8 或以上系统通过 startForegroundService() 启动前台服务
                    startForegroundService(intent);
                } else {
                    // android 8 以下系统通过 startService() 启动后台服务，然后在服务类中将后台服务变为前台服务
                    // 注：
                    // 1、通过 startService() 启动的后台服务与调用者无关，会一直运行（当然这指的是 android 8 以下系统，android 8 或以上系统当 app 进入空闲状态后，后台服务会被自动停掉）
                    // 2、通过 bindService() 启动的后台服务是和调用者绑定的，调用者被销毁的时候，就要解绑服务，服务就被销毁了，没啥用，我就不写了
                    startService(intent);
                }
            }
        });

        // 停止前台服务
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });
    }
}