/**
 * 一个自定义的 view
 * @Override boolean onTouchEvent() - 在控件内部监听触摸事件
 *
 *
 * 本例绘制了一个蓝色圆圈，其会跟随当前触摸点的位置（演示了如何在自定义控件内部监听 touch 事件）
 */

package com.webabcd.androiddemo.input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchDemo4_CustomView extends View {

    // 圆圈的位置
    public float X = 50;
    public float Y = 50;

    private Paint mPaint = new Paint();

    public TouchDemo4_CustomView(Context context, AttributeSet set)
    {
        super(context,set);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制一个蓝色圆圈
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(X,Y,30, mPaint);
    }

    // 在控件内部处理触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.X = event.getX();
        this.Y = event.getY();

        // 通知组件重新绘制
        this.invalidate();

        return true;
    }
}