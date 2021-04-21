package com.webabcd.androiddemo.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyLoading1 extends View {

    // 用于绘制总进度的图形
    private Paint mPaintTotal;
    // 用于绘制当前进度的图形
    private Paint mPaintCurrent;
    // 用于绘制当前进度的文本
    private Paint mPaintText;
    // 用于承载圆弧（当前进度的图形）的矩形
    private RectF mRect;

    // 画笔宽度
    private float mStrokeWidth = 50;
    // 文字大小
    private float mTextSize = 80;
    // 圆形 loading 的半径
    private float mRadius = 200;

    // 进度的最大值
    private int mProgressMax = 100;
    // 当前进度值
    private int mProgressCurrent = 0;

    // 测量后的宽
    private int mWidth;
    // 测量后的高
    private int mHeight;

    public MyLoading1(Context context) {
        super(context);
        init();
    }

    public MyLoading1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLoading1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 初始化各个画笔对象
    private void init() {
        mPaintTotal = new Paint();
        mPaintTotal.setColor(Color.RED);
        mPaintTotal.setAntiAlias(true); // 抗锯齿
        mPaintTotal.setStyle(Paint.Style.STROKE);
        mPaintTotal.setStrokeWidth(mStrokeWidth);

        mPaintCurrent = new Paint();
        mPaintCurrent.setColor(Color.GREEN);
        mPaintCurrent.setAntiAlias(true); // 抗锯齿
        mPaintCurrent.setStyle(Paint.Style.STROKE);
        mPaintCurrent.setStrokeWidth(mStrokeWidth);

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLUE);
        mPaintText.setAntiAlias(true); // 抗锯齿
        mPaintText.setTextSize(mTextSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量尺寸
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        // 计算当前进度的角度值
        float angle = 1.0f * mProgressCurrent / mProgressMax * 360;

        // 初始化用于承载圆弧（当前进度的图形）的矩形
        initArcRect();

        // 绘制总进度的图形
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintTotal);
        // 绘制当前进度的图形
        canvas.drawArc(mRect, -90, angle, false, mPaintCurrent);
        // 绘制当前进度的文本
        canvas.drawText(mProgressCurrent + "%", mWidth / 2 + mStrokeWidth / 2, mHeight / 2 + mStrokeWidth / 2, mPaintText);
    }

    // 设置当前进度值
    public void setProgress(int progress) {
        mProgressCurrent = progress;

        // 重新绘制
        invalidate();
    }

    private int getRealSize(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            // 自己计算
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }

        return result;
    }

    // 初始化用于承载圆弧（当前进度的图形）的矩形
    private void initArcRect() {
        if (mRect == null) {
            mRect = new RectF();
            int viewSize = (int) (mRadius * 2);
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;
            mRect.set(left, top, right, bottom);
        }
    }
}