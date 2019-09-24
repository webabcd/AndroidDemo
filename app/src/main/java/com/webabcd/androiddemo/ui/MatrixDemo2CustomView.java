/**
 * 本例演示如何重写 TextView 控件的 onDraw() 方法，以使其支持 Matrix 变换
 *
 *
 * 注意：
 * 1、因为每一次进 onDraw() 都是在上一次 canvas 的基础上 draw 的，所以如果对 canvas 做了 matrix 变换的话，那么下一次再 draw 的话就是针对上一次 matrix 变换后的 canvas 做 draw 的
 *    如果希望的是每次 draw 都是在初始 canvas 上进行的话，则需要对 canvas 进行 save()/restore() 操作
 * 2、对于类似 LinearLayout 之类的控件来说，其本身是不会调用 onDraw() 的，因为绘制部分都是通过它的子控件来完成的
 *    如果需要调用 onDraw() 做一些额外绘制的话，则需要在构造函数中调用 setWillNotDraw(false)，但是是无法在 LinearLayout 的 onDraw() 中对整个 LinearLayout 做 matrix 变换
 * 3、对于类似 LinearLayout 之类的控件来说，有一些类似 setRotation(), setScaleX(), setTranslationX() 之类的方法可以调用
 * 4、另外也可以考虑用 Animation 的方式实现类似的变换
 */

package com.webabcd.androiddemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

// 自定义 TextView 控件
public class MatrixDemo2CustomView extends android.support.v7.widget.AppCompatTextView {

    public MatrixDemo2CustomView(Context context) {
        super(context);
    }

    public MatrixDemo2CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixDemo2CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 保存 canvas 的状态，之后可以通过 restore() 恢复
        canvas.save();

        // 对 canvas 做 matrix 变换
        Matrix matrix = new Matrix();
        matrix.setTranslate(10f, 10f);
        matrix.postSkew(0.1f, 0.3f);
        canvas.setMatrix(matrix);

        // 绘制 matrix 变换后的 canvas
        super.onDraw(canvas);

        // 恢复 canvas 的调用 save() 之前的状态
        canvas.restore();
    }
}
