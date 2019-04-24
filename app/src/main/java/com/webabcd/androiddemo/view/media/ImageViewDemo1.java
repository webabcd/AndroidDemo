/**
 * ImageView - 图片控件
 */

package com.webabcd.androiddemo.view.media;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

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
        _imageView4.setImageDrawable(getResources().getDrawable(R.drawable.img_sample_son));

        // 设置 background
        // _imageView4.setBackground(getResources().getDrawable(R.drawable.img_sample_son));
    }
}
