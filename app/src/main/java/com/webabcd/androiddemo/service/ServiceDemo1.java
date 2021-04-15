/**
 * 后台服务已经被事实上弃用了，因为它不再被允许后台长时间操作了
 * 除了 foreground service 外，就不再需要 service 了
 *
 * 后台任务请用 WorkManager 实现吧
 */

package com.webabcd.androiddemo.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class ServiceDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_servicedemo1);
    }
}