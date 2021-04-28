/**
 * 文件和文件夹操作
 *
 *  本例演示如何通过 File 对象操作文件和文件夹
 *  1、新建文件或覆盖文件并写入数据（自动创建文件夹）
 *  2、文件存在则追加数据，文件不存在则新建文件并写入数据（自动创建文件夹）
 *  3、读取文件内容
 *  4、获取文件列表和文件夹列表
 *  5、删除文件和文件夹
 *  6、文件重命名，文件复制，文件移动
 *
 * File - 可以代表文件或文件夹（通过指定地址来实例化）
 *     getParentFile() - 获取父文件夹
 *     getCanonicalPath() - 获取绝对路径
 *     exists() - 判断文件或文件夹是否存在
 *     isFile(), isDirectory() - 判断 File 对象是代表文件还是文件夹
 *         一个不存在的地址即不是文件也不是文件夹，所以应该先通过 exists() 判断地址存在，然后才能判断其是文件还是文件夹
 *         针对一个不存在的文件地址创建目录时要注意，需要调用 file.getParentFile().mkdirs() 创建目录，如果你使用的是 file.mkdirs() 则会将文件地址创建为目录
 *     mkdir() - 创建当前路径的最后一级的文件夹（返回值代表是否成功）
 *     mkdirs() - 创建当前路径的全部级别的文件夹（返回值代表是否成功）
 *     list() - 获取文件夹中的全部文件和文件夹的名称
 *     listFiles() - 获取文件夹中的全部文件和文件夹对象
 *     getName() - 文件或文件夹的名称
 *     lastModified(), setLastModified()  - 文件或文件夹的最后修改日期的时间戳
 *     length() - 文件的大小（单位：字节）
 *     delete() - 删除文件（返回值代表是否成功）
 *     renameTo() - 文件重命名
 *     createNewFile() - 创建空的新文件（如果文件已存在则不创建，并返回 false）
 *
 * FileOutputStream - 写文件的对象
 *     write() - 写入数据
 *     close() - 关闭流
 *
 * FileInputStream - 读文件的对象
 *     read() - 读取数据
 *     close() - 关闭流
 *
 *
 * 注：
 * 1、本例操作的是 files 目录（通过 getFilesDir() 获取）中的文件和文件夹。关于各种存储路径和权限请求的相关说明请参见 storage/StorageDemo3
 * 2、如果是 6.0 或以上系统（支持多用户），其 files 目录类似 /data/user/n/packagename/files （其中的 n 为整数，代表不同的用户，如果就一个用户那么 n 就是 0）
 *
 *
 * 另：
 * 关于 File 的 getPath(), getAbsolutePath(), getCanonicalPath() 的区别
 * 如果你定义 File 时用的是绝对路径，则这 3 个方法返回的数据是一样的
 * 如果你定义 File 时用的是相对路径，请看下面的说明
 * File file = new File(".\\test.txt");
 * file.getPath() - .\test.txt
 * file.getAbsolutePath() - c:\directory1\directory2\.\test.txt
 * file.getCanonicalPath() - c:\directory1\directory2\test.txt
 */

package com.webabcd.androiddemo.storage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class StorageDemo2 extends AppCompatActivity {

    private final String FILE_NAME = "/myDirectory/myFile.txt";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_storagedemo2);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }


    private void sample() {
        // 新建文件或覆盖文件并写入数据（自动创建文件夹）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 通过指定的地址实例化 File 对象（File 对象可以代表文件或文件夹）
                    File file = new File(getFilesDir(), FILE_NAME);
                    // 本例中的 file 代表的是文件，调用其 getParentFile() 方法获取到的就是此文件的所属文件夹
                    File directory = file.getParentFile();
                    // isFile(), isDirectory() - 判断 File 对象是代表文件还是文件夹（注：一个不存在的地址即不是文件也不是文件夹）
                    // exists() - 判断文件或文件夹是否存在
                    if (!directory.exists()) {
                        // mkdir() - 创建当前路径的最后一级的文件夹（返回值代表是否成功）
                        // mkdirs() - 创建当前路径的全部级别的文件夹（返回值代表是否成功）
                        boolean result = directory.mkdirs();
                    }

                    String stringContent = Helper.formatDate(new Date(), "HH:mm:ss\n");
                    byte[] bytesContent = stringContent.getBytes(StandardCharsets.UTF_8);

                    // 实例化 FileOutputStream 对象（没有文件则新建，有文件则覆盖）
                    FileOutputStream fos = new FileOutputStream(file);
                    // 写入数据
                    fos.write(bytesContent);
                    // 关闭流
                    fos.close();

                    mTextView1.setText("操作成功");
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 文件存在则追加数据，文件不存在则新建文件并写入数据（自动创建文件夹）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 通过指定的地址实例化 File 对象（File 对象可以代表文件或文件夹）
                    File file = new File(getFilesDir(), FILE_NAME);
                    // 本例中的 file 代表的是文件，调用其 getParentFile() 方法获取到的就是此文件的所属文件夹
                    File directory = file.getParentFile();
                    // isFile(), isDirectory() - 判断 File 对象是代表文件还是文件夹（注：一个不存在的地址即不是文件也不是文件夹）
                    // exists() - 判断文件或文件夹是否存在
                    if (!directory.exists()) {
                        // mkdir() - 创建当前路径的最后一级的文件夹（返回值代表是否成功）
                        // mkdirs() - 创建当前路径的全部级别的文件夹（返回值代表是否成功）
                        boolean result = directory.mkdirs();
                    }

                    String stringContent = Helper.formatDate(new Date(), "HH:mm:ss\n");
                    byte[] bytesContent = stringContent.getBytes(StandardCharsets.UTF_8);

                    // 实例化 FileOutputStream 对象，第 2 个参数传 true 代表追加数据（没有文件则新建，有文件则追加）
                    FileOutputStream fos = new FileOutputStream(file, true);
                    // 写入数据
                    fos.write(bytesContent);
                    // 关闭流
                    fos.close();

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
                    // 通过指定的地址实例化 File 对象
                    File file = new File(getFilesDir(), FILE_NAME);

                    // 实例化 FileInputStream 对象
                    FileInputStream fileInputStream = new FileInputStream(file);
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

        // 获取文件列表和文件夹列表
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTextView1.setText(getFilesDir() + FILE_NAME + " 中的文件列表和文件夹列表如下：\n");

                    // 通过指定的地址实例化 File 对象
                    File file = new File(getFilesDir(), FILE_NAME);
                    // 获取 file.getParentFile() 文件夹中的全部文件和文件夹的名称
                    // String[] fileList = file.getParentFile().list();
                    // 获取 file.getParentFile() 文件夹中的全部文件和文件夹对象
                    File[] fileList = file.getParentFile().listFiles();
                    for (File f : fileList) {
                        // getName() - 文件或文件夹的名称
                        // isFile(), isDirectory() - 判断 File 对象是代表文件还是文件夹（注：一个不存在的地址即不是文件也不是文件夹）
                        // lastModified() - 文件或文件夹的最后修改日期的时间戳（此值可以通过 setLastModified() 方法修改）
                        // length() - 文件的大小（单位：字节）
                        mTextView1.append(String.format("%s, %b, %d, %s\n", f.getName(), f.isDirectory(), f.length(), Helper.formatDate(new Date(f.lastModified()), "yyyy-MM-dd HH:mm:ss")));
                    }
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 删除文件和文件夹
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTextView1.setText("");

                    // 通过指定的地址实例化 File 对象
                    File file = new File(getFilesDir(), FILE_NAME);
                    // 获取 file.getParentFile() 文件夹中的全部文件和文件夹对象
                    File[] fileList = file.getParentFile().listFiles();
                    for (File f : fileList) {
                        // 删除文件或文件夹（返回值代表是否成功）
                        boolean result = f.delete();
                        mTextView1.append("操作结果：" + result + "\n");
                    }
                    // 删除 file.getParentFile() 文件夹（返回值代表是否成功）
                    boolean result = file.getParentFile().delete();
                    mTextView1.append("操作结果：" + result + "\n");
                } catch (Exception ex) {
                    mTextView1.setText("操作失败：" + ex.toString());
                }
            }
        });

        // 文件重命名，文件复制，文件移动
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView1.setText("通过 renameTo() 对文件重命名");
                mTextView1.append("\n");
                mTextView1.setText("文件复制（读取文件内容，并将内容保存到另一个地址）");
                mTextView1.append("\n");
                mTextView1.setText("文件移动（读取文件内容，并将内容保存到另一个地址，然后删除源文件）");
            }
        });
    }
}

