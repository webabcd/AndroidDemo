/**
 * Gesture 手势检测基础
 *
 * GestureDetector - 手势检测
 *     GestureDetector.OnGestureListener - 手势监听
 *         onDown(), onShowPress(), onSingleTapUp(), onScroll(), onLongPress(), onFling() - 详见本例代码中的说明
 *
 *
 * MotionEvent 的 getX() - 触摸点相对于监听控件自身的 x 位置
 * MotionEvent 的 getY() -  触摸点相对于监听控件自身的 y 位置
 * MotionEvent 的 getRawX() - 触摸点相对于整个屏幕的 x 位置
 * MotionEvent 的 getRawY() - 触摸点相对于整个屏幕的 y 位置
 *
 *
 * 注：手势检测的流程
 * 1、走到 onTouchEvent(MotionEvent event) 中
 * 2、通过 GestureDetector 将 MotionEvent 转给自定义的 GestureDetector.OnGestureListener 处理
 */

package com.webabcd.androiddemo.input;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.webabcd.androiddemo.R;

public class GestureDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "GestureDemo1";

    // 手势检测
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_gesturedemo1);

        // 通过指定自定义的 GestureDetector.OnGestureListener 对象来实例化 GestureDetector
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
    }

    // 处理 activity 的触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 通过 GestureDetector 转发给自定义的 GestureDetector.OnGestureListener 处理
        return mGestureDetector.onTouchEvent(event);
    }

    // 自定义 GestureDetector.OnGestureListener
    private class MyGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(LOG_TAG, "onDown 手指按下");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(LOG_TAG, "onShowPress 手指按下了一段时间，但还没有到长按时间");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // 如果走到了 onLongPress(), onScroll(), onFling() 则不会走到这里
            Log.d(LOG_TAG, "onSingleTapUp 手指轻点后离开");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            /**
             * MotionEvent e1 - 按下点的 MotionEvent
             * MotionEvent e2 - 当前点的 MotionEvent
             * float distanceX - 自上次滚动以来的 X 方向的距离
             * float distanceY - 自上次滚动以来的 Y 方向的距离
             */
            Log.d(LOG_TAG, String.format("onScroll 手指移动, distanceX:%f, distanceY:%f", distanceX, distanceY));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(LOG_TAG, "onLongPress 手指长按");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            /**
             * MotionEvent e1 - 按下点的 MotionEvent
             * MotionEvent e2 - 当前点的 MotionEvent
             * float velocityX - 此手势 X 方向的速度（单位：像素/秒）
             * float velocityY - 此手势 Y 方向的速度（单位：像素/秒）
             */
            Log.d(LOG_TAG, String.format("onFling 手指快速移动并松开, velocityX:%f, velocityY:%f", velocityX, velocityY));
            return false;
        }
    }
}