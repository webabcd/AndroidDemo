/**
 * ListView 的多布局（不同的 item 使用不同的项模板）
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

public class ListViewDemo7 extends AppCompatActivity {

    // 第 1 种布局类别：左对齐
    private static final int TYPE_LEFT = 0;
    // 第 2 种布局类别：右对齐
    private static final int TYPE_RIGHT = 1;

    private ListView _listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo7);

        _listView1 = (ListView) findViewById(R.id.listView1);

        sample();
    }

    private void sample() {
        // 构造数据
        List<MyData> myDataList = new ArrayList<MyData>();
        for (int i = 0; i < 100; i++) {
            myDataList.add(new MyData(R.drawable.img_sample_son, "name " + i, "comment " + i));
        }
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

        // 返回指定索引位置的 item 的布局类别
        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return TYPE_LEFT;
            } else {
                return TYPE_RIGHT;
            }
        }

        // 布局类别的数量
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 每次绘制 item 都会调用 getView()
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            ViewHolder1 holder1 = null;
            ViewHolder2 holder2 = null;
            if (convertView == null) {
                if (type == TYPE_LEFT) { // 使用 TYPE_LEFT 布局类别的项模板
                    convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo7_1, parent, false);
                    holder1 = new ViewHolder1();
                    holder1.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                    holder1.txtName = (TextView) convertView.findViewById(R.id.txtName);
                    holder1.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                    convertView.setTag(holder1);
                } else if (type == TYPE_RIGHT) { // 使用 TYPE_RIGHT 布局类别的项模板
                    convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo7_2, parent, false);
                    holder2 = new ViewHolder2();
                    holder2.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                    holder2.txtName = (TextView) convertView.findViewById(R.id.txtName);
                    holder2.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                    convertView.setTag(holder2);
                }
            } else {
                if (type == TYPE_LEFT) {
                    holder1 = (ViewHolder1) convertView.getTag();
                } else if (type == TYPE_RIGHT) {
                    holder2 = (ViewHolder2) convertView.getTag();
                }
            }

            if (type == TYPE_LEFT) {
                holder1.imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
                holder1.txtName.setText(_myDataList.get(position).getName());
                holder1.txtComment.setText(_myDataList.get(position).getComment());
            } else if (type == TYPE_RIGHT) {
                holder2.imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
                holder2.txtName.setText(_myDataList.get(position).getName());
                holder2.txtComment.setText(_myDataList.get(position).getComment());
            }

            return convertView;
        }

        class ViewHolder1 {
            ImageView imgLogo;
            TextView txtName;
            TextView txtComment;
        }

        class ViewHolder2 {
            ImageView imgLogo;
            TextView txtName;
            TextView txtComment;
        }
    }
}