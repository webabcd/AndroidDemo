/**
 * ListView 的 item 的点击事件和长按事件
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo4 extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private final String LOG_TAG = "ListViewDemo4";

    private ListView _listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo4);

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

        // item 的点击事件
        _listView1.setOnItemClickListener(this);
        // item 的长按事件
        _listView1.setOnItemLongClickListener(this);
    }

    // item 的点击事件的回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // parent 就是 ListView
        // view 就是被点击的 item
        // position 就是被点击的 item 的索引位置
        // id 就是被点击的 item 的 id（通过调用 BaseAdapter 的 getItemId(int position) 来获取此 id）

        // 调用 BaseAdapter 的 getItem(int position)
        // MyData myData = (MyData)parent.getAdapter().getItem(position);

        // 与上面的代码其实是一样的，会调用 BaseAdapter 的 getItem(int position)
        MyData myData = (MyData)parent.getItemAtPosition(position);

        Toast.makeText(this,String.format("click position:%d, id:%d, data:%s", position, id, myData.getName()),Toast.LENGTH_SHORT).show();
    }

    // item 的长按事件的回调
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MyData myData = (MyData)parent.getItemAtPosition(position);
        Toast.makeText(this,String.format("longclick position:%d, id:%d, data:%s", position, id, myData.getName()),Toast.LENGTH_SHORT).show();

        // 当同时监听了 item 的点击事件和长按事件时
        // 此处返回 true 则触发了长按事件后，不会再触发点击事件
        // 此处返回 false 则触发了长按事件后，会再触发点击事件
        boolean handled = true;
        return handled;
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
            return position * 10;
        }

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 每次绘制 item 都会调用 getView()，说明如下：
        // 1、最开始绘制 ListView 时，每个可见的 item 都会通过调用 getView() 来获得
        // 2、滚动 ListView 时，之前不可见滚动后可见的 item 都会通过调用 getView() 来获得
        // 3、滚动 ListView 时，之前可见又变为不可见又再次变为可见的 item 都会通过调用 getView() 来获得
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 多多上下滚动 ListView 来了解一下调用 getView() 的时机
            Log.d(LOG_TAG, String.format("getView: %d", position));

            ViewHolder holder = null;
            if (convertView == null) {
                // 只 inflate() 一次 xml
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo4, parent, false);

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


















































// 我们可以通过设置：android:scrollbars="none" 或者 setVerticalScrollBarEnabled(true); 解决这个问题！
            /*

如何修改数据新增数据，如何程序来滚动滚动条



https://blog.csdn.net/zh175578809/article/details/72797535

                    View headView = LayoutInflater.from(this).inflate(R.layout.item_view_listview_listviewdemo6_header, null, false);
        View footView = LayoutInflater.from(this).inflate(R.layout.item_view_listview_listviewdemo6_footer, null, false);
        _listView1.addHeaderView(headView);
        _listView1.addFooterView(footView);


            listview作为一个列表控件，他和普通的列表一样，可以自己设置表头与表尾： 以及分割线，可供我们设置的属性如下：

footerDividersEnabled：是否在footerView(表尾)前绘制一个分隔条,默认为true
headerDividersEnabled:是否在headerView(表头)前绘制一个分隔条,默认为true
divider:设置分隔条,可以用颜色分割,也可以用drawable资源分割
dividerHeight:设置分隔条的高度
翻遍了了API发现并没有可以直接设置ListView表头或者表尾的属性，只能在Java中写代码 进行设置了，可供我们调用的方法如下：

addHeaderView(View v)：添加headView(表头),括号中的参数是一个View对象
addFooterView(View v)：添加footerView(表尾)，括号中的参数是一个View对象
addHeaderView(headView, null, false)：和前面的区别：设置Header是否可以被选中
addFooterView(View,view,false)：同上
对了，使用这个addHeaderView方法必须放在listview.setAdapter前面，否则会报错。
             */