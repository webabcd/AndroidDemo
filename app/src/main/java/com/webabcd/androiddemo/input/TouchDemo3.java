/**
 * Touch 多点触摸
 *
 * setOnTouchListener() - 触摸事件
 *     通过 MotionEvent 的 event.getAction() & MotionEvent.ACTION_MASK 获取当前的触摸类别
 *         MotionEvent.ACTION_DOWN - 第一根手指触摸按下
 *         MotionEvent.ACTION_MOVE - 触摸移动
 *         MotionEvent.ACTION_UP - 最后一根手指触摸抬起
 *         MotionEvent.ACTION_POINTER_DOWN - 非第一根手指触摸按下（即第 2,3,4,...n 根手指触摸按下）
 *         MotionEvent.ACTION_POINTER_UP - 非最后一根手指触摸抬起（即第 n...,4,3,2 根手指触摸抬起）
 *
 *
 * 本例演示
 * 1、如何通过单点触摸拖拽组件
 * 2、如何通过两点触摸缩放组件
 */

package com.webabcd.androiddemo.input;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

public class TouchDemo3 extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageView1;

    // 需要移动到的位置或缩放到的位置的 matrix
    private Matrix mMatrix = new Matrix();
    // 触摸按下时的初始 matrix
    private Matrix mMatrixTemp = new Matrix();

    // 状态（用于标记当前操作是拖拽还是缩放）
    private int mMode = NONE;
    private static final int NONE = 0;
    private static final int DRAG = 1; // 拖拽
    private static final int ZOOM = 2; // 缩放

    // 第一根手指触摸按下时的位置
    private PointF startPoint = new PointF();

    // 第二根手指触摸按下时，其与第一根手指的中间点的位置
    private PointF middlePoint = new PointF();
    // 第二根手指触摸按下时，其与第一根手指的距离
    private float startDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_touchdemo3);

        mImageView1 = findViewById(R.id.imageView1);
        mImageView1.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 第一根手指按下时
            case MotionEvent.ACTION_DOWN:
                mMatrix.set(imageView.getImageMatrix());
                mMatrixTemp.set(mMatrix);
                startPoint.set(event.getX(), event.getY());
                mMode = DRAG;
                break;
            // 第二根手指按下时
            case MotionEvent.ACTION_POINTER_DOWN:
                startDistance = distance(event);
                middlePoint = middle(event);
                mMode = ZOOM;
                break;
            // 手指放开时
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mMode = NONE;
                break;
            // 手指移动时
            case MotionEvent.ACTION_MOVE:
                if (mMode == DRAG) {
                    // 单指移动（执行拖拽操作）
                    mMatrix.set(mMatrixTemp);
                    mMatrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                } else if (mMode == ZOOM) {
                    // 双指移动（执行缩放操作）
                    float currentDistance = distance(event);
                    float scale = currentDistance / startDistance;
                    mMatrix.set(mMatrixTemp);
                    mMatrix.postScale(scale, scale, middlePoint.x, middlePoint.y);
                }
                break;
        }

        // 指定 ImageView 的 matrix
        imageView.setImageMatrix(mMatrix);

        return true;
    }

    // 计算两个触摸点之间的距离
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 计算两个触摸点之间的中间点的位置
    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }
}
