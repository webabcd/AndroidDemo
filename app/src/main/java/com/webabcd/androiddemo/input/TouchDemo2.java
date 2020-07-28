/**
 * Touch 基础
 *
 * setOnTouchListener() - 触摸事件
 *     MotionEvent 的 getX() - 触摸点相对于监听控件自身的 x 位置
 *     MotionEvent 的 getY() -  触摸点相对于监听控件自身的 y 位置
 *     MotionEvent 的 getRawX() - 触摸点相对于整个屏幕的 x 位置
 *     MotionEvent 的 getRawY() - 触摸点相对于整个屏幕的 y 位置
 *     返回 true 代表事件不冒泡
 *     返回 false 代表事件冒泡（如果在 MotionEvent.ACTION_DOWN 中返回 false，则之后不会收到 MotionEvent.ACTION_MOVE 和 MotionEvent.ACTION_UP）
 *
 *
 * 本例演示
 * 1、如何在 setOnTouchListener() 中获取触摸点的位置信息
 * 2、事件冒泡
 */


package com.webabcd.androiddemo.input;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TouchDemo2 extends AppCompatActivity {

    private FrameLayout mFrameLayout1;
    private ImageView mImageView1;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo2);

        mFrameLayout1 = findViewById(R.id.frameLayout1);
        mImageView1 = findViewById(R.id.imageView1);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void sample() {
        mFrameLayout1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTextView1.append(String.format("mFrameLayout1 setOnClickListener, x:%f, y:%f, rawX:%f, rawY:%f\n", event.getX(), event.getY(), event.getRawX(), event.getRawY()));

                return false;
            }
        });

        mImageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTextView1.append(String.format("mImageView1 setOnClickListener, x:%f, y:%f, rawX:%f, rawY:%f\n", event.getX(), event.getY(), event.getRawX(), event.getRawY()));

                return false;
            }
        });
    }
}
