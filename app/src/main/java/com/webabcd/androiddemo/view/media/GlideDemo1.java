/**
 * Glide 基础
 * 需要在 app 的 build.gradle 中配置好 implementation 'com.com.github.bumptech.glide:glide:x.x.x'
 *
 * 注：对比 Picasso 和 Glide
 * 1、Picasso 库比较小，Glide 库比较大
 * 2、Picasso 不支持 gif 动画，Glide 支持 gif 动画
 * 3、缓存对比
 *   Picasso 下载图片后会完整保存，每次显示时 resize 之后再显示
 *   Glide 的缓存逻辑是可以设置的
 */

package com.webabcd.androiddemo.view.media;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webabcd.androiddemo.R;

public class GlideDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "GlideDemo1";

    private ImageView _imageView1;
    private ImageView _imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_media_glidedemo1);

        _imageView1 = findViewById(R.id.imageView1);
        _imageView2 = findViewById(R.id.imageView2);

        sample1();
        sample2();
    }

    // Glide 基础
    private void sample1() {

        Glide.with(this)
                // 需要显示的图片地址
                .load("https://css.itv.com.cn/styles/images/t.jpg")
                // 图片加载完成前显示的占位图片
                .placeholder(R.mipmap.ic_launcher)
                // 图片加载失败时显示的占位图片
                .error(R.mipmap.ic_launcher_round)
                // 禁用内存缓存，默认是开启的
                .skipMemoryCache(true)
                // 指定磁盘缓存逻辑
                // AUTOMATIC - 默认值
                //     加载网络图片，会缓存原始图片数据，每次显示的时候先 resize 然后再显示
                //     加载本地图片，会缓存 resize 之后的图片数据，每次显示的时候有对应的尺寸就从缓存读取，否则会重新加载原始图片
                // RESOURCE - 只缓存 resize 之后的图片数据
                // DATA - 只缓存原始图片数据
                // ALL - 加载网络图片会缓存 resize 之后的图片数据和原始图片数据，加载本地图片只会缓存 resize 之后的图片数据
                // NONE - 不缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                // 将图片数据显示到指定的 ImageView 控件上
                .into(_imageView1);
    }

    // Glide 监听图片加载的结果
    private void sample2() {

        Glide.with(this)
                .load("https://css.itv.com.cn/styles/images/t.jpg")
                // 监听图片加载的结果
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // 图片加载失败
                        Log.d(LOG_TAG, "onLoadFailed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 图片加载成功
                        Log.d(LOG_TAG, "onResourceReady");
                        return false;
                    }
                })
                .into(_imageView2);
    }
}