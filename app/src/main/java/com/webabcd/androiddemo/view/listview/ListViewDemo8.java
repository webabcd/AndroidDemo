/**
 * ListView 滚动到底部加载更多数据
 *
 * 自定义的用于支持滚动到底部加载更多数据的 ListView 的代码参见 ListViewDemo8LoadMoreListView.java
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo8 extends AppCompatActivity {

    private ListViewDemo8LoadMoreListView _listView1;

    // 数据源
    private List<MyData> _myDataList = new ArrayList<MyData>();
    // BaseAdapter
    private MyAdapter _myAdapter;
    // 数据分页的页索引
    private int _pageIndex = 0;
    // 数据分页的页大小
    private int _pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo8);

        _listView1 = (ListViewDemo8LoadMoreListView) findViewById(R.id.listView1);

        sample();
    }

    private void sample() {
        // 异步加载数据
        new MyTask().execute(_pageIndex, _pageSize);
        _myAdapter = new MyAdapter(_myDataList, this);
        _listView1.setAdapter(_myAdapter);
        // 告知 ListView 有更多数据可供加载
        _listView1.setHasMoreItems(true);

        // 处理 ListView 的需要加载更多数据的事件
        _listView1.setOnLoadMoreListener(new ListViewDemo8LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                _pageIndex++;
                new MyTask().execute(_pageIndex, _pageSize);
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

            List<MyData> result = new ArrayList<MyData>();
            for (int i = pageIndex * pageSize; i < pageIndex * pageSize + pageSize; i++) {
                result.add(new MyData(R.drawable.img_sample_son, "name " + i, "comment " + i));
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<MyData> result) {
            if (_pageIndex > 3) {
                // 告知 ListView 没有更多数据可供加载了
                _listView1.setHasMoreItems(false);
            }

            // 添加数据
            _myDataList.addAll(result);
            // 刷新 ListView
            _myAdapter.notifyDataSetChanged();
            // 告知 ListView 当前数据加载已经完成
            _listView1.loadComplete();
        }
    }

    // 自定义实体类
    private static class MyData {
        private int _logoId;
        private String _name;
        private String _comment;

        public MyData() {
        }

        public MyData(int logoId, String name, String comment) {
            this._logoId = logoId;
            this._name = name;
            this._comment = comment;
        }

        public int getLogoId() {
            return _logoId;
        }

        public String getName() {
            return _name;
        }

        public String getComment() {
            return _comment;
        }

        public void setLogoId(int logoId) {
            this._logoId = logoId;
        }

        public void setName(String name) {
            this._name = name;
        }

        public void setComment(String comment) {
            this._comment = comment;
        }
    }

    // 自定义 BaseAdapter
    class MyAdapter extends BaseAdapter {

        private List<MyData> _myDataList;
        private Context _context;

        public MyAdapter(List<MyData> myDataList, Context context) {
            this._myDataList = myDataList;
            this._context = context;
        }

        // 需要呈现的 item 的总数
        @Override
        public int getCount() {
            return _myDataList.size();
        }

        // 返回指定索引位置的 item 的对象
        @Override
        public Object getItem(int position) {
            return _myDataList.get(position);
        }

        // 返回指定索引位置的 item 的 id
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 每次绘制 item 都会调用 getView()
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                // 只 inflate() 一次 xml
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo8, parent, false);

                holder = new ViewHolder();
                holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                convertView.setTag(holder); // 将 holder 保存到 convertView 中
            } else {
                // 不再频繁地调用 findViewById()
                holder = (ViewHolder) convertView.getTag();
            }

            holder.imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
            holder.txtName.setText(_myDataList.get(position).getName());
            holder.txtComment.setText(_myDataList.get(position).getComment());

            return convertView;
        }

        class ViewHolder {
            ImageView imgLogo;
            TextView txtName;
            TextView txtComment;
        }
    }
}
