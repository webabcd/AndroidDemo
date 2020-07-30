/**
 * 本例用于演示如何调用 ContentProvider
 * 正常演示此示例需要安装 AndroidDemoIpc 项目生成的 app
 *
 * 注：本例调用的 ContentProvider 的具体实现请参见 AndroidDemoIpc 项目中的 ContentProviderDemo1.java
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ContentProviderDemo1 extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_contentproviderdemo1);

        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        String message = "";
        Cursor cursor = null;
        try {
            // 从指定 url 的 ContentProvider 中查询数据
            Uri uri = Uri.parse("content://cn.webabcd.contentProviderDemo1/api");
            cursor = this.getContentResolver().query(uri, null, null, null, null);

            if (cursor == null) {
                message = "无数据";
            } else {
                // 从 cursor 中获取数据
                while (cursor.moveToNext()) {
                    String key = cursor.getString(cursor.getColumnIndex("key"));
                    String value = cursor.getString(cursor.getColumnIndex("value"));
                    message += key + ":" + value + "\n";
                }
            }
        } catch (Exception e) {
            message = e.toString();
        } finally {
            // 关闭 cursor
            if (cursor != null) {
                cursor.close();
            }
        }

        mTextView1.setText(message);
    }
}