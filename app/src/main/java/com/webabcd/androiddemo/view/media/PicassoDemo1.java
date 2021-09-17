/**
 * Picasso 基础
 * 需要在 app 的 build.gradle 中配置好 implementation 'com.squareup.picasso:picasso:x.x.x'
 *
 * 注：对比 Picasso 和 Glide
 * 1、Picasso 库比较小，Glide 库比较大
 * 2、Picasso 不支持 gif 动画，Glide 支持 gif 动画
 * 3、缓存对比
 *   Picasso 下载图片后会完整保存，每次显示时 resize 之后再显示
 *   Glide 的缓存逻辑是可以设置的
 */

package com.webabcd.androiddemo.view.media;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.webabcd.androiddemo.R;

public class PicassoDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "PicassoDemo1";

    private ImageView _imageView1;
    private ImageView _imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_media_picassodemo1);

        _imageView1 = findViewById(R.id.imageView1);
        _imageView2 = findViewById(R.id.imageView2);

        sample1();
        sample2();
    }

    // Picasso 基础
    private void sample1() {

        Picasso.with(this)
                // 需要显示的图片地址
                .load("https://css.itv.com.cn/styles/images/t.jpg")
                // 图片加载完成前显示的占位图片
                .placeholder(R.mipmap.ic_launcher)
                // 图片加载失败时显示的占位图片
                .error(R.mipmap.ic_launcher_round)
                // 禁用本地存储缓存（默认是开启的）。缓存的默认目录是 data/data/packagename/cache/picasso-cache
                .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
                // 禁用本地内存缓存。内存缓存用的是 LRU（Least Recently Used的）淘汰策略，即“如果数据最近被访问过，那么将来被访问的几率也更高”
                .memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE)
                // 将图片数据显示到指定的 ImageView 控件上
                .into(_imageView1);
    }

    // Picasso 监听图片加载的结果
    private void sample2() {

        Picasso.with(this)
                .load("https://css.itv.com.cn/styles/images/t.jpg")
                // 监听图片加载的结果
                .into(_imageView2, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "onSuccess");
                    }
                    @Override
                    public void onError() {
                        Log.d(LOG_TAG, "onError");
                    }
                });
    }
}