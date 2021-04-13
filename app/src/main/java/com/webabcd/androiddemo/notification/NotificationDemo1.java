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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_notificationdemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        // api level 26 或以上系统的通知的实现逻辑
        if (Build.VERSION.SDK_INT >= 26) {
            // 先创建一个 NotificationChannel 对象
            String channel_id = "channel_id"; // 通道id，需要包内唯一
            String channel_name = "channel_name"; // 通道名称，用户可见的一个名称（我也不知道用户咋可见）
            int importance = NotificationManager.IMPORTANCE_HIGH; // 通道重要性
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, importance);

            // 获取 NotificationManager 对象，并为其指定 NotificationChannel 对象
            final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            // 构造通知点击后的需要跳转到的 PendingIntent 对象（点击通知跳转后的行为参见 NotificationDemo1Content.java）
            Intent intent = new Intent(getApplicationContext(), NotificationDemo1Content.class);
            // 通过 intent 保存通知的数据
            intent.putExtra("param1", "abc");
            intent.putExtra("param2", "xyz");
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // 构造 Notification 对象
            final Notification notification = new Notification.Builder(this, channel_id)
                    .setContentTitle("Title") // 通知的标题
                    .setContentText("测试内容") // 通知的内容
                    .setSmallIcon(R.mipmap.ic_launcher_alpha) // 小图标，状态栏上用此图标（只能用 alpha 图层绘制，简单说就是背景透明，然后只用白色作画）
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 大图标
                    .setContentIntent(pendingIntent) // 通知点击后的需要跳转到的 PendingIntent 对象
                    .setAutoCancel(true) // 通知点击后自动消失
                    .build();

            mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 弹出通知
                    // 第 1 个参数用标识该 Notification 对象
                    notificationManager.notify(1, notification);
                }
            });
        } else {
            // api level 26 以下系统的通知的实现逻辑
            // 就是不需要 NotificationChannel 了，其他都差不多
        }
    }
}