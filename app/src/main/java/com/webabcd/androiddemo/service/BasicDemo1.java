package com.webabcd.androiddemo.service;

import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

import com.webabcd.androiddemo.service.DownloadService;

public class BasicDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_basic_demo1);

        setTitle("afdsafdsfadsadfs");

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    BasicDemo1.this.startForegroundService(new Intent(BasicDemo1.this, DownloadService.class));
                } else {
                    BasicDemo1.this.startService(new Intent(BasicDemo1.this, DownloadService.class));
                }
            }
        });
        thread.start();


    }
}
