/**
 * 演示如何通过 inflate 动态加载布局文件
 *
 * 有 3 种方式
 * 1、View view = View.inflate(context, int resource, ViewGroup root);
 *    相当于 LayoutInflater.from(context).inflate(int resource, ViewGroup root, true);
 * 2、View view = LayoutInflater.from(context).inflate(int resource, ViewGroup root);
 *    相当于 LayoutInflater.from(context).inflate(int resource, ViewGroup root, true);
 * 3、View view = LayoutInflater.from(context).inflate(int resource, ViewGroup root, boolean attachToRoot);
 *
 * 也就是说只要明白下面这个方法就行了
 * View view = LayoutInflater.from(context).inflate(int resource, ViewGroup root, boolean attachToRoot);
 *     resource - 需要加载的布局文件的资源 id
 *     root - 父容器（指定父容器后，才能计算出动态加载的布局文件的根布局中的 width, height 之类的属性）
 *     attachToRoot - 是否将获取到的 view 添加到父容器中（如果指定的父容器是 null，则此值无论你传的是什么，都视为 false）
 *     返回值就是获取到的 view
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.webabcd.androiddemo.R;

public class InflateDemo1 extends AppCompatActivity {

    private LinearLayout _container;
    private Button _button1;
    private Button _button2;
    private Button _button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_inflatedemo1);

        _container = findViewById(R.id.root);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 动态加载指定的布局文件，并指定其父容器，并自动添加到此父容器中（下面 3 句代码是一样的）

                // View layoutView = View.inflate(ViewDemo4.this,  R.layout.view_view_layout_inflatedemo1, _root);
                // View layoutView = LayoutInflater.from(ViewDemo4.this).inflate(R.layout.view_view_layout_inflatedemo1, _root);
                View layoutView = LayoutInflater.from(InflateDemo1.this).inflate(R.layout.view_view_layout_inflatedemo1, _container, true);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 动态加载指定的布局文件，并指定其父容器，但是不自动添加到此父容器中
                View layoutView = LayoutInflater.from(InflateDemo1.this).inflate(R.layout.view_view_layout_inflatedemo1, _container, false);
                // 手动将获取到的 view 添加到指定的父容器中
                _container.addView(layoutView);
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 动态加载指定的布局文件，但是不指定其父容器
                View layoutView = LayoutInflater.from(InflateDemo1.this).inflate(R.layout.view_view_layout_inflatedemo1, null);
                // 手动将获取到的 view 添加到指定的父容器中
                // 请运行本例看实际效果，由于 inflate 的时候未指定父容器，所以加载的 view 的高度计算是有问题的
                _container.addView(layoutView);
            }
        });
    }
}