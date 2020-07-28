/**
 * WebView 拦截 url 请求并返回自定义数据
 *     shouldInterceptRequest() - 拦截 url 请求并返回自定义数据
 */

package com.webabcd.androiddemo.view.webview;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webabcd.androiddemo.R;

import java.io.IOException;
import java.io.InputStream;

public class WebViewDemo4 extends AppCompatActivity {

    private WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_webview_webviewdemo4);

        mWebView1 = findViewById(R.id.webView1);

        sample();
    }

    private void sample() {
        // 启用 javascript 支持
        WebSettings webSettings = mWebView1.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // loadUrl() - 加载指定的 url
        mWebView1.loadUrl("https://www.baidu.com");


        mWebView1.setWebViewClient(new WebViewClient() {
            // 拦截 url 请求并返回自定义数据（注：android 5.0 以下重写的方法为  @Override public WebResourceResponse shouldInterceptRequest(WebView view, String url)）
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {
                if (request != null && request.getUrl() != null) {
                    final Uri uri = request.getUrl();
                    if (uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")) {
                        if (uri.getPath().endsWith(".png")) {
                            // 拦截所有 http 或 https 的 .png 请求，并构造自定义的 WebResourceResponse
                            WebResourceResponse response = null;
                            try {
                                InputStream is = view.getContext().getAssets().open("son.jpg");
                                response = new WebResourceResponse("image/png", "UTF-8", is);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            return response;
                        }
                    }
                }

                // 这里会返回 null 就是不拦截了
                return super.shouldInterceptRequest(view, request);
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
