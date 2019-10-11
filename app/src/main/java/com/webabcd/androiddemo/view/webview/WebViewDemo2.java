/**
 * WebView 和 javascript 交互（本例所用 html 请参见 /assets/WebViewDemo2.html）
 *     android 调用 js 可以通过 loadUrl() 的方式（无法获取返回值）或 evaluateJavascript() 的方式（可以获取返回值）
 *     js 调用 android 可以通过 addJavascriptInterface() 和 @JavascriptInterface 将指定对象的指定方法注册到 html 中
 *
 * 注：js 调用 android 也可以通过拦截 url 跳转的方式实现，详见 view/webview/WebViewDemo3.java
 */

package com.webabcd.androiddemo.view.webview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class WebViewDemo2 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_webview_webviewdemo2);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mWebView1 = findViewById(R.id.webView1);

        sample();
    }

    private void sample() {
        // 启用 javascript 支持
        WebSettings webSettings = mWebView1.getSettings();
        webSettings.setJavaScriptEnabled(true);


        // addJavascriptInterface(Object object, String name) - 注册 js 对象到 html 中
        //     object - 在 android 端提供 js 映射函数的对象
        //     name - 注册到 html 中的 js 对象的名字
        mWebView1.addJavascriptInterface(this, "myObject");


        // 加载指定的 url
        mWebView1.loadUrl("file:///android_asset/WebViewDemo2.html");


        // android 调用 js（loadUrl() 方式）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView1.loadUrl("javascript:androidCallJs1('p1', 'p2')");
            }
        });


        // android 调用 js（evaluateJavascript() 方式）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // android 4.4 或以上版本支持 evaluateJavascript()
                if (Build.VERSION.SDK_INT >= 18) {
                    mWebView1.evaluateJavascript("javascript:androidCallJs2('p1', 'p2')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Toast.makeText(WebViewDemo2.this, "android 调用 js 的返回值: " + value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // 在 android 端提供 js 映射函数，必须使用 @JavascriptInterface 注释（注：android 4.2 之前版本不支持此注释，有安全漏洞）
    @JavascriptInterface
    public String jsCallAndroid(String p1, String p2) {
        // 本例中 js 可以通过如下方式调用 android 并获取返回结果：var result = myObject.jsCallAndroid("p1", "p2");
        Toast.makeText(WebViewDemo2.this, "js 通过 addJavascriptInterface() 的方式调用了 android " + p1 + p2, Toast.LENGTH_SHORT).show();
        return "ok";
    }

    // 释放资源
    @Override
    protected void onDestroy() {
        // 从父容器中移除 WebView
        ((ViewGroup) mWebView1.getParent()).removeView(mWebView1);
        // 移除 WebView 内的所有控件
        mWebView1.removeAllViews();
        // destroy() - 销毁
        mWebView1.destroy();
        mWebView1 = null;

        super.onDestroy();
    }
}
