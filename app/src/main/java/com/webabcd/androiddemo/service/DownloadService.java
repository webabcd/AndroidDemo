package com.webabcd.androiddemo.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.webabcd.androiddemo.R;

// 线程同步的时候注意：此后台任务和前端是同进程的（如果前端和后台任务同时被杀，那么后台任务自己重启后会换一个 pid，如果再启动前端则这个前端也是和后台任务同 pid 的）
// 通过 android.os.Process.myPid() 获取 pid
public class DownloadService extends Service {
    // 所以 DownloadService 这个不能被混淆
    private static final String ACTION = "com.webabcd.androiddemo.service.DownloadService";

    private static String _currentTaskId; // 空的话就证明没有正在执行的任务或取消了当前任务

    private boolean _allowsCellularAccess = false;
    private int _maxSpeed = Integer.MAX_VALUE;

    private boolean _isNetworkAvailable = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //startForeground(1,new Notification());

        // service 也是会阻塞的，所以要新开线程，否则会 anr
        DownloadThread downloadThread = new DownloadThread("DownloadThread");
        downloadThread.start();

        if (Build.VERSION.SDK_INT >= 26)
        {
            /*
            String notificationId = "channelId";

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationId);
            Notification notification = builder.build();
            startForeground(1122334, notification);
            */

            String channelId = "channelId";
            String channelName = "channelName";

            Notification.Builder builder = new Notification.Builder(this, channelId);
            // builder..setSmallIcon(R.drawable.logo_small);
            // builder..setContentTitle("测试服务");
            // builder..setContentText("我正在运行");

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            startForeground(1122334, notification);
        }

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // startForeground(1,new Notification());

        // return super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    // 在 app switcher 杀不会走到这里，在“设置”->“应用”->“已下载”中杀不会走到这里
    // 在“设置”->“应用”->“正在运行”中杀会走到这里，调用 Service 的 stopSelf() 方法时会走到这里
    @Override
    public void onDestroy() {

        super.onDestroy();

        // startService(new Intent(this, DownloadService.class));
    }


    class DownloadThread extends Thread {
        public DownloadThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            long prevTime = 0;
            int callAdjustFileExtensionNum = 0;

            while (true) {
                try {
                    Log.d("aaa", "dddd");
                    mySleep(1000);

                } catch (Exception ex) {
                    mySleep(1000);
                }
            }
        }
    }

    private void mySleep(int ms) {
        if (ms < 0) {
            return;
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
