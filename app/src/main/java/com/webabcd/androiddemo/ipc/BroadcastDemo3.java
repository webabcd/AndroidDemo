/**
 * 本例用于演示如何发送有序广播，对应的的广播接收器采用动态注册的方式（即在 java 中注册）
 *
 * 标准广播和有序广播
 * 1、标准广播就是发出去后，所有注册的监听器均会收到广播信息。
 *    通过 sendBroadcast() 发送
 * 2、有序广播就是发出去后，所有注册的监听器按优先级级别顺序接收。某一级接收器收到广播后，可以修改广播内容再向下传递，或者取消广播的向下传递。
 *    通过 sendOrderedBroadcast() 发送
 *    动态注册时通过 IntentFilter 的 setPriority() 指定优先级
 *    静态注册时通过 intent-filter 的 android:priority 指定优先级
 *    优先级的取值范围是 -1000 到 1000（值越大越优先）
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

public class BroadcastDemo3 extends AppCompatActivity {

    public static final String ACTION_NAME = "cn.webabcd.broadcast.BroadcastDemo3";
    private boolean mRegistered = false;

    private TextView mTextView1;
    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_broadcastdemo3);

        mButton1 = findViewById(R.id.button1);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        registerReceiver();

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 指定广播的名称
                Intent intent = new Intent(ACTION_NAME);

                // 指定广播传递的参数
                intent.putExtra("p1", "v1");

                // 指定有序广播的初始数据（这部分每一级接收器都是可以修改后再向下传递的）
                int initialCode = 100;
                String initialData = "abc";
                Bundle initialExtras = new Bundle();
                initialExtras.putString("p2", "v2");

                // 发送有序广播
                sendOrderedBroadcast(intent, null, null, null, initialCode, initialData, initialExtras);
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
        mMyBroadcastReceiver = new MyBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NAME);
        filter.setPriority(500); // 指定优先级
        this.registerReceiver(mMyBroadcastReceiver, filter);

        mMyBroadcastReceiver2 = new MyBroadcastReceiver2(this);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(ACTION_NAME);
        filter2.setPriority(100); // 指定优先级
        this.registerReceiver(mMyBroadcastReceiver2, filter2);

        mRegistered = true;
    }

    // 动态注销指定的广播接收器
    public void unregisterReceiver() {
        if (mRegistered) {
            try {
                this.unregisterReceiver(mMyBroadcastReceiver);
                this.unregisterReceiver(mMyBroadcastReceiver2);
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
        private WeakReference<BroadcastDemo3> mActivity;

        public MyBroadcastReceiver(BroadcastDemo3 activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastDemo3.ACTION_NAME.equals(intent.getAction())) {

                // 获取接收到的广播的名称
                String actionName = intent.getAction();

                // 获取广播传递的参数
                String p1 = intent.getStringExtra("p1");

                // 获取有序广播的数据
                int code = getResultCode();
                String data = getResultData();
                Bundle extras = getResultExtras(true);
                String p2 = extras.getString("p2");

                // 打印日志
                mActivity.get().appendText(String.format("action1:%s, p1:%s, extras p2:%s, data:%s, code:%d", actionName, p1, p2, data, code));

                // 修改有序广播的数据（下一级接收器将收到修改后的数据）
                extras.putString("p2", "v2_modified");
                setResultExtras(extras);
                setResultData("xyz");
                setResultCode(999);

                // 取消有序广播的向下传递
                // abortBroadcast();
            }
        }
    }

    // 自定义的广播接收器的实现
    private MyBroadcastReceiver2 mMyBroadcastReceiver2;
    private static class MyBroadcastReceiver2 extends BroadcastReceiver {
        // 用于保存本例中向此接收器发送广播的 activity（仅用于本例演示用）
        private WeakReference<BroadcastDemo3> mActivity;

        public MyBroadcastReceiver2(BroadcastDemo3 activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastDemo3.ACTION_NAME.equals(intent.getAction())) {

                // 获取接收到的广播的名称
                String actionName = intent.getAction();

                // 获取广播传递的参数
                String p1 = intent.getStringExtra("p1");

                // 获取有序广播的数据
                int code = getResultCode();
                String data = getResultData();
                Bundle extras = getResultExtras(true);
                String p2 = extras.getString("p2");

                // 打印日志
                mActivity.get().appendText(String.format("action1:%s, p1:%s, extras p2:%s, data:%s, code:%d", actionName, p1, p2, data, code));
            }
        }
    }
}