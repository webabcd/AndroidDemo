/**
 * 演示 view 剪裁
 */

package com.webabcd.androiddemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

public class ViewDemo3 extends AppCompatActivity {

    private ImageView _imageView1;
    private ImageView _imageView2;
    private ImageView _imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_viewdemo3);

        _imageView1 = findViewById(R.id.imageView1);
        _imageView2 = findViewById(R.id.imageView2);
        _imageView3 = findViewById(R.id.imageView3);

        sample1();
        sample2();
        sample3();
    }

    // 圆形剪裁
    private void sample1() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0,0,_imageView1.getWidth(), _imageView1.getHeight());
            }
        };
        _imageView1.setOutlineProvider(viewOutlineProvider);
        _imageView1.setClipToOutline(true);
    }

    // 矩形剪裁
    private void sample2() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(20,0,_imageView2.getWidth(), _imageView2.getHeight());
            }
        };
        _imageView2.setOutlineProvider(viewOutlineProvider);
        _imageView2.setClipToOutline(true);
    }

    // 圆角剪裁
    private void sample3() {
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,_imageView3.getWidth(), _imageView3.getHeight(), 20);
            }
        };
        _imageView3.setOutlineProvider(viewOutlineProvider);
        _imageView3.setClipToOutline(true);
    }
}