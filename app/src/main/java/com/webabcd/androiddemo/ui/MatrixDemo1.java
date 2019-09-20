/**
 * Matrix 变换（用于做位移，旋转，缩放，扭曲等变换）
 *
 * Matrix - Matrix 变换
 *     public Matrix(Matrix src) - 根据已有矩阵来实例化矩阵
 *     setScale(), setRotate(), setTranslate(), setSkew() - 设置缩放、旋转、位移、扭曲。除了位移之外均可指定变换的中心点
 *         设置矩阵，会覆盖掉之前的值
 *     postScale(), postRotate(), postTranslate(), postSkew() - 后乘缩放、旋转、位移、扭曲。除了位移之外均可指定变换的中心点
 *         后乘矩阵，假设原始矩阵为 A，变换矩阵为 B，则 post 的含义是 A * B（即在之前矩阵的基础上再做变换）
 *     preScale(), preRotate(), preTranslate(), preSkew() - 前乘缩放、旋转、位移、扭曲。除了位移之外均可指定变换的中心点
 *         前乘矩阵：假设原始矩阵为 A，变换矩阵为 B，则 pre 的含义是 B * A
 *     setPolyToPoly() - 将一个指定的多边形区域扭曲到另一个指定的多边形区域
 *     reset() - 清除变换
 *
 *
 * 注：
 * 1、所有 Matrix 变换其实都是通过仿射矩阵实现的
 * 2、通过 Matrix 对象的 toShortString() 方法可以获取到此矩阵的 9 个值
 *
 * 关于仿射矩阵的说明：
 * |X|             |M11（默认值 1）      M21（默认值 0）       0|
 * |Y| = |x y 1| * |M12（默认值 0）      M22（默认值 1）       0|
 * |1|             |OffsetX（默认值 0）  OffsetY（默认值 0）   1|
 * X = x * M11 + y * M12 + OffsetX
 * Y = x * M21 + y * M22 + OffsetY
 */

package com.webabcd.androiddemo.ui;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

public class MatrixDemo1 extends AppCompatActivity {

    private ImageView _imageView1;

    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;
    private Button _button5;
    private Button _button6;
    private Button _button7;
    private Button _button8;
    private Button _button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_matrixdemo1);

        _imageView1 = findViewById(R.id.imageView1);

        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);
        _button4 = findViewById(R.id.button4);
        _button5 = findViewById(R.id.button5);
        _button6 = findViewById(R.id.button6);
        _button7 = findViewById(R.id.button7);
        _button8 = findViewById(R.id.button8);
        _button9 = findViewById(R.id.button9);

        // 演示如何设置 Matrix 变换
        sample1();
        // 演示如何后乘 Matrix 变换
        sample2();
        // 演示 setPolyToPoly() 的应用
        sample3();
        // 演示什么是仿射矩阵
        sample4();
        // 演示如何清除 Matrix 变换
        sample5();
    }

    // 演示如何设置 Matrix 变换
    private void sample1() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                // 指定缩放的倍数，后两个参数代表变换的中心点（默认值为 0,0）
                matrix.setScale(1.2f, 1.2f, 0f, 0f);
                _imageView1.setImageMatrix(matrix);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                // 指定顺时针旋转的角度，后两个参数代表变换的中心点（默认值为 0,0）
                matrix.setRotate(30f, 0f, 0f);
                _imageView1.setImageMatrix(matrix);
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                // 指定位移的像素数
                matrix.setTranslate(50f, 10f);
                _imageView1.setImageMatrix(matrix);
            }
        });

        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                // 指定扭曲度，后两个参数代表变换的中心点（默认值为 0,0）
                matrix.setSkew(0.1f, 0.3f, 0f, 0f);
                _imageView1.setImageMatrix(matrix);
            }
        });

    }

    // 演示如何后乘 Matrix 变换
    private void sample2() {
        _button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 根据已有矩阵来实例化矩阵
                Matrix matrix = new Matrix(_imageView1.getImageMatrix());
                // 后乘矩阵（即在之前矩阵的基础上再做变换）
                matrix.postScale(1.2f, 1.2f, 0f, 0f);
                _imageView1.setImageMatrix(matrix);
            }
        });

        _button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 根据已有矩阵来实例化矩阵
                Matrix matrix = new Matrix(_imageView1.getImageMatrix());
                // 后乘矩阵（即在之前矩阵的基础上再做变换）
                matrix.postTranslate(10f, 10f);
                _imageView1.setImageMatrix(matrix);
            }
        });
    }

    // 演示 setPolyToPoly() 的应用（poly 就是 polygon 的缩写）
    private void sample3() {
        _button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] src = {0, 0,                                            // 左上
                        _imageView1.getWidth(), 0,                              // 右上
                        _imageView1.getWidth(), _imageView1.getHeight(),        // 右下
                        0, _imageView1.getHeight()};                            // 左下

                float[] dst = {0, 0,                                            // 左上
                        _imageView1.getWidth(), 400,                            // 右上
                        _imageView1.getWidth(), _imageView1.getHeight() - 100,  // 右下
                        0, _imageView1.getHeight()};                            // 左下

                Matrix matrix = new Matrix();
                // 将 src 矩形区域扭曲到 dst 矩形区域，最后一个参数代表控制点的数量（范围在 0 - 4 之间）
                matrix.setPolyToPoly(src, 0, dst, 0, 4);
                _imageView1.setImageMatrix(matrix);
            }
        });
    }

    // 演示什么是仿射矩阵（所有 Matrix 变换其实都是通过仿射矩阵实现的）
    private void sample4() {
        /*
        仿射矩阵
        |X|             |M11（默认值 1）      M21（默认值 0）       0|
        |Y| = |x y 1| * |M12（默认值 0）      M22（默认值 1）       0|
        |1|             |OffsetX（默认值 0）  OffsetY（默认值 0）   1|

        X = x * M11 + y * M12 + OffsetX
        Y = x * M21 + y * M22 + OffsetY
         */
        _button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                // 设置仿射矩阵，其 9 个值分别为 M11, M12, OffsetX, M21, M22, OffsetY, 0, 0, 1
                matrix.setValues(new float[] { 1f, 0f, 50f, 0f, 1f, 10f, 0f, 0f, 1f });
                _imageView1.setImageMatrix(matrix);
            }
        });

        // 注：通过 Matrix 对象的 toShortString() 方法可以获取到此矩阵的 9 个值
    }

    // 演示如何清除 Matrix 变换
    private void sample5() {
        _button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix(_imageView1.getImageMatrix());
                // 复位 Matrix 变换
                matrix.reset();
                _imageView1.setImageMatrix(matrix);
            }
        });
    }
}
