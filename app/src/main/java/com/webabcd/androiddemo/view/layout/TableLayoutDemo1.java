/**
 * TableLayout - 表格布局控件
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TableLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_tablelayoutdemo1);

        // 演示如何在 java 中控制 TableLayout 布局，仅代码演示，没有对应的显示效果
        sample();
    }

    private void sample() {
        TableLayout tableLayout = new TableLayout(this);
        // 对应 xml 中的 stretchColumns
        tableLayout.setColumnStretchable(0, true); // tableLayout.setStretchAllColumns(true);
        // 对应 xml 中的 shrinkColumns
        tableLayout.setColumnShrinkable(0, true); // tableLayout.setShrinkAllColumns(true);
        // 对应 xml 中的 collapseColumns
        tableLayout.setColumnCollapsed(0, true);

        TextView textView = new TextView(this);
        // 第 1 个参数对应 xml 中的 layout_width（像素值）
        // 第 2 个参数对应 xml 中的 layout_height（像素值）
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 对应 xml 中的 layout_span
        layoutParams.span = 1;
        // 对应 xml 中的 layout_column
        layoutParams.column = 1;
        textView.setLayoutParams(layoutParams);

        tableLayout.addView(textView);
    }
}
