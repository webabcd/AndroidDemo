/**
 * WebView 基础
 *     loadUrl("https://www.baidu.com") - 加载一个 http 或 https 的网页
 *     loadUrl("file:///android_asset/xxx.html"), loadUrl("file:///android_res/xxx.html") - 加载包内的 html 文件
 *     loadUrl("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/xxx.html") - 加载存储中的 html 文件
 *     loadDataWithBaseURL(null, "html content", "text/html", "utf-8", null) - 加载指定的 html 字符串
 */

package com.webabcd.androiddemo.view.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class WebViewDemo1 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_webview_webviewdemo1);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);
        mWebView1 = findViewById(R.id.webView1);

        sample();
    }

    private void sample() {
        // clearCache(boolean includeDiskFiles) - 清除缓存
        //     false - 仅清除内存缓存
        //     true - 清除内存缓存和硬盘缓存
        mWebView1.clearCache(true);
        // clearHistory() - 清除访问的历史记录
        mWebView1.clearHistory();
        // clearFormData() - 清除表单的自动完成填充的数据
        mWebView1.clearFormData();


        // canGoBack(), goBack(), canGoForward(), goForward() - 页面导航管理，含义分别为：是否可以后退，后退网页，是否可以前进，前进网页
        // canGoBackOrForward(int steps), goBackOrForward(int steps) - 能后退或前进的步数，后退或前进指定步数的网页。负数为后退，正式为前进
        // reload(), stopLoading() - 刷新页面，停止加载


        // 当 WebView 不可见或切换到后台时，通过如下接口来降低 cpu 的占用
        // onPause(), onResume() - 停止 js 的执行，恢复 js 的执行
        // pauseTimers(), resumeTimers() - 停止 js 的计时器的执行，恢复 js 的计时器的执行


        // getSettings() - 获取 WebSettings 对象，用于对 WebView 做一些设置
        WebSettings webSettings = mWebView1.getSettings();

        // setJavaScriptEnabled() - 是否启用 javascript 支持
        webSettings.setJavaScriptEnabled(true);

        // setUseWideViewPort() - 是否启用对 html 中 viewport 的支持
        webSettings.setUseWideViewPort(true);
        // setLoadWithOverviewMode() - 是否需要缩小内容以适应屏幕的宽度
        webSettings.setLoadWithOverviewMode(true);

        // setSupportZoom() - 是否需要支持缩放
        webSettings.setSupportZoom(true);
        // setBuiltInZoomControls() - 是否启用 WebView 内置的缩放功能
        webSettings.setBuiltInZoomControls(true);
        // setDisplayZoomControls() - 是否显示缩放按钮
        webSettings.setDisplayZoomControls(false);

        // setCacheMode() - 指定缓存方式
        //     LOAD_DEFAULT - 根据 html 中的 cache-control 决定是否从网络上请求数据，默认值
        //     LOAD_CACHE_ONLY - 不请求网络，只读取本地缓存数据
        //     LOAD_NO_CACHE - 不使用本地缓存，只从网络请求数据.
        //     LOAD_CACHE_ELSE_NETWORK - 优先从本地缓存获取数据，没有的话则请求网络
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setAllowFileAccess() - 是否可以访问本地存储文件（比如通过 file 协议访问 sd 卡中的资源）（注：通过 file 协议访问包内资源不会受此影响）
        webSettings.setAllowFileAccess(true);
        // setJavaScriptCanOpenWindowsAutomatically() - 是否支持 javascript 打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // setLoadsImagesAutomatically() - 是否自动加载图片资源
        webSettings.setLoadsImagesAutomatically(true);
        // setDefaultTextEncodingName() - 指定编码格式
        webSettings.setDefaultTextEncodingName("UTF-8");
        // setMixedContentMode() - 设置 http 和 https 混用的模式
        //     MIXED_CONTENT_ALWAYS_ALLOW - 允许加载的 https 页面引用 http 资源
        //     MIXED_CONTENT_NEVER_ALLOW - 不允许加载的 https 页面引用 http 资源
        //     MIXED_CONTENT_COMPATIBILITY_MODE - 加载 https 页面后，由系统决定其引用的哪类 http 资源可以加载，哪类 http 资源不可以加载
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        // loadUrl() - 加载指定的 url
        mWebView1.loadUrl("https://www.baidu.com");
        // setWebViewClient() - 事件监听
        mWebView1.setWebViewClient(webViewClient);
        // setWebChromeClient() -  事件监听
        mWebView1.setWebChromeClient(webChromeClient);
    }

    // WebViewClient 事件监听
    private WebViewClient webViewClient = new WebViewClient() {
        // 开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mTextView1.setText("onPageStarted");
        }

        // 加载完成
        @Override
        public void onPageFinished(WebView view, String url) {
            mTextView1.setText("onPageFinished");
        }

        // 加载任意资源时的回调（比如请求 html, css, js, jpg 等资源时都会触发此函数）
        @Override
        public void onLoadResource(WebView view, String url) {
            mTextView3.setText(String.format("onLoadResource: %s", url));
        }

        // 加载失败（errorCode 为 http status code）
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){

        }

        // 加载 https 失败
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 取消
            // handler.cancel();

            // 继续
            // handler.proceed();
        }
    };

    // WebChromeClient 事件监听
    private WebChromeClient webChromeClient = new WebChromeClient() {
        // 加载进度（0 - 100 之间）
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mTextView1.setText(String.format("onProgressChanged: %d", newProgress));
        }

        // 拿到 html 中的 title
        @Override
        public void onReceivedTitle(WebView view, String title) {
            mTextView2.setText(String.format("onReceivedTitle: %s", title));
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 用户按下返回键时，如果页面可后退则后退
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView1.canGoBack()) {
            mWebView1.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
