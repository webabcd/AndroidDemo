/**
 * 本例用于演示如何发送广播，对应的的广播接收器采用动态注册的方式（即在 java 中注册）
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.lang.ref.WeakReference;

public class BroadcastDemo2 extends AppCompatActivity {

    public static final String ACTION_NAME = "cn.webabcd.broadcast.BroadcastDemo2";
    private boolean mRegistered = false;

    private TextView mTextView1;
    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_broadcastdemo2);

        mButton1 = findViewById(R.id.button1);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        registerReceiver();

        // 发送指定名称的广播，并传递参数
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_NAME);
                intent.putExtra("p1", "v1");
                intent.putExtra("p2", "v2");

                // 发送标准广播（关于有序广播的说明参见 BroadcastDemo3.java）
                sendBroadcast(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

    public void appendText(String text) {
        mTextView1.append(text);
        mTextView1.append("\n");
    }



    // 动态注册指定的广播接收器
    public void registerReceiver() {
        // 实例化需要注册的自定义的 BroadcastReceiver 对象
        mMyBroadcastReceiver = new MyBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        // 指定需要监听的广播的名称（注：有一堆系统广播可以监听，关于这些系统广播的名称网上搜吧）
        filter.addAction(ACTION_NAME);
        // 注册指定的广播
        this.registerReceiver(mMyBroadcastReceiver, filter);
        mRegistered = true;
    }

    // 动态注销指定的广播接收器
    public void unregisterReceiver() {
        if (mRegistered) {
            try {
                // 注销指定的广播
                this.unregisterReceiver(mMyBroadcastReceiver);
                mRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 自定义的广播接收器的实现
    private MyBroadcastReceiver mMyBroadcastReceiver;
    private static class MyBroadcastReceiver extends BroadcastReceiver {
        // 用于保存本例中向此接收器发送广播的 activity（仅用于本例演示用）
        private WeakReference<BroadcastDemo2> mActivity;

        public MyBroadcastReceiver(BroadcastDemo2 activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastDemo2.ACTION_NAME.equals(intent.getAction())) {

                // 获取接收到的广播的名称
                String actionName = intent.getAction();

                // 获取传递过来的参数
                String p1 = intent.getStringExtra("p1");
                String p2 = intent.getStringExtra("p2");

                // 打印日志
                mActivity.get().appendText(String.format("action:%s, p1:%s, p2:%s", actionName, p1, p2));
            }
        }
    }
}