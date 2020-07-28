/**
 * Touch 基础
 *
 * setOnClickListener() - 点击事件
 * setOnLongClickListener() - 长按事件
 * setOnTouchListener() - 触摸事件
 *     通过 MotionEvent 的 getAction() 获取当前的触摸类别
 *         MotionEvent.ACTION_DOWN - 触摸按下
 *         MotionEvent.ACTION_MOVE - 触摸移动
 *         MotionEvent.ACTION_UP - 触摸抬起
 *
 *
 * 本例演示
 * 1、如何通过 setOnClickListener() 监听点击事件
 * 2、如何通过 setOnLongClickListener() 监听长按事件
 * 3、如何通过 setOnTouchListener() 监听触摸事件，并判断“单击/双击/长按”事件，以及如何判断“触摸按下/触摸移动/触摸抬起”
 *
 *
 * 注：
 * 1、setOnTouchListener() 要先于 setOnClickListener()，如果在 setOnTouchListener() 中返回 true 则代表事件不冒泡，则不会触发 setOnClickListener()
 * 2、如果在 setOnTouchListener() 的 MotionEvent.ACTION_DOWN 中返回 false，则之后不会收到 MotionEvent.ACTION_MOVE 和 MotionEvent.ACTION_UP
 */

package com.webabcd.androiddemo.input;

import android.annotation.SuppressLint;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class TouchDemo1 extends AppCompatActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo1);

        mImageView1 = findViewById(R.id.imageView1);
        mImageView2 = findViewById(R.id.imageView2);

        sample1();
        sample2();
    }

    private void sample1() {
        // 单击事件
        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "setOnClickListener", Toast.LENGTH_SHORT).show();
            }
        });

        // 长按事件
        mImageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "setOnLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }



    private int mClickCount; // 点击次数
    private int mDownX; // 按下时的 x 位置
    private int mDownY; // 按下时的 y 位置
    private int mMoveX; // 移动时的 x 位置
    private int mMoveY; // 移动时的 y 位置
    private int mUpX; // 抬起时的 x 位置
    private int mUpY; // 抬起时的 y 位置
    private long mLastDownTime; // 最近按下时的时间
    private long mLastUpTime; // 最近抬起时的时间
    private long mFirstClickTime; // 第 1 次点击时的时间
    private long mSecondClickTime; // 第 2 次点击时的时间
    private int MAX_TIME = 250;// 判断“单击/双击/长按”的时间
    private int MAX_MOVE = 10;// 触摸点的最大变化值，超过此值则认为是移动（即不会判断为“双击/长按”）
    private Handler mHandler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    private void sample2() {
        mImageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastDownTime = System.currentTimeMillis();
                        mDownX = (int) event.getX();
                        mDownY = (int) event.getY();
                        mClickCount++;
                        break;
                    case MotionEvent.ACTION_MOVE: {
                        mMoveX = (int) event.getX();
                        mMoveY = (int) event.getY();
                        int offsetX = Math.abs(mMoveX - mDownX);
                        int offsetY = Math.abs(mMoveY - mDownY);

                        // 触摸点变化较大，不会判断为双击
                        if (offsetX > MAX_MOVE && offsetY > MAX_MOVE) {
                            mClickCount = 0;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mLastUpTime = System.currentTimeMillis();
                        mUpX = (int) event.getX();
                        mUpY = (int) event.getY();
                        int offsetX = Math.abs(mUpX - mDownX);
                        int offsetY = Math.abs(mUpY - mDownY);

                        // 触摸点变化较大，不会判断为双击或长按
                        if (offsetX > MAX_MOVE && offsetY > MAX_MOVE) {
                            mClickCount = 0;
                        }
                        else {
                            // 判断为长按
                            if ((mLastUpTime - mLastDownTime) > MAX_TIME) {
                                Toast.makeText(getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
                                mClickCount = 0;
                            }
                        }

                        if (mClickCount == 1) {
                            mFirstClickTime = System.currentTimeMillis();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 判断为单击
                                    if (mClickCount == 1) {
                                        Toast.makeText(getApplicationContext(), "单击", Toast.LENGTH_SHORT).show();
                                    }
                                    mClickCount = 0;
                                }
                            }, MAX_TIME);
                        }
                        else if (mClickCount == 2) {
                            mSecondClickTime = System.currentTimeMillis();
                            // 判断为双击
                            if (mSecondClickTime - mFirstClickTime < MAX_TIME) {
                                Toast.makeText(getApplicationContext(), "双击", Toast.LENGTH_SHORT).show();
                            }
                            mClickCount = 0;
                        }
                        break;
                    }
                }

                // 返回 true（事件不冒泡，不可能触发 setOnClickListener() 事件或 setOnLongClickListener() 事件）
                return true;

                // 返回 false（事件会冒泡，有可能触发 setOnClickListener() 事件或 setOnLongClickListener() 事件）
                // 如果在 MotionEvent.ACTION_DOWN 中返回 false，则之后不会收到 MotionEvent.ACTION_MOVE 和 MotionEvent.ACTION_UP
                // return false;
            }
        });
    }
}
