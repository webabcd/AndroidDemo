/**
 * ListView 通过自定义 BaseAdapter 显示数据
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
        myDataList.add(new MyData(R.drawable.img_sample_son, "中国", "我是中国"));
        myDataList.add(new MyData(R.drawable.img_sample_son, "美国", "我是美国"));
        myDataList.add(new MyData(R.drawable.img_sample_son, "日本", "我是日本"));

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
            convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo3,parent,false);

            ImageView imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
            TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
            TextView txtComment = (TextView) convertView.findViewById(R.id.txtComment);

            imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
            txtName.setText(_myDataList.get(position).getName());
            txtComment.setText(_myDataList.get(position).getComment());

            return convertView;
        }
        */

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 数据量不大的话就可以像上面那样写，数量大的话则可以像下面这样写
        // 下面这个写法是优化写法，其优化了如下两点
        // 1、只 inflate() 一次 xml
        // 2、不再频繁地调用 findViewById()
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                // 只 inflate() 一次 xml
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo3, parent, false);

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


