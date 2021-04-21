/**
 * ListView 的表头，表尾，分隔线，滚动条的显示与隐藏，数据更新与 ListView 刷新，滚动到指定位置，监听 ListView 的滚动状态
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
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewDemo6 extends AppCompatActivity {

    private ListView _listView1;
    private Button _button1;
    private Button _button2;
    private TextView _textView1;
    private TextView _textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo6);

        _listView1 = (ListView) findViewById(R.id.listView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);

        sample();
    }

    private void sample() {
        // 构造数据
        final List<MyData> myDataList = new ArrayList<MyData>();
        for (int i = 0; i < 100; i++) {
            myDataList.add(new MyData(R.drawable.img_sample_son, "name " + i, "comment " + i));
        }
        final MyAdapter myAdapter = new MyAdapter(myDataList, this);
        _listView1.setAdapter(myAdapter);

        // 为 ListView 指定表头和表尾
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_view_listview_listviewdemo6_header, null, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_view_listview_listviewdemo6_footer, null, false);
        _listView1.addHeaderView(headerView);
        _listView1.addFooterView(footerView);
        // 是否在表头后绘制分隔线
        _listView1.setHeaderDividersEnabled(true);
        // 是否在表尾前绘制分隔线
        _listView1.setFooterDividersEnabled(true);
        // 分隔线的样式
        _listView1.setDivider(Helper.id2drawable(this, R.drawable.shape_listview_divider));
        // 分隔线的高度
        _listView1.setDividerHeight(Helper.dp2px(this, 1));

        // 不显示滚动条（对应的 xml 的属性为 android:scrollbars 其值为 none|horizontal|vertical）
        _listView1.setVerticalScrollBarEnabled(false);
        _listView1.setHorizontalScrollBarEnabled(false);

        // 更新数据后，刷新 ListView
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据源
                Random random = new Random();
                for (MyData myData : myDataList) {
                    myData.setName("name " + random.nextInt(10000));
                    myData.setComment("comment " + random.nextInt(10000));
                }

                // 通过调用 BaseAdapter 的 notifyDataSetChanged() 来刷新 ListView（重绘可见区域，不改变当前的滚动位置）
                myAdapter.notifyDataSetChanged();
                // 通过调用 BaseAdapter 的 notifyDataSetInvalidated() 来刷新 ListView（重绘整个控件，且滚动到顶部）
                // myAdapter.notifyDataSetInvalidated();
            }
        });

        // 滚动到指定位置
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setSelection(int position) - 直接定位到指定索引位置的 item 所在的位置（无滚动的动画效果）
                // _listView1.setSelection(50);

                // smoothScrollToPositionFromTop(int position, int offset, int duration) - 滚动到指定索引位置的 item 所在的位置（有滚动的动画效果）
                //     position - 需要滚动到的 item 的索引位置
                //     offset - 最终位置与指定滚动位置的偏移量（单位：像素）
                //     duration - 滚动到指定位置的时间（单位：毫秒）
                _listView1.smoothScrollToPositionFromTop(50, 0, 100);
            }
        });

        // 监听 ListView 的滚动状态
        _listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 0 - AbsListView.OnScrollListener.SCROLL_STATE_IDLE（当前没有发生滚动）
                // 1 - AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL（因为用户触摸导致的滚动）
                // 2 - AbsListView.OnScrollListener.SCROLL_STATE_FLING（因为惯性导致的滚动）
                _textView1.setText(String.format("scrollState:%d", scrollState));
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // firstVisibleItem - 当前可视区第一条 item 的索引位置
                // visibleItemCount - 当前可视区显示的 item 的总数
                // totalItemCount - ListView 的 item 的总数
                // AbsListView.getFirstVisiblePosition() - 当前可视区第一条 item 的索引位置
                // AbsListView.getLastVisiblePosition() - 当前可视区最后一条 item 的索引位置
                _textView2.setText(String.format("firstVisibleItem:%d, visibleItemCount:%d, totalItemCount:%d, AbsListView.getFirstVisiblePosition():%d, AbsListView.getLastVisiblePosition():%d",
                        firstVisibleItem, visibleItemCount, totalItemCount, view.getFirstVisiblePosition(), view.getLastVisiblePosition()));
            }
        });
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

        // 每构造一个 item 就会调用一次 getView() 来获取这个 item 的 view
        // 每次绘制 item 都会调用 getView()
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo6, parent, false);

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


















































// 我们可以通过设置：android:scrollbars="none" 或者 setVerticalScrollBarEnabled(true); 解决这个问题！
            /*

如何修改数据新增数据，如何程序来滚动滚动条



https://blog.csdn.net/zh175578809/article/details/72797535



             */