/**
 * 内部存储，外部存储，权限请求，存储路径，存储大小，获取 assets 中的数据，获取 res/raw 中的数据
 *
 *
 * 内部存储和外部存储
 * 机身存储包括内部存储和外部存储，外部可移动存储就是外部存储
 *
 * 程序的内部存储路径为 /data/data/packagename/， 如果是 6.0 或以上系统则为 /data/user/n/packagename/（其中的 n 为整数，代表不同的用户，如果就一个用户那么 n 就是 0）
 * 程序的外部存储（机身存储）路径为 /storage/emulated/0/
 * 程序的外部存储（外部可移动存储）路径类似 /storage/B3E4-1711/（后面的这个挂载目录根据设备不同会有所不同）
 *
 * files 目录，存放的数据系统不会主动删除，用户在设置中的“应用信息”中单击“清除数据”后会删除
 * files 目录内部存储的路径为 /data/data/packagename/files 或 /data/user/n/packagename/files
 * files 目录外部存储的路径为 /storage/emulated/0/Android/data/packagename/files
 * cache 目录，存放的数据可能会被系统主动删除（比如系统认为存储空间不够的时候），用户在设置中的“应用信息”中单击“清除缓存”后会删除
 * cache 目录内部存储的路径为 /data/data/packagename/cache 或 /data/user/n/packagename/cache
 * cache 目录外部存储的路径为 /storage/emulated/0/Android/data/packagename/cache
 *
 * 在 6.0 或以上系统操作外部存储（不包括外部存储的 files 目录和 cache 目录），需要动态申请权限
 *
 *
 * 本例演示
 * 1、如何动态申请存储权限（注：files 目录和 cache 目录不用申请权限）
 * 2、如何获取各种存储的路径，以及如何获取存储的大小
 * 3、如何获取 assets 中的数据（不会映射到 R.java 文件中，允许有子目录）
 * 4、如何获取 res/raw 中的数据（会映射到 R.java 文件中，不允许有子目录）
 */

package com.webabcd.androiddemo.storage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class StorageDemo3 extends AppCompatActivity {

    private final String LOG_TAG = "StorageDemo3";

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_storagedemo3);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample1();
        sample2();
        sample3();
    }


    // 演示如何动态申请存储权限（注：files 目录和 cache 目录不用申请权限）
    private void sample1() {
        // 在 6.0 或以上系统操作外部存储，需要动态申请权限（注：外部存储的 files 目录和 cache 目录不需要权限）
        // 需要先在 AndroidManifest.xml 中做如下配置
        // <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
        // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

        String[] storagePermissions = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };
        // 检查是否有指定的权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 动态申请指定的权限（前提是先要在 AndroidManifest.xml 做好权限的配置）
            // 申请结果通过回调 @Override onRequestPermissionsResult() 获取
            ActivityCompat.requestPermissions(this, storagePermissions, 1); // 最后的参数就是一个标识，以便在申请结果的回调中知道是谁申请的
        }
    }
    // 权限动态申请的结果的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // requestCode - 就是通过 requestPermissions() 申请权限时的最后一个参数
        // permissions - 申请权限的名称列表
        // grantResults - 申请权限的结果列表（-1 代表拒绝；1 代表允许）

        for (int i = 0; i < permissions.length; i++) {
            Log.i(LOG_TAG, "申请的权限为：" + permissions[i] + "，申请结果：" + grantResults[i]);
        }
    }


    // 演示如何获取各种存储的路径，以及如何获取存储的大小
    private void sample2() {
        try {
            // 内部存储的 files 目录（类似 /data/data/packagename/files）
            mTextView1.setText("getFilesDir(): " + getFilesDir().getCanonicalPath());
            mTextView1.append("\n");
            // 内部存储的 cache 目录（类似 /data/data/packagename/cache）
            mTextView1.append("getCacheDir(): " + getCacheDir().getCanonicalPath());
            mTextView1.append("\n");
            // 外部存储的 files 目录（类似 /storage/emulated/0/Android/data/packagename/files）
            mTextView1.append("getExternalFilesDir(): " + getExternalFilesDir("").getCanonicalPath());
            mTextView1.append("\n");
            // 外部存储的 cache 目录（类似 /storage/emulated/0/Android/data/packagename/cache）
            mTextView1.append("getExternalCacheDir(): " + getExternalCacheDir().getCanonicalPath());
            mTextView1.append("\n");
            // 默认外部存储目录（类似 /storage/emulated/0）
            mTextView1.append("getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory().getCanonicalPath());
            mTextView1.append("\n");

            // getExternalFilesDirs(Environment.MEDIA_MOUNTED) - 获取所有已挂载的外部存储
            // 比如既有机身内的外部存储，又有 sd 卡外部存储的时候，会获取到 2 条记录，类似如下
            // /storage/emulated/0/Android/data/packagename/files/mounted（其中的 /storage/emulated/0 就是机身内的外部存储的路径）
            // /storage/B3E4-1711/Android/data/packagename/files/mounted（其中的 /storage/B3E4-1711 就是 sd 卡外部存储的路径）
            File[] fileList = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            for (int i = 0; i < fileList.length; i++) {
                mTextView1.append("getExternalFilesDirs(): " + fileList[i].getCanonicalPath());
                mTextView1.append("\n");
            }
        } catch (Exception ex) {
            mTextView1.setText(ex.toString());
        }

        // 获取存储的大小
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat(",###");

                File file = new File(getFilesDir(), "");
                mTextView1.setText(String.format("getTotalSpace():%s, getUsableSpace():%s, getFreeSpace:%s",
                        decimalFormat.format(file.getTotalSpace()), // 存储的总空间的大小（单位：字节）
                        decimalFormat.format(file.getUsableSpace()), // 存储的可用空间的大小（单位：字节）
                        decimalFormat.format(file.getFreeSpace()))); // 存储的剩余空间的大小（单位：字节），不一定都可用
            }
        });
    }


    // 演示如何获取 assets 中的数据，以及如何获取 res/raw 中的数据
    private void sample3() {
        // 获取 assets 中的数据（不会映射到 R.java 文件中，允许有子目录）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AssetManager assetManager =  getAssets();
                    InputStream inputStream = assetManager.open("text_storage_storagedemo3.txt");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();

                    mTextView1.setText(sb.toString());
                } catch (IOException ex) {
                    mTextView1.append(ex.toString());
                }
            }
        });

        // 获取 res/raw 中的数据（会映射到 R.java 文件中，不允许有子目录）
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getResources().openRawResource(R.raw.text_storage_storagedemo3);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();

                    mTextView1.setText(sb.toString());
                } catch (IOException ex) {
                    mTextView1.append(ex.toString());
                }
            }
        });
    }
}