/**
 * GridLayout - 网格布局控件
 */

package com.webabcd.androiddemo.view.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class GridLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_gridlayoutdemo1);

        // 演示如何在 java 中控制 GridLayout 布局，仅代码演示，没有对应的显示效果
        sample();
    }

    private void sample() {
        GridLayout gridLayout = new GridLayout(this);
        // 对应 xml 中的 orientation
        gridLayout.setOrientation(GridLayout.VERTICAL);
        // 对应 xml 中的 columnCount
        gridLayout.setColumnCount(10);
        // 对应 xml 中的 rowCount
        gridLayout.setRowCount(10);

        TextView textView = new TextView(this);
        // 第 1 个参数对应 xml 中的 layout_row
        // 第 2 个参数对应 xml 中的 layout_rowSpan
        // 第 3 个参数对应 xml 中的 layout_gravity
        // 第 4 个参数对应 xml 中的 layout_rowWeight
        GridLayout.Spec rowSpec = GridLayout.spec(0, 2, GridLayout.FILL,1.0f);
        // 第 1 个参数对应 xml 中的 layout_column
        // 第 2 个参数对应 xml 中的 layout_columnSpan
        // 第 3 个参数对应 xml 中的 layout_gravity
        // 第 4 个参数对应 xml 中的 layout_columnWeight
        GridLayout.Spec columnSpec = GridLayout.spec(0, 2, GridLayout.FILL, GridLayout.UNDEFINED);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        textView.setLayoutParams(layoutParams);

        gridLayout.addView(textView);
    }
}
