/**
 * DownloadManager 的示例（下载任务会在通知栏显示，即使 app 被杀也能继续下载）
 * 本例以一个 apk 文件下载为例，演示如何新增任务，删除任务，获取任务状态和下载进度，以及下载完成后弹出安装界面
 *
 * 注：
 * 1、我这里模拟器运行失败，真机运行正常
 * 2、请先进入 /storage/StorageDemo3.java 动态申请权限（我懒得在这里写了）
 * 3、经测试，大部分华为手机的支持都有问题，会报错 java.lang.SecurityException: Unsupported path xxx 
 */

package com.webabcd.androiddemo.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.io.File;
import java.util.Locale;

public class DownloadManagerDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "DownloadManagerDemo1";

    private Thread mThread;
    private DownloadManager mDownloadManager;
    private long mDownloadId = -1; // 下载任务的唯一标识

    private boolean mRegistered = false;
    private DownloadReceiver mDownloadReceiver;

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_downloadmanagerdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mTextView1 = findViewById(R.id.textView1);

        sample();
        registerReceiver();
    }

    private void sample() {
        // 新增下载任务
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 用于在 7.0 或以上系统，让下载的 apk 文件可以被直接安装（否则会因为读取不到 apk 而无法安装）
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    StrictMode.VmPolicy.Builder localBuilder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(localBuilder.build());
                }

                // 通过下载地址实例化 DownloadManager.Request 对象
                String downloadUrl = "https://mmgrapp-75037.gzc.vod.tencent-cloud.com/secure/GodDresser/1/2/3/102027/tencentmobilemanager_20210401113919_8.11.0_android_build6724_102027.apk";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

                // 移动网络和 wifi 网络均可下载
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                // 通知栏标题
                request.setTitle("download_title");
                // 通知栏内容
                request.setDescription("download_description");
                // 7.0 或以上系统适配
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    request.setRequiresDeviceIdle(false); // 是否只能在系统空闲时执行
                    request.setRequiresCharging(false); // 是否只能在充电状态下执行
                }

                // 下载过程和下载完成后通知栏有通知消息
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                // 隐藏通知栏的下载任务的消息，需要在 AndroidManifest.xml 配置权限 <uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
                // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                // 下载文件的类型为 apk
                request.setMimeType("application/vnd.android.package-archive");
                // 指定文件保存地址，需要动态申请权限，否则会报错 No permission to write to...（懒得写了，请先进入 /storage/StorageDemo3.java 动态申请权限）
                File file = new File(Environment.getExternalStorageDirectory(), "myTest.apk");
                Log.d(LOG_TAG, "localUrl: " + Uri.fromFile(file).toString());
                request.setDestinationUri(Uri.fromFile(file));

                // 实例化 DownloadManager
                mDownloadManager = (DownloadManager)DownloadManagerDemo1.this.getSystemService(Context.DOWNLOAD_SERVICE);
                // 将下载请求加入下载队列，返回数据为该下载任务的标识
                mDownloadId = mDownloadManager.enqueue(request);
                Log.d(LOG_TAG, "downloadId: " + mDownloadId);
            }
        });

        // 删除当前的下载任务（如何手动 pause/resume 呢？我不知道）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDownloadId > -1) {
                    mDownloadManager = (DownloadManager) DownloadManagerDemo1.this.getSystemService(Context.DOWNLOAD_SERVICE);
                    mDownloadManager.remove(mDownloadId);
                }
            }
        });

        // 跳转到系统下载界面
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManagerDemo1.this.startActivity(new android.content.Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            }
        });

        // 获取任务状态
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (mDownloadId == -1) {
                            continue;
                        }

                        // 实例化 DownloadManager.Query 对象
                        DownloadManager.Query query = new DownloadManager.Query();
                        // 查询指定的 downloadId（可以不指定查询条件，那样就是查询全部下载任务）
                        query.setFilterById(mDownloadId);
                        // 根据查询条件获取游标
                        Cursor cursor = mDownloadManager.query(query);
                        if (cursor.moveToFirst()) {

                            int downloadIdIndex = cursor.getColumnIndex(DownloadManager.COLUMN_ID);
                            int remoteUrlIndex = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
                            int localUrlIndex = 0;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                // 7.0 或以上系统使用 COLUMN_LOCAL_URI 字段
                                localUrlIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                            } else {
                                localUrlIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                            }
                            int totalBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                            int downloadedBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                            int taskStatusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);

                            // 以下几个字段看名字就知道意思，就不细说了
                            long downloadId = cursor.getLong(downloadIdIndex);
                            String remoteUrl = cursor.getString(remoteUrlIndex);
                            String localUrl = cursor.getString(localUrlIndex);
                            long totalBytes = cursor.getLong(totalBytesIndex);
                            long downloadedBytes = cursor.getLong(downloadedBytesIndex);

                            // 任务状态包括如下几个可能的值
                            // DownloadManager.STATUS_PENDING, DownloadManager.STATUS_RUNNING, DownloadManager.STATUS_PAUSED, DownloadManager.STATUS_SUCCESSFUL, DownloadManager.STATUS_FAILED
                            int taskStatus = cursor.getInt(taskStatusIndex);

                            writeMessage(String.format(Locale.US, "downloadId:%d\nremoteUrl:%s\nlocalUrl:%s\ntotalBytes:%d\ndownloadedBytes:%d\ntaskStatus:%d",
                                    downloadId, remoteUrl, localUrl, totalBytes, downloadedBytes, taskStatus));
                        }
                        cursor.close();

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
        mThread.setName("thread_DownloadManagerDemo1");
        mThread.setDaemon(true);
        mThread.start();
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView1.setText(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

        unregisterReceiver();

        super.onDestroy();
    }



    // 通过监听广播，获取任务完成事件（也可以通过轮询任务的方式获取下载任务是否完成）和通知栏中的任务点击事件（这个只能通过广播来获取了）
    public void registerReceiver() {
        mDownloadReceiver = new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // 下载任务完成的广播
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED); // 在通知栏点击下载任务之后的广播
        this.registerReceiver(mDownloadReceiver, filter);
        mRegistered = true;
    }
    public void unregisterReceiver() {
        if (mRegistered) {
            try {
                this.unregisterReceiver(mDownloadReceiver);
                mRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 下载任务完成
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 获取当前完成的下载任务的 downloadId
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                // 通过 downloadId 获取本地文件地址
                DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri localUri = downloadManager.getUriForDownloadedFile(downloadId);
                if (localUri != null) {
                    // 弹出 apk 安装界面
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(localUri, "application/vnd.android.package-archive");
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);
                } else {
                    Log.e(LOG_TAG, "download error");
                }
            }
            // 在通知栏点击下载任务之后，则跳转到系统下载界面
            else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(viewDownloadIntent);
            }
        }
    }
}