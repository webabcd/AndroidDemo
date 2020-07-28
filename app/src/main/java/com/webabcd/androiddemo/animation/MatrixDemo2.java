/**
 * Matrix 变换（通过自定义控件实现）
 *
 * ImageView 有设置其 Matrix 变换的方法（需要将其 scaleType 设置为 matrix），但是其他控件都没有，那么如何设置其他控件的 Matrix 变换呢
 * 本例演示如何重写 TextView 控件的 onDraw() 方法，以使其支持 Matrix 变换（详细代码参见：animation/MatrixDemo2CustomView.java）
 *
 * 注：对于非 ImageView 控件的 Matrix 变换来说，除了用本例的方法外，也可以用其他方法实现。比如调用控件的 setTranslationX(), setScaleX() 之类的方法，或者用 0 秒动画
 */

package com.webabcd.androiddemo.animation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class MatrixDemo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_matrixdemo2);
    }
}
