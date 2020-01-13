/**
 * 自定义控件，实现了简单的涂鸦功能
 */

package com.webabcd.androiddemo.input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchDemo5_CustomView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mX;
    private float mY;

    public TouchDemo5_CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制涂鸦
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = x;
                mY = y;
                // 指定 path 的起点
                mPath.moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                // 用二次贝塞尔曲线连接 path 的每一点
                mPath.quadTo(mX, mY, x, y);
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        // 调用 draw()
        invalidate();

        return true;
    }

    // 清除涂鸦
    public void clear() {
        mPath.reset();

        // 调用 draw()
        invalidate();
    }
}