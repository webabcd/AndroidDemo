/**
 * WebView 拦截 url 跳转，拦截 alert, confirm, prompt 弹出框，拦截文件选择框（本例所用 html 请参见 /assets/WebViewDemo3.html）
 *     shouldOverrideUrlLoading() - 拦截任意协议的 url 跳转（通过自定义协议和此特性可以实现 js 调用 android）
 *     onJsAlert() - 拦截 alert 框（WebView 不支持 js 的 alert 框，但是可以通过此特性在 android 中模拟实现 alert 框；也可以通过自定义协议和此特性实现 js 调用 android）
 *     onJsConfirm() - 拦截 confirm 框（WebView 不支持 js 的 confirm 框，但是可以通过此特性在 android 中模拟实现 confirm 框；也可以通过自定义协议和此特性实现 js 调用 android 并返回 bool 值）
 *     onJsPrompt() - 拦截 prompt 框（WebView 不支持 js 的 prompt 框，但是可以通过此特性在 android 中模拟实现 prompt 框；也可以通过自定义协议和此特性实现 js 调用 android 并返回字符串值）
 *     onShowFileChooser() - 拦截文件选择框（WebView 不支持 js 的文件选择框，但是可以通过此特性在 android 中模拟实现文件选择框，然后再将用户选择文件的结果告知给页面）
 */

package com.webabcd.androiddemo.view.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.Set;

public class WebViewDemo3 extends AppCompatActivity {

    private WebView mWebView1;

    private ValueCallback<Uri[]> mFilePathCallback;
    private final static int FILE_CHOOSER_RESULT_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_webview_webviewdemo3);

        mWebView1 = findViewById(R.id.webView1);

        sample();
    }

    private void sample() {
        // 启用 javascript 支持
        WebSettings webSettings = mWebView1.getSettings();
        webSettings.setJavaScriptEnabled(true);


        // 加载指定的 url
        mWebView1.loadUrl("file:///android_asset/WebViewDemo3.html");


        mWebView1.setWebViewClient(new WebViewClient() {
            // 通过 shouldOverrideUrlLoading() 拦截 url 跳转
            // 任意协议的 url 跳转都会被拦截，本例演示如何通过此特性来实现 js 调用 android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                // scheme - url 的 scheme，本例需要拦截的 scheme 为 js
                // authority - url 的 authority，本例需要拦截的 authority 为 cn.webabcd.jscallandroid
                if (uri.getScheme().equalsIgnoreCase("js")) {
                    if (uri.getAuthority().equalsIgnoreCase("cn.webabcd.jscallandroid")) {
                        // 通过 url 的 query 来获取 js 传递过来的数据
                        Set<String> parameterNames = uri.getQueryParameterNames();
                        Toast.makeText(WebViewDemo3.this, "android 拦截了 url 跳转 " + uri.getQueryParameter("p1") + uri.getQueryParameter("p2"), Toast.LENGTH_SHORT).show();
                    }

                    // 已处理
                    return true;
                }

                // 这里会返回 false 就是不拦截了
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView1.setWebChromeClient(new WebChromeClient() {
            // 通过 onJsAlert() 拦截 alert 框（WebView 不支持 js 的 alert 框，但是可以通过此特性在 android 中模拟实现 alert 框；也可以通过自定义协议和此特性实现 js 调用 android）
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 通过 AlertDialog 实现一个类似 js 中的 alert 框
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewDemo3.this);
                builder.setTitle("alert");
                // message 就是 js 中 alert() 传递过来的信息
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 结束 js 中的 alert 框（必须加这句，否则页面就死掉了）
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                // 已处理
                return true;
            }


            // 通过 onJsConfirm() 拦截 confirm 框（WebView 不支持 js 的 confirm 框，但是可以通过此特性在 android 中模拟实现 confirm 框；也可以通过自定义协议和此特性实现 js 调用 android 并返回 bool 值）
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                // 通过 AlertDialog 实现一个类似 js 中的 confirm 框
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewDemo3.this);
                builder.setTitle("confirm");
                // message 就是 js 中 confirm() 传递过来的信息
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 为 js 中的 confirm 框返回 true
                        result.confirm();
                    }
                });
                builder.setNeutralButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 为 js 中的 confirm 框返回 false
                        result.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                // 已处理
                return true;
            }


            // 通过 onJsPrompt() 拦截 prompt 框（WebView 不支持 js 的 prompt 框，但是可以通过此特性在 android 中模拟实现 prompt 框；也可以通过自定义协议和此特性实现 js 调用 android 并返回字符串值）
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                // 通过 AlertDialog 和 EditText 实现一个类似 js 中的 prompt 框
                final EditText editText = new EditText(WebViewDemo3.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewDemo3.this);
                builder.setTitle("prompt");
                // message 就是 js 中 prompt() 传递过来的信息
                builder.setMessage(message);
                builder.setView(editText);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 为 js 中的 prompt 框返回数据
                        result.confirm(editText.getText().toString());
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                // 已处理
                return true;
            }


            // 通过 onShowFileChooser() 拦截文件选择框（WebView 不支持 js 的文件选择框，但是可以通过此特性在 android 中模拟实现文件选择框，然后再将用户选择文件的结果告知给页面）
            // 此方法在 android 5.0 或以上版本支持（android 5.0 以下版本可以重写别的方法）
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                // 保存回调对象，之后获取到用户的选择结果后通过调用此对象的 onReceiveValue() 方法为页面告知结果
                mFilePathCallback = filePathCallback;

                // 弹出文件选择框（选择文件后或退出后会调用 onActivityResult() 方法）
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "文件选择"), FILE_CHOOSER_RESULT_CODE);

                return true;
            }
        });
    }

    // 本例重写 onActivityResult() 方法的目的是获取用户在文件选择框中选择文件的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            Uri[] result = null;
            // Activity.RESULT_OK - 选择了文件
            // Activity.DONT_FINISH_TASK_WITH_ACTIVITY - 没有选择文件
            if (resultCode == Activity.RESULT_OK) {
                // 将用户的选择结果告知 js 中的文件选择框
                result = new Uri[]{Uri.parse(data.getDataString())};
            }
            mFilePathCallback.onReceiveValue(result);
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
