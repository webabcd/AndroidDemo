/**
 * ConstraintLayout - 约束布局控件
 */

package com.webabcd.androiddemo.view.layout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ConstraintLayoutDemo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_constraintlayoutdemo2);

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
        // 实现一个横向链式约束（textView1, textView2 为一个横向链式约束）
        constraintSet.connect(textView1Id, ConstraintSet.RIGHT, textView2Id, ConstraintSet.LEFT, 0);
        constraintSet.connect(textView2Id, ConstraintSet.LEFT, textView1Id, ConstraintSet.RIGHT, 0);
        // 对应 xml 中的 layout_constraintHorizontal_chainStyle
        constraintSet.setHorizontalChainStyle(textView1Id, ConstraintSet.CHAIN_SPREAD);
        // 对应 xml 中的 layout_constraintHorizontal_weight
        constraintSet.setHorizontalWeight(textView1Id, 1.0f);
        // 对应 xml 中的 layout_constraintVertical_chainStyle
        constraintSet.setVerticalChainStyle(textView1Id, ConstraintSet.CHAIN_SPREAD);
        // 对应 xml 中的 layout_constraintVertical_weight
        constraintSet.setVerticalWeight(textView1Id, 1.0f);

        constraintSet.applyTo(constraintLayout);
    }
}
