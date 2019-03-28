/**
 * ConstraintLayout - 约束布局控件
 */

package com.webabcd.androiddemo.view.layout;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ConstraintLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_constraintlayoutdemo1);

        // 演示如何在 java 中控制 ConstraintLayout 布局，仅代码演示，没有对应的显示效果
        sample();
    }

    private void sample() {
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        TextView textView1 = new TextView(this);
        int textView1Id = View.generateViewId();
        textView1.setId(textView1Id);
        TextView textView2 = new TextView(this);
        int textView2Id = View.generateViewId();
        textView2.setId(textView2Id);
        constraintLayout.addView(textView1);
        constraintLayout.addView(textView2);

        ConstraintSet constraintSet = new ConstraintSet();
        // 前 4 个参数的意思：约束 textView2 的左侧，使其与位于 textView1 的右侧
        // 第 5 个参数的意思：约束方向上的 margin
        constraintSet.connect(textView2Id, ConstraintSet.LEFT, textView1Id, ConstraintSet.RIGHT, 0);
        // 对应 xml 中的 layout_goneMarginStart
        constraintSet.setGoneMargin(textView2Id, ConstraintSet.START, 1);
        // 对应 xml 中的 layout_constraintCircle, layout_constraintCircleAngle, layout_constraintCircleRadius
        // 约束 textView2 控件，使其位于相对于 textView1 控件的 120 角度和 150 距离（像素值）的位置
        constraintSet.constrainCircle(textView2Id, textView1Id, 120, 150);
        // 对应 xml 中的 layout_constraintDimensionRatio
        constraintSet.setDimensionRatio(textView1Id, "2:1");
        // 对应 xml 中的 layout_constraintHorizontal_bias
        constraintSet.setHorizontalBias(textView1Id, 0.5f);
        // 对应 xml 中的 layout_constraintVertical_bias
        constraintSet.setVerticalBias(textView1Id, 0.5f);
        // 约束 textView2 控件，使其相对于 textView1 控件水平居中
        constraintSet.centerHorizontally(textView2Id, textView1Id);
        // 约束 textView2 控件，使其相对于 textView1 控件垂直居中
        constraintSet.centerVertically(textView2Id, textView1Id);

        constraintSet.applyTo(constraintLayout);
    }
}
