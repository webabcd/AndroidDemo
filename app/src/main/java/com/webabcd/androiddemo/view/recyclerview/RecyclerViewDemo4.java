/**
 * 演示 RecyclerView 的上拉加载更多数据
 *
 * adapter 参见 RecyclerViewDemo4Adapter.java
 * scroll 监听器参见  RecyclerViewDemo4OnScrollListener.java
 */

package com.webabcd.androiddemo.view.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.webabcd.androiddemo.R;

import java.util.List;

public class RecyclerViewDemo4 extends AppCompatActivity {

    private RecyclerView _recyclerView;

    // 数据分页的页索引
    private int _pageIndex = 0;
    // 数据分页的页大小
    private int _pageSize = 20;
    // 是否正在加载数据
    private boolean _isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recyclerview_recyclerviewdemo4);

        _recyclerView = findViewById(R.id.recyclerView1);

        sample();
    }

    private void sample() {

        _recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewDemo4.this));
        // 初始数据
        _recyclerView.setAdapter(new RecyclerViewDemo4Adapter(MyData.generateDataList(_pageIndex, _pageSize)));

        _recyclerView.addOnScrollListener(new RecyclerViewDemo4OnScrollListener() {
            @Override
            public void onBottom() {
                if (!_isLoading) {
                    // 加载更多数据
                    _pageIndex++;
                    _isLoading = true;
                    new MyTask().execute(_pageIndex, _pageSize);
                }
            }
        });
    }

    // 异步加载数据
    private class MyTask extends AsyncTask<Integer, Void, List<MyData>> {
        @Override
        protected List<MyData> doInBackground(Integer... params) {
            SystemClock.sleep(1000);

            int pageIndex = params[0];
            int pageSize = params[1];

            // 请求到更多数据
            return MyData.generateDataList(pageIndex * pageSize, pageSize);
        }

        @Override
        protected void onPostExecute(List<MyData> result) {
            RecyclerViewDemo4Adapter adapter = (RecyclerViewDemo4Adapter)_recyclerView.getAdapter();
            if (_pageIndex > 3) {
                // 没有更多数据了
                adapter.setHasMoreItems(false);
            } else {
                // 追加数据
                adapter.appendData(result);
            }

            adapter.notifyDataSetChanged();
            _isLoading = false;
        }
    }
}