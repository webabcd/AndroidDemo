/**
 * 本例用于演示剪切板的使用
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Date;

public class ClipboardDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "ClipboardDemo1";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private EditText mEditText1;
    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_clipboarddemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mEditText1 = findViewById(R.id.editText1);

        sample();
    }

    private void sample() {

        // 获取 ClipboardManager 对象
        mClipboardManager = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);

        // 设置剪切板的数据
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过 newPlainText() 构造剪切板文本数据（第 1 个参数没啥用，传 null 就行），类似的还有 newHtmlText(), newRawUri(), newUri(), newIntent()
                ClipData clipData = ClipData.newPlainText(null, Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                // 设置剪切板的数据
                mClipboardManager.setPrimaryClip(clipData);
            }
        });

        // 清除剪切板的数据
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // 清除剪切板的数据（api level 28 或以上支持）
                    mClipboardManager.clearPrimaryClip();
                }
            }
        });

        // 获取剪切板的数据
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取剪切板的数据
                ClipData clipData = mClipboardManager.getPrimaryClip();
                if (clipData != null && clipData.getItemCount() > 0) {
                    // 从剪切板的数据集中获取第一条数据
                    ClipData.Item item = clipData.getItemAt(0);
                    // 通过 getText() 获取 ClipData.Item 中的文本数据，类似的还有 getHtmlText(), getUri(), getIntent()
                    CharSequence text = item.getText();
                    mEditText1.setText(text);
                }
            }
        });

        // 添加剪贴板数据改变的监听器
        mClipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                // 剪切板数据发生改变就会走到这里
                Log.d(LOG_TAG, "onPrimaryClipChanged()");
            }
        });
        // 移除指定的剪贴板数据改变的监听器
        // mClipboardManager.removePrimaryClipChangedListener(listener);
    }
}