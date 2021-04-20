/**
 * Notification
 *
 * 本例用于演示
 * 1、弹出通知
 * 2、点击通知后跳转到指定的页面
 */

package com.webabcd.androiddemo.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class NotificationDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_notificationdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        // 获取 NotificationManager 对象，并为其指定 NotificationChannel 对象
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        // 构造通知点击后的需要跳转到的 PendingIntent 对象（点击通知跳转后的行为参见 NotificationDemo1Click.java）
        Intent intent = new Intent(getApplicationContext(), NotificationDemo1Click.class);
        // 通过 intent 保存通知的数据
        intent.putExtra("param1", "abc");
        intent.putExtra("param2", "xyz");
        // 第 2 个参数用于标识 PendingIntent
        // 第 4 个参数，用于指定当存在多个标识相同的 PendingIntent 时的行为
        //   PendingIntent.FLAG_CANCEL_CURRENT - 取消老的 PendingIntent，只保留新的 PendingIntent（即新的通知点击有效果，老的通知点击没有效果）
        //   PendingIntent.FLAG_UPDATE_CURRENT - 以新的 PendingIntent 更新老的 PendingIntent（即新的通知和老的通知点击效果都使用新的通知的）
        //   所以，如果需要不影响之前的通知，请每次构造 PendingIntent 时都把第 2 个参数设置为不同的值
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // 如果需要点击通知后发广播，则请使用类似如下的方式构造 PendingIntent 对象
        // PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0, new Intent("xxx.xxx.xxx"), PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder notificationBuilder = null;
        // api level 26 或以上系统的通知的实现逻辑（需要注册通知通道）
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 先创建一个 NotificationChannel 对象
            String channel_id = "channel_id"; // 通道id，需要包内唯一
            String channel_name = "channel_name"; // 通道名称，用户可见的一个名称（我也不知道用户咋可见）
            int importance = NotificationManager.IMPORTANCE_DEFAULT; // 通道重要性
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, importance);
            notificationManager.createNotificationChannel(notificationChannel);
            // api level 26 或以上系统需要注册通知通道，然后在这里指定通知通道的 id
            notificationBuilder = new Notification.Builder(this, channel_id);
        } else {
            // api level 26 以下系统不需要注册通知通道
            notificationBuilder = new Notification.Builder(this);
        }

        // 构造 Notification 对象
        final Notification notification = notificationBuilder
                .setContentTitle("Title") // 通知的标题
                .setContentText("测试内容") // 通知的内容
                .setSmallIcon(R.mipmap.ic_launcher_alpha) // 小图标，状态栏上用此图标（只能用 alpha 图层绘制，简单说就是背景透明，然后只用白色作画）
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 大图标
                .setContentIntent(pendingIntent) // 通知点击后的需要跳转到的 PendingIntent 对象
                .setAutoCancel(true) // 通知点击后自动消失
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE) // 使用系统默认声音和震动（自定义声音和震动懒得写了，网上搜吧）
                .setProgress(100, 30, false) // 设置进度条（3 个参数分别为: 最大值, 当前值, 进度是否是不确定的）
                .build();


        // 弹出通知
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出通知
                // 第 2 个参数为具体的 notification 对象
                // 第 1 个参数用于指定该 notification 对象的 id
                notificationManager.notify(123, notification);
            }
        });

        // 移除通知
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 移除通知
                // 移除指定 id 的 notification 对象
                notificationManager.cancel(123);
            }
        });

        // 更新通知
        Notification.Builder finalNotificationBuilder = notificationBuilder;
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新通知和弹出通知是一样的，调用 notify() 就行，其会覆盖同 id 的 notification 对象
                final Notification notification = finalNotificationBuilder.setContentTitle("aaa") .setContentText("bbb").build();
                notificationManager.notify(123, notification);
            }
        });
    }
}