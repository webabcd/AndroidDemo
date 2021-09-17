/**
 * ImageView - 图片控件
 *
 * 本例用于演示 ImageView 基础
 *
 * 注：关于网络图片的显示和缓存，以及图片的处理之类的建议使用 Picasso 框架
 */

package com.webabcd.androiddemo.view.media;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class ImageViewDemo1 extends AppCompatActivity {

    private ImageView _imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_media_imageviewdemo1);

        _imageView4 = (ImageView)findViewById(R.id.imageView4);

        sample();
    }

    private void sample() {
        // 设置 src
        _imageView4.setImageDrawable(Helper.id2drawable(this, R.drawable.img_sample_son));

        // 设置 background
        // _imageView4.setBackground(Helper.id2drawable(this, R.drawable.img_sample_son));

        // 如何显示一个网络图片呢？
        // 先把获取到的网络图片数据放到一个 InputStream 对象里，然后将其转为 Bitmap 对象，然后让 ImageView 显示这个 Bitmap 对象
        // Bitmap bitmap = BitmapFactory.decodeStream(InputStream);
        // _imageView4.setImageBitmap(bitmap);

        // 注：更简单的方法是用 Glide 库或 Picasso 库
    }
}
