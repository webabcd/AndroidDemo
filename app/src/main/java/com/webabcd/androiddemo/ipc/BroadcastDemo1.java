/**
 * 本例用于演示如何发送广播，对应的的广播接收器采用静态注册的方式（即在 AndroidManifest.xml 中注册）
 * 正常演示此示例需要安装 AndroidDemoIpc 项目生成的 app
 *
 * 注：关于如何通过静态注册（即在 AndroidManifest.xml 中注册）的方式注册 BroadcastReceiver 请参见 AndroidDemoIpc 项目中的 BroadcastDemo1.java
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class BroadcastDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_broadcastdemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 指定需要发送的广播的名称
                Intent intent = new Intent("cn.webabcd.broadcast.BroadcastDemo1");
                // 指定广播接收器的包名和类名
                // 注：android 8 对静态注册的广播做了限制，必须要加这个。而动态注册的广播则无此限制
                ComponentName componentName = new ComponentName("com.webabcd.androiddemoipc", "com.webabcd.androiddemoipc.BroadcastDemo1");
                intent.setComponent(componentName);

                // 发送广播时，带上需要传递的参数
                Bundle bundle = new Bundle();
                bundle.putString("p1", "v1");
                bundle.putString("p2", "v2");
                intent.putExtras(bundle);

                // 发送标准广播（关于有序广播的说明参见 BroadcastDemo3.java）
                sendBroadcast(intent);
            }
        });
    }
}