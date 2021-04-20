/**
 * Notification
 *
 * 本例用于演示
 * 1、弹出自定义 ui 的 Notification
 */

package com.webabcd.androiddemo.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.webabcd.androiddemo.R;

public class NotificationDemo2 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_notificationdemo2);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder = null;
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channel_id = "channel_id";
            String channel_name = "channel_name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, importance);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationBuilder = new Notification.Builder(this, channel_id);
        } else {
            notificationBuilder = new Notification.Builder(this);
        }

        // 普通的自定义通知栏（高度最大 64dp）
        // 只能通过 setTextViewText(), setViewVisibility(), setProgressBar() 之类的修改控件信息
        // 可以通过 setOnClickPendingIntent() 为指定控件增加点击后的动作
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.remoteviews_notification_notificationdemo2);
        remoteViews.setTextViewText(R.id.textView1, "aaaaaa");

        // 大个的自定义通知栏（高度最大 256dp）
        // 只能通过 setTextViewText(), setViewVisibility(), setProgressBar() 之类的修改控件信息
        // 可以通过 setOnClickPendingIntent() 为指定控件增加点击后的动作
        RemoteViews remoteViewsBig = new RemoteViews(this.getPackageName(), R.layout.remoteviews_big_notification_notificationdemo2);
        remoteViewsBig.setTextViewText(R.id.textView1, "111111");


        final Notification notification = notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher_alpha)

                // api level 24 以下系统指定自定义通知栏
                .setContent(remoteViews)

                // api level 24 或以上系统指定自定义通知栏（普通的自定义通知栏）
                // .setCustomContentView(remoteViews)

                // api level 24 或以上系统指定自定义通知栏（大个的自定义通知栏）
                // .setCustomBigContentView(remoteViewsBig)

                .build();

        // 弹出通知
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.notify(111, notification);
            }
        });
    }
}