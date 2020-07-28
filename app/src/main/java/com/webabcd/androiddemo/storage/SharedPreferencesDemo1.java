/**
 * SharedPreferences 用户偏好数据的管理
 *
 *
 * 通过 SharedPreferences 在 /data/data/packagename/shared_prefs 中管理 xml 文件，每个 xml 文件中可以保存多条 key/value 数据
 * 实例化 SharedPreferences 对象时，指定的文件名不需包含后缀名，因为 SharedPreferences 管理的都是 xml 格式的文件，它会自动为文件添加 .xml 后缀名
 * 通过 SharedPreferences 是无法删除 shared_prefs 中的文件的（但是可以删除文件中的数据），需要通过 File 对象来删除文件
 *
 *
 * 注：如果用户在设置中的“应用信息”中单击“清除数据”的话，会删除 shared_prefs 中的全部文件
 */

package com.webabcd.androiddemo.storage;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.io.File;

public class SharedPreferencesDemo1 extends AppCompatActivity {

    // 需要操作的 shared_prefs 中的文件名（不需要后缀名）
    private final String FILE_NAME = "myTest";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_sharedpreferencesdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        // 在 shared_prefs 的指定文件中，保存多条 key/value 数据
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过指定的文件名实例化 SharedPreferences 对象（指定文件名时不需包含后缀名，SharedPreferences 保存的都是 xml 格式的文件，会自动为其添加 .xml 后缀名）
                SharedPreferences sp = SharedPreferencesDemo1.this.getSharedPreferences(FILE_NAME, SharedPreferencesDemo1.this.MODE_PRIVATE);
                // 通过 SharedPreferences.Editor 保存数据或删除数据
                SharedPreferences.Editor editor = sp.edit();

                // 通过 key/value 的形式在当前文件中保存如下类型的数据，可以同时保存多条数据
                // putString(), putBoolean(), putFloat(), putInt(), putLong(), putStringSet()
                editor.putString("myKey", "myValue");
                editor.putString("myKey2", "myValue2");

                // 保存
                editor.commit();

                mTextView1.setText("保存成功");
            }
        });

        // 从 shared_prefs 的指定文件中，读取指定 key 的数据
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过指定的文件名实例化 SharedPreferences 对象
                SharedPreferences sp = SharedPreferencesDemo1.this.getSharedPreferences(FILE_NAME, SharedPreferencesDemo1.this.MODE_PRIVATE);

                // 获取当前文件中的全部 key/value 数据
                // sp.getAll();

                // contains() - 当前文件中是否包含指定 key 的数据
                if (sp.contains("myKey")) {
                    mTextView1.setText("读取成功\n");
                    // 获取当前文件中各种类型的指定 key 的数据
                    // getString(), getBoolean(), getFloat(), getInt(), getLong(), getStringSet()
                    mTextView1.append(sp.getString("myKey", "defaultValue"));
                    mTextView1.append("\n");
                } else {
                    mTextView1.setText("没有发现 key 为“myKey”的数据");
                }

                mTextView1.append(sp.getString("myKey2", "defaultValue2"));
                mTextView1.append("\n");
                // 找不到指定 key 的数据则返回指定的默认值
                mTextView1.append(sp.getString("myKey3", "defaultValue3"));
            }
        });

        // 从 shared_prefs 的指定文件中，删除指定 key 的数据
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过指定的文件名实例化 SharedPreferences 对象
                SharedPreferences sp = SharedPreferencesDemo1.this.getSharedPreferences(FILE_NAME, SharedPreferencesDemo1.this.MODE_PRIVATE);
                // 通过 SharedPreferences.Editor 保存数据或删除数据
                SharedPreferences.Editor editor = sp.edit();

                // 删除当前文件中指定 key 的数据
                // editor.remove("myKey");
                // 删除当前文件中的全部数据（不会删除文件本身）
                editor.clear();

                // 保存
                editor.commit();

                mTextView1.setText("数据删除成功");
            }
        });

        // 删除 shared_prefs 中的文件
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过 File 对象删除 shared_prefs 中的文件
                File file= new File("/data/data/" + getPackageName() + "/shared_prefs",FILE_NAME + ".xml");
                if (file.exists()) {
                    file.delete();
                }

                mTextView1.setText("文件删除成功");
            }
        });
    }
}
