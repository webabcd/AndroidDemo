/**
 * Notification
 *
 * 本例用于处理 NotificationDemo1 弹出的通知点击后的行为
 */

package com.webabcd.androiddemo.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Locale;

public class NotificationDemo1Click extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_notificationdemo1click);

        mTextView1 = findViewById(R.id.textView1);

        // 通过 intent 获取通知的数据
        Intent intent = this.getIntent();
        String param1 = intent.getStringExtra("param1");
        String param2 = intent.getStringExtra("param2");
        mTextView1.setText(String.format(Locale.US, "param1:%s, param2:%s", param1, param2));
    }
}