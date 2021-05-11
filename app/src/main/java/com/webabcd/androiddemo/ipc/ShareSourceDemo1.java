/**
 * 本例用于演示“分享源”
 *
 * 注：
 * 1、关于“分享目标”请参见 AndroidDemoIpc 项目中的 ShareTargetDemo1.java
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ShareSourceDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_sharesourcedemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        Intent intent = new Intent();
        // 设置 action 为发送分享
        intent.setAction(Intent.ACTION_SEND);
        // 设置 type 为发送纯文本内容
        intent.setType("text/plain");
        // 设置需要分享的数据
        intent.putExtra(Intent.EXTRA_TEXT, "我是分享内容");

        // 弹出系统分享
        startActivity(Intent.createChooser(intent, "系统分享框的 Title 上显示的信息"));
        // startActivity(Intent.createChooser(intent, getResources().getText(R.string.app_name)));
    }
}