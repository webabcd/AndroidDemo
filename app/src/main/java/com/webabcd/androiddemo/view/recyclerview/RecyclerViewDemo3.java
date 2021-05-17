/**
 * 演示 RecyclerView 的下拉刷新，结合 SwipeRefreshLayout 控件实现
 *
 *
 * 引入 SwipeRefreshLayout 控件的话，需要在 app 的 build.gradle 中配置好 implementation "androidx.swiperefreshlayout:swiperefreshlayout:x.x.x"
 */

package com.webabcd.androiddemo.view.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;

import com.webabcd.androiddemo.R;

public class RecyclerViewDemo3 extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private SwipeRefreshLayout _swipeRefreshLayout1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recyclerview_recyclerviewdemo3);

        _recyclerView = findViewById(R.id.recyclerView1);
        _swipeRefreshLayout1 = findViewById(R.id.swipeRefreshLayout1);

        sample();
    }

    private void sample() {

        _recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewDemo3.this));
        _recyclerView.setAdapter(new MyRecyclerViewAdapter(MyData.generateDataList()));

        // 下拉刷新提示动画的前景色
        _swipeRefreshLayout1.setColorSchemeResources(R.color.white);
        // 下拉刷新提示动画的背景色
        _swipeRefreshLayout1.setProgressBackgroundColorSchemeResource(R.color.orange);
        // 监听下拉刷新事件
        _swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏下拉刷新提示动画
                        _swipeRefreshLayout1.setRefreshing(false);

                        // 更新 RecyclerView 的数据
                        ((MyRecyclerViewAdapter)_recyclerView.getAdapter()).updateData(MyData.generateDataList());
                        _recyclerView.getAdapter().notifyDataSetChanged();
                    }
                },2000);
            }
        });
    }
}