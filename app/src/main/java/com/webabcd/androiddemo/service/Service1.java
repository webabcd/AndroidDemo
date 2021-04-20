/**
 * 本例用于演示具体的前台服务逻辑
 *
 * 注：
 * 1、需要在 AndroidManifest.xml 注册服务 <service android:name=".service.Service1" android:enabled="true" />
 * 2、对于前台服务来说，必须在通知栏显示通知，即使杀掉 app 前台服务也会运行，用户可以通过通知栏停掉前台服务
 */

package com.webabcd.androiddemo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Date;
import java.util.Locale;

// 通过继承 Service 来实现自定义服务，在 Service 中你需要自己启动一个子线程来实现业务逻辑（因为 Service 是运行在主线程的）
// 如果是通过继承 IntentService 实现自定义服务的话，则可以在其内部的 onHandleIntent() 方法中实现业务逻辑（它是工作在子线程的）
public class Service1 extends Service {

    private final String LOG_TAG = "Service1";

    private Notification.Builder mNotificationBuilder;

    public Service1() {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        // 构造前台服务的通知（这里不细说了，关于通知的详细说明请参见 /notification/NotificationDemo1.java）
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationBuilder = new Notification.Builder(this, "channel_id");
        } else {
            mNotificationBuilder = new Notification.Builder(this);
        }
        final Notification notification = mNotificationBuilder
                .setContentTitle("Title")
                .setContentText("测试内容")
                .setSmallIcon(R.mipmap.ic_launcher_alpha)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();

        // 对于 android 8 以下系统，通过如下方式将后台服务变为前台服务，并显示前台服务通知
        // 对于 android 8 或以上系统，必须在创建前台服务后的 5 秒内，通过如下方式显示前台服务通知
        // 第 2 个参数为具体的 notification 对象
        // 第 1 个参数用于指定该 notification 对象的 id
        startForeground(789, notification);

        // 启动一个线程，用于实现业务逻辑
        startThread();
    }

    // 调用者通过 startForegroundService() 或 startService() 启动服务时，分为如下两种情况
    // 1、如果服务没被创建，则会走 onCreate(), onStartCommand()
    // 2、如果服务已被创建，则只会走 onStartCommand()，也就是说服务不会被重复创建的
    // onStartCommand() 的主要作用是: 接收调用者传递过来的数据
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取服务的调用者传递过来的数据
        String param1 = intent.getStringExtra("param1");
        String param2 = intent.getStringExtra("param2");
        Toast.makeText(this, String.format(Locale.US, "param1:%s, param2:%s", param1, param2), Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 停止子线程
        stopThread();
        // 停止前台服务（true 代表移除通知栏的通知）
        stopForeground(true);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private Thread mThread;
    private void startThread() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 构造新的通知
                        Notification notification = mNotificationBuilder
                                .setContentTitle("Title")
                                .setContentText("测试内容 " + Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"))
                                .setSmallIcon(R.mipmap.ic_launcher_alpha)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .build();
                        // 更新指定 id 的通知
                        startForeground(789, notification);

                        // 可以通过 stopSelf() 停掉当前服务
                        // stopSelf();

                        if (Thread.interrupted()) {
                            break;
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, "线程被 interrupted 了");
                }
            }
        });
        mThread.setName("thread_Service1");
        mThread.setDaemon(true);
        mThread.start();
    }
    private void stopThread() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
