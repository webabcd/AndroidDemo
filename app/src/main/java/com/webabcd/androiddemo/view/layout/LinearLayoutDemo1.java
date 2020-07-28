/**
 * LinearLayout - 线性布局控件
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class LinearLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_linearlayoutdemo1);

        // 演示如何在 java 中控制 LinearLayout 布局，仅代码演示，没有对应的显示效果
        sample();
    }

    private void sample() {
        LinearLayout linearLayout = new LinearLayout(this);
        // 对应 xml 中的 orientation
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 对应 xml 中的 divider
        linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.img_sample_son));
        // 对应 xml 中的 showDividers
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        int dpValue = 10;
        float scale = this.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * scale + 0.5f);
        // 对应 xml 中的 dividerPadding
        linearLayout.setDividerPadding(pxValue);

        TextView textView = new TextView(this);
        // 第 1 个参数对应 xml 中的 layout_width（像素值）
        // 第 2 个参数对应 xml 中的 layout_height（像素值）
        // 第 3 个参数对应 xml 中的 layout_weight
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f);
        textView.setLayoutParams(layoutParams);

        linearLayout.addView(textView);
    }
}
