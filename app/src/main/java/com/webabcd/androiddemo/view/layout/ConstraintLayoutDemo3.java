/**
 * ConstraintLayout - 约束布局控件
 */

package com.webabcd.androiddemo.view.layout;

import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Placeholder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ConstraintLayoutDemo3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_constraintlayoutdemo3);

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
        Barrier barrier = new Barrier(this);
        int barrierId = View.generateViewId();
        barrier.setId(barrierId);
        Group group = new Group(this);
        Placeholder placeholder = new Placeholder(this);
        constraintLayout.addView(textView1);
        constraintLayout.addView(textView2);
        constraintLayout.addView(barrier);
        constraintLayout.addView(group);
        constraintLayout.addView(placeholder);

        ConstraintSet constraintSet = new ConstraintSet();

        // 对应 xml 中的 Barrier
        // 第 2 个参数对应 xml 中的 barrierDirection
        // 第 3...n 个参数对应 xml 中的 constraint_referenced_ids
        constraintSet.createBarrier(barrierId, Barrier.LEFT, textView1Id, textView2Id);

        // 对应 xml 中的 Group
        // 参数对应 xml 中的 constraint_referenced_ids
        int[] referencedIds = {textView1Id, textView2Id};
        group.setReferencedIds(referencedIds);

        // 对应 xml 中的 Placeholder
        // 参数对应 xml 中的 content
        placeholder.setContentId(textView1Id);

        constraintSet.applyTo(constraintLayout);
    }
}
