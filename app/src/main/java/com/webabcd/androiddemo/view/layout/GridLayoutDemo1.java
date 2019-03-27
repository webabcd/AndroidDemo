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

        sample();
    }

    private void sample() {
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setOrientation(GridLayout.VERTICAL);
        gridLayout.setColumnCount(10);
        gridLayout.setRowCount(10);


        /*
        TextView textView = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(0, 2, GridLayout.UNDEFINED);
        GridLayout.Spec columnSpec = GridLayout.spec(i, 1, GridLayout.UNDEFINED);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);


        textView.setLayoutParams(layoutParams);
        */
    }
}
