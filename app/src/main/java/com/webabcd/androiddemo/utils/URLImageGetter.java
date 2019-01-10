/**
 * 实现 Html.ImageGetter 接口，用于解析并呈现 img 标签
 *
 * 本例可以显示本地图片，drawable 图片，http 图片
 */

package com.webabcd.androiddemo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLImageGetter implements Html.ImageGetter {
    private Context _context;
    private TextView _textView;

    public URLImageGetter(TextView textView, Context context) {
        this._context = context;
        this._textView = textView;
    }

    // 将指定的 url 转换为一个 Drawable 对象
    @Override
    public Drawable getDrawable(String source) {
        if (source.indexOf("/mnt") == 0) { // 显示本地图片，路径类似 /mnt/sdcard/xxx.jpg
            Drawable drawable = Drawable.createFromPath(source);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            return drawable;
        } else if (Helper.isUInt(source)) { // 显示 drawable 中的图片，路径类似 R.drawable.img_sample_son
            Drawable drawable = _context.getResources().getDrawable(Integer.parseInt(source));
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            return drawable;
        } else { // 显示 http 图片
            URLImageGetterDrawable urlImageGetterDrawable = new URLImageGetterDrawable();

            // 启动一个异步任务去处理 img 的 src 的图片下载，并将图片转换为 Drawable 对象（这里的 URLImageGetterDrawable 对象会在异步任务中完成对其 drawable 属性的赋值）
            ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlImageGetterDrawable);
            asyncTask.execute(source);

            return urlImageGetterDrawable;
        }
    }

    // 异步加载图片
    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        private URLImageGetterDrawable _urlImageGetterDrawable;

        public ImageGetterAsyncTask(URLImageGetterDrawable urlImageGetterDrawable) {
            this._urlImageGetterDrawable = urlImageGetterDrawable;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            // 下载指定 url 的图片，并将其转换为 Drawable 对象
            String source = params[0];
            Drawable bitmapDrawable = fetchDrawable(source);

            return bitmapDrawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // 得到了 img 的 src 指定图片的 Drawable 对象，将其保存到 URLImageGetterDrawable 的 drawable 属性中以便后续的绘制
            _urlImageGetterDrawable.drawable = result;
            // 更新 TextView 的高度为：当前高度 + 图片的高度
            // 调用 setHeight() 后，会自动调用 invalidate() 从而触发 URLImageGetterDrawable 的 draw()，从而绘制之前获取到的 Drawable 对象
            URLImageGetter.this._textView.setHeight((URLImageGetter.this._textView.getHeight() + result.getIntrinsicHeight()));
        }


        // 下载指定 url 的图片，并将其转换为 Drawable 对象
        private Drawable fetchDrawable(String urlString) {
            try {
                // 获取 url 指定资源的 InputStream 对象
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();

                // 将 InputStream 转换为 Drawable
                Drawable drawable = Drawable.createFromStream(is, "URLImageGetter");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                return drawable;
            } catch (Exception e) {
                return null;
            }
        }
    }
}