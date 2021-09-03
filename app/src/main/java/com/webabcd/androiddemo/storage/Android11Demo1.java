/**
 * 本例用于演示如何在 android 11.0 或以上系统且 targetSdkVersion 大于等于 30 时使用外部存储
 *
 * 1、android 10.0 或以上系统且 targetSdkVersion 小于等于 29 时使用外部存储
 * 在 AndroidManifest 的 application 节点配置 requestLegacyExternalStorage="true"
 *
 * 2、android 11.0 或以上系统且 targetSdkVersion 大于等于 30 时使用外部存储
 * 在 AndroidManifest 配置 <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
 * 这个需要用户先去设置页打开相关权限
 *
 * 3、android 11.0 或以上系统且 targetSdkVersion 大于等于 30 时不使用外部存储，但是需要对老程序做外部存储到内部存储的迁移
 * 在 AndroidManifest 的 application 节点配置 preserveLegacyExternalStorage="true"
 * 这样的话，如果你是更新老版程序则可使用外部存储，如果是新装程序则不可使用外部存储
 */

package com.webabcd.androiddemo.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;

public class Android11Demo1 extends AppCompatActivity {

    private Button _button1;
    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_android11demo1);

        _button1 = findViewById(R.id.button1);
        _textView1 = findViewById(R.id.textView1);

        // 演示如何在 android 11.0 或以上系统且 targetSdkVersion 大于等于 30 时使用外部存储
        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFile();
            }
        });
    }

    private void createFile() {
        // 判断是否有使用外部存储的权限
        if (Environment.isExternalStorageManager()) {
            String dirPath = Environment.getExternalStorageDirectory() + "/wanglei_test/";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                // 没有权限的话会返回 false
                boolean isOk = dir.mkdirs();
                appendLog(String.format(Locale.ENGLISH, "%s mkdirs: %b", dirPath, isOk));
            } else {
                appendLog(String.format(Locale.ENGLISH, "文件夹存在: %s", dirPath));
            }

            // 新建文件
            String stringContent = Helper.formatDate(new Date(), "HH:mm:ss\n");
            byte[] bytesContent = stringContent.getBytes(StandardCharsets.UTF_8);
            File file = new File(dirPath, new Date().getTime() + ".txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytesContent);
                fos.close();
                appendLog(String.format(Locale.ENGLISH, "新建文件成功: %s", file.getAbsolutePath()));
            } catch (Exception ex) {
                appendLog(String.format(Locale.ENGLISH, "新建文件失败: %s", ex.toString()));
            }
        } else {
            appendLog("请先去设置页打开相关权限");

            // 跳转到 ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION 权限设置页
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
        }
    }

    private void appendLog(String message) {
        _textView1.append(message);
        _textView1.append("\n");
    }
}