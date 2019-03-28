/**
 * RelativeLayout - 相对布局控件
 */

package com.webabcd.androiddemo.view.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class RelativeLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_relativelayoutdemo1);

        // 演示如何在 java 中控制 RelativeLayout 布局，仅代码演示，没有对应的显示效果
        sample();
    }

    private void sample() {
        RelativeLayout relativeLayout = new RelativeLayout(this);

        TextView textView = new TextView(this);
        // 第 1 个参数对应 xml 中的 layout_width（像素值）
        // 第 2 个参数对应 xml 中的 layout_height（像素值）
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 对应 xml 中的 layout_...
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.textView1); // layout_above
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.textView1); // layout_centerHorizontal
        textView.setLayoutParams(layoutParams);

        relativeLayout.addView(textView);
    }
}
