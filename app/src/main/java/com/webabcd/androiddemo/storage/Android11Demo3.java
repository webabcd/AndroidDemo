/**
 * 本例用于演示 Android 11 通过 Storage Access Framework 管理文件
 *
 * SAF（Storage Access Framework）不需要申请权限，但是需要弹出对框框，让用户选择地址
 */

package com.webabcd.androiddemo.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Android11Demo3 extends AppCompatActivity {

    private final String LOG_TAG = "Android11Demo3";

    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;
    private ImageView _imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_android11demo3);

        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);
        _button4 = findViewById(R.id.button4);
        _imageView1 = findViewById(R.id.imageView1);

        sample();
    }

    private void sample() {
        // 通过 SAF 保存一个文本文件
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFileText();
            }
        });

        // 通过 SAF 保存一个图片文件
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFileImage();
            }
        });

        // 通过 SAF 读取一个文本文件
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFileText();
            }
        });

        // 通过 SAF 读取一个图片文件
        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFileImage();
            }
        });
    }


    // 弹出创建文件对框框，由用户创建文本文件
    private void createFileText() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TITLE, "123.txt"); // 默认文件名
        // 弹出对话框，之后可以在 onActivityResult() 中获取用户的操作结果
        startActivityForResult(intent, 1);
    }
    // 写入文本数据（根据用户选择的 uri 地址）
    private void writeFileText(Uri uri) {
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = this.getContentResolver().openOutputStream(uri);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("i am webabcd");
        } catch (Exception ex) {
            Log.d(LOG_TAG, "写入数据失败：" + ex.toString());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ex) {

            }
        }
    }


    // 弹出创建文件对框框，由用户创建图片文件
    private void createFileImage() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TITLE, "123.jpg"); // 默认文件名
        // 弹出对话框，之后可以在 onActivityResult() 中获取用户的操作结果
        startActivityForResult(intent, 2);
    }
    // 写入图片数据（根据用户选择的 uri 地址）
    private void writeFileImage(Uri uri) {
        // 需要写入的图片数据
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.son01, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        OutputStream outputStream = null;
        try {
            outputStream = this.getContentResolver().openOutputStream(uri);
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


    // 弹出选择文件对话框，由用户选择文本文件
    private void chooseFileText() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        // 弹出对话框，之后可以在 onActivityResult() 中获取用户的操作结果
        startActivityForResult(intent, 3);
    }
    // 读取文本数据（根据用户选择的 uri 地址）
    private void readFileText(Uri uri) {
        // 获取用户选择的文件的文件名
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            Log.d(LOG_TAG, "file name：" + displayName);
            cursor.close();
        }

        // 读取文本数据
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            inputStream.close();

            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(LOG_TAG, "读取数据失败：" + ex.toString());
        }

    }


    // 弹出选择文件对话框，由用户选择图片文件
    private void chooseFileImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 4);
    }
    // 读取图片数据（根据用户选择的 uri 地址）
    private void readFileImage(Uri uri) {
        // 获取用户选择的文件的文件名
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            Log.d(LOG_TAG, "file name：" + displayName);
            cursor.close();
        }

        // 读取图片数据并显示
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();

            _imageView1.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.d(LOG_TAG, "读取数据失败：" + ex.toString());
        }
    }


    // 获取用户在弹出的文件操作对话框中的操作结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Activity.RESULT_OK - 相关操作完成
        // Activity.DONT_FINISH_TASK_WITH_ACTIVITY - 没有任何操作
        if (resultCode != Activity.RESULT_OK) {
            Log.d(LOG_TAG, "用户没有任何操作");
            return;
        }

        // 获取用户选择的文件或保存的文件的 uri 地址（类似 content://com.android.providers.downloads.documents/document/3）
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            Log.d(LOG_TAG, "uri: " + uri.toString());
        }
        if (uri == null) {
            Log.d(LOG_TAG, "没有 uri");
            return;
        }

        if (requestCode == 1) {
            // 写入文本数据
            writeFileText(uri);
        } else if (requestCode == 2) {
            // 写入图片数据
            writeFileImage(uri);
        } else if (requestCode == 3) {
            // 读取文本数据
            readFileText(uri);
        } else if (requestCode == 4) {
            // 读取图片数据
            readFileImage(uri);
        }
    }
}