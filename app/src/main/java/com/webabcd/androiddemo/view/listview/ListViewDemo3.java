/**
 * ListView 通过自定义 BaseAdapter 显示数据（同时演示如何通过 convertView 复用的方式提高效率，以及 getView() 的调用时机）
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo3 extends AppCompatActivity {

    private final String LOG_TAG = "ListViewDemo4";

    private ListView _listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo3);

        _listView1 = (ListView) findViewById(R.id.listView1);

        sample();
    }

    private void sample() {
        // 构造数据
        List<MyData> myDataList = new ArrayList<MyData>();
        for (int i = 0; i < 100; i++) {
            myDataList.add(new MyData(R.drawable.img_sample_son, "name " + i, "comment " + i));
        }

        // 实例化自定义的 BaseAdapter
        MyAdapter myAdapter = new MyAdapter(myDataList, this);
        _listView1.setAdapter(myAdapter);
    }

    // 自定义实体类
    class MyData {
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

        /*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo3, parent,false);

            ImageView imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            TextView txtComment = (TextView) view.findViewById(R.id.txtComment);

            imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
            txtName.setText(_myDataList.get(position).getName());
            txtComment.setText(_myDataList.get(position).getComment());

            return view;
        }
        */

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 数据量不大的话就可以像上面注销的代码那样写，数量大的话则可以像下面这样写
        // 以下演示通过 convertView 复用的方式提高效率
        //
        // 关于每次绘制 item 都会调用 getView()，说明如下：
        // 1、最开始绘制 ListView 时，每个可见的 item 都会通过调用 getView() 来获得
        // 2、滚动 ListView 时，之前不可见滚动后可见的 item 都会通过调用 getView() 来获得
        // 3、滚动 ListView 时，之前可见又变为不可见又再次变为可见的 item 都会通过调用 getView() 来获得
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 多多上下滚动 ListView 来了解一下调用 getView() 的时机
            Log.d(LOG_TAG, String.format("getView: %d", position));

            ViewHolder holder = null;
            // 开始构造可见区域的 item 时，这个 convertView 都是 null
            // 如果 item 从可见区域移动到不可见区域了，那么系统会把这个 item 的 view 保存起来
            // 然后有的 item 会从不可见区域移动到可见区域，此时 convertView 就不是 null 了，它会是某一个之前被移出可视区域的 item 的 view
            // 然后你就可以利用这个 convertView 了，通过 ViewHolder 的方式让其保存相关数据
            // 这样就可以不用频繁的 inflate() 了，也不用频繁的 findViewById() 了，从而提高了效率
            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo3, parent, false);

                holder = new ViewHolder();
                holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                convertView.setTag(holder); // 将 holder 保存到 convertView 中
            } else {
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


