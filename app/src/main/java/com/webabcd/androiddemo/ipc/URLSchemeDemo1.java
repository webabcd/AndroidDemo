/**
 * 本例用于演示如何通过 URLScheme(deep link) 启动另一个 app
 * 正常演示此示例需要安装 AndroidDemoIpc 项目生成的 app
 *
 * 注：支持指定 URLScheme 的实现请参见 AndroidDemoIpc 项目中的 URLScheme1.java
 */

package com.webabcd.androiddemo.ipc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.List;

public class URLSchemeDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private TextView mTextView1;
    private WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_urlschemedemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mTextView1 = findViewById(R.id.textView1);
        mWebView1 = findViewById(R.id.webView1);

        sample();
    }

    // 判断指定的协议是否有效（即指定的 url 是否有 app 可以打开）
    private boolean schemeIsValid(String url) {
        PackageManager manager = this.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(url));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("webabcd://a.b.c/api?p1=v1&p2=v2");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception ex) {
                    // 如果指定的协议无效的话则会抛出类似如下的异常
                    // android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.VIEW dat=webabcd://a.b.c/api?p1=v1&p2=v2 }
                    mTextView1.setText(ex.toString());
                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断指定的协议是否有效
                boolean b1 = schemeIsValid("webabcd://a.b.c/api");
                boolean b2 = schemeIsValid("abc://a.b.c/api");
                mTextView1.append("webabcd://a.b.c/api, " + b1);
                mTextView1.append("\n");
                mTextView1.append("abc://a.b.c/api, " + b2);
            }
        });

        // 在 html 中通过 js 打开指定的协议，从而唤起另一个 app
        WebSettings webSettings = mWebView1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView1.loadUrl("file:///android_asset/URLSchemeDemo1.html");
    }
}
