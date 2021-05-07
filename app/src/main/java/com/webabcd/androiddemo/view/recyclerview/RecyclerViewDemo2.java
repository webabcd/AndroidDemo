/**
 * RecyclerView 分隔线
 *
 * 关于本例中使用的自定义的垂直线性布局的分隔线请参见
 */

package com.webabcd.androiddemo.view.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class RecyclerViewDemo2 extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private Button _button1;
    private Button _button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recyclerview_recyclerviewdemo2);

        _recyclerView = findViewById(R.id.recyclerView1);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        _recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewDemo2.this));
        _recyclerView.setAdapter(new MyRecyclerViewAdapter(MyData.getDataList()));

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (_recyclerView.getItemDecorationCount() > 0) {
                    _recyclerView.removeItemDecorationAt(0);
                }
                // 指定自定义分隔线（指定高度和颜色）
                _recyclerView.addItemDecoration(new MyVerticalLinearLayoutManagerDivider(20, getResources().getColor(R.color.orange)));
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (_recyclerView.getItemDecorationCount() > 0) {
                    _recyclerView.removeItemDecorationAt(0);
                }
                // 指定自定义分隔线（指定高度和 Drawable 对象）
                _recyclerView.addItemDecoration(new MyVerticalLinearLayoutManagerDivider(20, getResources().getDrawable(R.drawable.shape_recyclerview_divider)));
            }
        });
    }
}