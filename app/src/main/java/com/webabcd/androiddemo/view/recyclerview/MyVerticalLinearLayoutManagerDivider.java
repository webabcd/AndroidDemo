/**
 * 自定义 RecyclerView.ItemDecoration
 *
 * 本例实现了一个自定义的垂直线性布局的分隔线，代码请参见 MyVerticalLinearLayoutManagerDivider.java
 */

package com.webabcd.androiddemo.view.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyVerticalLinearLayoutManagerDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint = null;
    private Drawable mDrawable = null;
    private int mDividerHeight = 0;

    // 指定分隔线的高度和颜色
    public MyVerticalLinearLayoutManagerDivider(int dividerHeight, int color) {

        mDividerHeight = dividerHeight;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    // 指定分隔线的高度和 Drawable 对象
    public MyVerticalLinearLayoutManagerDivider(int dividerHeight, Drawable drawable) {

        mDividerHeight = dividerHeight;

        mDrawable = drawable;
    }

    // 用于绘制可视区中的分隔线
    // 比如在可视区中，由于你的拖动，分隔线上下移动了哪怕是 1 个像素都会调用此方法重新绘制
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 重新绘制可视区中的所有分隔线
        int childCount = parent.getChildCount(); // 这个返回的是可视区中的 item 的数量（而不是全部 item 的数量）
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            drawTop(c, child, parent);
        }
    }

    // 设置分隔线的高度
    // 比如当某个 item 即将从不可视区到可视区时，就会调用此方法来设置 2 个 item 之间的间隔大小
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,@NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemCount = state.getItemCount(); // 总 item 的数量
        int childCount = parent.getChildCount(); // 可视区 item 的数量
        RecyclerView.Adapter adapter = parent.getAdapter(); // 获取 RecyclerView 的 Adapter
        int itemPosition = parent.getChildAdapterPosition(view); // 获取即将出现在可视区中的 item 在全部 item 中的索引位置

        if (itemPosition == 0) { // 第 1 个 item 的上部的分隔线的高度为 0
            outRect.set(0, 0, 0, 0);
        } else { // 非第 1 个 item 的上部的分隔线的高度为指定的高度
            outRect.set(0, mDividerHeight, 0, 0);
        }
    }

    // 绘制 item 的上部的分隔线
    private void drawTop(Canvas c, View child, RecyclerView recyclerView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        int left = child.getLeft() - lp.leftMargin;
        int top = child.getTop() - lp.topMargin - mDividerHeight;
        int right = child.getRight() + lp.rightMargin;
        int bottom = child.getTop() - lp.topMargin;

        if (mPaint != null) { // 绘制指定颜色的分隔线
            c.drawRect(left, top, right, bottom, mPaint);
        } else if (mDrawable != null) { // 绘制指定 Drawable 对象的分隔线
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }
}
