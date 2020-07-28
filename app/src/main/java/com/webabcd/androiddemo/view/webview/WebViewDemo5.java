/**
 * WebView 通过 post 加载 url，自定义请求 header，获取或设置 cookie
 *      postUrl() - 通过 post 方式加载指定的 url
 *      loadUrl() - 追加自定义 header 并加载指定的 url
 *      CookieManager - 用于管理 cookie 的类
 *
 *
 * 注：
 * 1、如果以上方法均不满足需求的话，可以通过 shouldInterceptRequest() 拦截 url 然后通过 URLConnection 等类以自定义的方式请求网络并返回数据
 * 2、关于 shouldInterceptRequest() 的基础请参见 view/webview/WebViewDemo5.java
 */

package com.webabcd.androiddemo.view.webview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class WebViewDemo5 extends AppCompatActivity {

    private TextView mTextView1;
    private WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_webview_webviewdemo5);

        mWebView1 = findViewById(R.id.webView1);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        // 启用 javascript 支持
        WebSettings webSettings = mWebView1.getSettings();
        webSettings.setJavaScriptEnabled(true);


        // postUrl() - 通过 post 方式加载指定的 url
        final String url = "https://www.baidu.com";
        String postData = "p1=p1&p2=p2";
        mWebView1.postUrl(url, Helper.stringToBytes(postData));


        // loadUrl() - 追加自定义 header 并加载指定的 url
        // Map<String, String> headers = new HashMap<String, String>();
        // headers.put("custom-header", "test");
        // mWebView1.loadUrl(url, headers);


        // 设置 cookie
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, "mycookie=webabcd");
        // 清除全部 cookie
        // cookieManager.removeAllCookies(null);
        // 持久化设置好的 cookie
        cookieManager.flush();


        // 获取 cookie
        mWebView1.setWebViewClient(new WebViewClient() {
            // 本例是在页面加载完成后获取 cookie 的，正常来说服务端是通过 response header 设置 cookie 的，所以拿到 response header 后就能拿到最新的 cookie 了
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieString = cookieManager.getCookie(url);
                mTextView1.setText(cookieString);
            }
        });
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