/**
 * 通过 context 操作 files 目录中的文件
 *
 * 本例演示如何通过 context 提供的方法在 /data/data/packagename/files 目录中做如下操作
 * 1、新建文件或覆盖文件并写入数据
 * 2、文件存在则追加数据，文件不存在则新建文件并写入数据
 * 3、读取文件内容
 * 4、获取文件列表
 * 5、删除文件
 *
 *
 * 注：
 * 1、本例所用的方法是通过 context 来操作 /data/data/packagename/files 目录中的文件，用法简单，但是不够强大。更多的文件和文件夹操作的相关说明请参见 storage/StorageDemo2
 * 2、如果是 6.0 以上的系统（支持多用户），其 files 目录类似 /data/user/n/packagename/files （其中的 n 为整数，代表不同的用户，如果就一个用户那么 n 就是 0）
 */

package com.webabcd.androiddemo.storage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class StorageDemo1 extends AppCompatActivity {

    private final String FILE_NAME = "myTest.txt";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_storagedemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        // 新建文件或覆盖文件并写入数据
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String content = Helper.formatDate(new Date(), "HH:mm:ss\n");

                    // 通过 context 的 openFileOutput() 来实例化 FileOutputStream（Context.MODE_PRIVATE - 没有文件则新建，有文件则覆盖）
                    // 此种方式打开的文件是 /data/data/packagename/files 目录下的指定文件（不能有子目录）
                    FileOutputStream fileOutputStream = StorageDemo1.this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    // 写入文本数据
                    fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
                    // 关闭流
                    fileOutputStream.close();

                    mTextView1.setText("操作成功");
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 文件存在则追加数据，文件不存在则新建文件并写入数据
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String content = Helper.formatDate(new Date(), "HH:mm:ss\n");

                    // 通过 context 的 openFileOutput() 来实例化 FileOutputStream（Context.MODE_APPEND - 没有文件则新建，有文件则追加）
                    // 此种方式打开的文件是 /data/data/packagename/files 目录下的指定文件（不能有子目录）
                    FileOutputStream fileOutputStream = StorageDemo1.this.openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    // 写入文本数据
                    fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
                    // 关闭流
                    fileOutputStream.close();

                    mTextView1.setText("操作成功");
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 读取文件内容
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 通过 context 的 openFileOutput() 来实例化 FileOutputStream
                    // 此种方式打开的文件是 /data/data/packagename/files 目录下的指定文件（不能有子目录）
                    FileInputStream fileInputStream = StorageDemo1.this.openFileInput(FILE_NAME);
                    // 开缓存区，一点一点地读取文本数据
                    byte[] buffer = new byte[1024];
                    StringBuilder sb = new StringBuilder();
                    int length = 0;
                    while ((length = fileInputStream.read(buffer)) > 0) {
                        sb.append(new String(buffer, 0, length, StandardCharsets.UTF_8));
                    }
                    // 关闭流
                    fileInputStream.close();

                    mTextView1.setText(sb.toString());
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 获取文件列表
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTextView1.setText("/data/data/" + StorageDemo1.this.getPackageName() + " 中的文件列表如下：");

                    // 通过 context 的 fileList() 来获取 /data/data/packagename/files 目录中的文件名称列表
                    String[] fileList = StorageDemo1.this.fileList();
                    for (String fileName : fileList) {
                        mTextView1.append(fileName + "\n");
                    }
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 删除文件
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 通过 context 的 deleteFile() 删除 /data/data/packagename/files 目录中的指定名称的文件（不能有子目录）
                    // 删除成功返回 true；删除失败返回 false
                    boolean result = StorageDemo1.this.deleteFile(FILE_NAME);

                    mTextView1.setText("操作结果：" + result);

                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });
    }
}
