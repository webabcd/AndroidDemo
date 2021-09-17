/**
 * 本例用于演示 Android 11 通过 MediaStore 管理文件
 *
 * 通过 MediaStore 可以在指定的公共目录中管理文件，包括图片目录，视频目录，音频目录，下载目录
 *     文档目录等其他公共目录是不能通过 MediaStore 管理的
 * 通过 MediaStore 保存的数据，卸载 app 后不会被删除
 * 通过 MediaStore 保存或读取数据全部由程序管理，不需要用户选择（通过 Storage Access Framework 管理文件是需要用户选择的）
 * 通过 MediaStore 保存文件时要注意，系统是通过你指定的 mime 来判断文件类型的
 *     图片目录只能保存图片类型的文件
 *     视频目录只能保存视频类型的文件
 *     音频目录只能保存音频类型的文件
 *     下载目录可以保存任意类型的文件
 *
 * 注：
 * 1、如果你卸载 app 后再重装 app，则会失去卸载 app 之前通过 MediaStore 创建的文件的所有权
 * 2、各种公共目录并不是物理文件夹，而是将其他相关文件夹中的相关文件集中管理
 *    比如视频目录会包括 DCIM 文件夹, Movies 文件夹, Pictures 文件夹中的视频文件
 */

package com.webabcd.androiddemo.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

import java.io.OutputStream;
import java.util.Locale;

public class Android11Demo2 extends AppCompatActivity {

    private final String LOG_TAG = "Android11Demo2";

    private Button _button0;
    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;
    private ImageView _imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_android11demo2);

        _button0 = findViewById(R.id.button0);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);
        _button4 = findViewById(R.id.button4);
        _imageView1 = findViewById(R.id.imageView1);

        sample();
    }

    private void sample() {
        _button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFile();
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryFileFirst();
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFile();
            }
        });

        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFileFirst();
            }
        });
    }

    // 请求“通过 MediaStore 读取非本 app 创建的文件”的权限
    // 先在 AndroidManifest.xml 中配置 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    // 然后按照如下方式请求（注：WRITE_EXTERNAL_STORAGE 权限已经无效了）
    private void requestPermission() {
        String[] storagePermissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, storagePermissions, 123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            Log.i(LOG_TAG, "申请的权限为：" + permissions[i] + "，申请结果：" + grantResults[i]);
        }
    }

    // 通过 MediaStore 在图片目录新建一个图片文件
    private void insertFile() {

        // 需要创建的图片对象
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.son01, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ContentValues contentValues = new ContentValues();
        // 指定文件保存的文件夹名称
        // Environment.DIRECTORY_PICTURES 值为 Pictures
        // Environment.DIRECTORY_DCIM 值为 DCIM
        // Environment.DIRECTORY_MOVIES 值为 Movies
        // Environment.DIRECTORY_MUSIC 值为 Music
        // Environment.DIRECTORY_DOWNLOADS 值为 Download
        // 如果想获取上述文件夹的真实地址可以通过这样的方式 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() 获取，他返回的值类似 /storage/emulated/0/Pictures
        contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/wanglei_test/");
        // 指定文件名
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "son");
        // 指定文件的 mime（比如 image/jpeg, application/vnd.android.package-archive 之类的）
        contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.ImageColumns.WIDTH, bitmap.getWidth());
        contentValues.put(MediaStore.Images.ImageColumns.HEIGHT, bitmap.getHeight());

        ContentResolver contentResolver = this.getContentResolver();
        // 通过 ContentResolver 在指定的公共目录下按照指定的 ContentValues 创建文件，会返回文件的 content uri（类似这样的地址 content://media/external/images/media/102）
        // 可以通过 MediaStore 保存文件的公共目录有：
        // MediaStore.Images.Media.EXTERNAL_CONTENT_URI - 图片目录，只能保存 mime 为图片类型的文件
        //     图片目录包括 Environment.DIRECTORY_PICTURES, Environment.DIRECTORY_DCIM 文件夹
        // MediaStore.Audio.Media.EXTERNAL_CONTENT_URI - 音频目录，只能保存 mime 为音频类型的文件
        //     音频目录包括 Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_RINGTONES 等等文件夹
        // MediaStore.Video.Media.EXTERNAL_CONTENT_URI - 视频目录，只能保存 mime 为视频类型的文件
        //     视频目录包括 DIRECTORY_MOVIES, Environment.DIRECTORY_PICTURES, Environment.DIRECTORY_DCIM 文件夹
        // MediaStore.Downloads.EXTERNAL_CONTENT_URI - 下载目录，可以保存任意类型的文件
        //     下载目录包括 Environment.DIRECTORY_DOWNLOADS 文件夹
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (uri == null) {
            Log.d(LOG_TAG, "创建文件失败");
        } else {
            Log.d(LOG_TAG, "创建文件成功：" + uri.toString());
        }

        // 写入图片数据
        OutputStream outputStream = null;
        try {
            outputStream = contentResolver.openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
        } catch (Exception ex) {
            Log.d(LOG_TAG, "写入数据失败：" + ex.toString());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception ex) {

            }
        }
    }

    // 通过 MediaStore 在图片目录遍历图片文件
    // 注：
    // 1、如果你想遍历出非本 app 创建的文件，则需要先申请 READ_EXTERNAL_STORAGE 权限
    // 2、如果你的 app 卸载后再重装的话系统不会认为是同一个 app（也就是你卸载之前创建的文件，再次安装 app 后必须先申请 READ_EXTERNAL_STORAGE 权限后才能获取到）
    private int queryFileFirst() {

        int contentId = -1;

        // 通过 ContentResolver 遍历指定公共目录中的文件
        ContentResolver contentResolver = this.getContentResolver();
        // 第 2 个参数（projection）：指定需要返回的字段，null 会返回所有字段
        // 第 3 个参数（selection）：查询的 where 语句，类似 xxx = ?
        // 第 4 个参数（selectionArgs）：查询的 where 语句的值，类似 new String[] { xxx }
        // 第 5 个参数（selectionArgs）：排序语句，类似 xxx DESC
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        Log.d(LOG_TAG, "count:" + cursor.getCount());
        while (cursor.moveToNext()) {
            // 比如这个地址 content://media/external/images/media/102 它的后面的 102 就是它的 id
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            // 获取文件名
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
            // 获取文件的真实路径，类似 /storage/emulated/0/Pictures/wanglei_test/son.jpg
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.RELATIVE_PATH));

            Log.d(LOG_TAG, String.format("id:%d, title:%s, path:%s", id, title, path));
            contentId = id;
        }
        cursor.close();

        // 返回指定的公共目录中的第一个文件的 id
        return contentId;
    }

    // 通过 MediaStore 读取图片目录中的图片，并显示
    private void showFile() {
        // 先拿到需要显示的图片的 content uri（类似这样的地址 content://media/external/images/media/102）
        int contentId = queryFileFirst();
        Uri contentUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + contentId);
        Log.d(LOG_TAG, "show uri:" + contentUri);

        Cursor cursor = null;
        try {
            String[] projection = { MediaStore.Images.Media.DATA };
            cursor = this.getContentResolver().query(contentUri, projection, null, null, null);
            cursor.moveToFirst();
            // 拿到指定的 content uri 的真实路径
            String path =  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            Log.d(LOG_TAG, String.format(Locale.ENGLISH, "contentUri:%s, path:%s", contentUri, path));

            // 显示指定路径的图片
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            _imageView1.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // 通过 MediaStore 删除图片目录中的图片文件（只能删除本 app 创建的文件）
    // 注：如果你的 app 卸载后再重装的话系统不会认为是同一个 app（也就是你卸载之前创建的文件，再次安装 app 后是无法通过这种方式删除它的）
    private void deleteFileFirst() {

        try {
            // 先拿到需要删除的图片的 content uri（类似这样的地址 content://media/external/images/media/102）
            int contentId = queryFileFirst();
            Uri contentUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + contentId);
            Log.d(LOG_TAG, "delete uri:" + contentUri);

            // 通过 ContentResolver 删除指定的 content uri 的文件
            ContentResolver contentResolver = this.getContentResolver();
            int deleteCount = contentResolver.delete(contentUri, null);
            Log.d(LOG_TAG, "delete count:" + deleteCount);
        } catch (Exception ex) {
            // 如果你想删除非本 app 创建的文件，就会收到类似这样的异常 android.app.RecoverableSecurityException: com.webabcd.androiddemo has no access to content://media/external/images/media/102
            Log.e(LOG_TAG, "delete error:" + ex.toString());
        }
    }
}