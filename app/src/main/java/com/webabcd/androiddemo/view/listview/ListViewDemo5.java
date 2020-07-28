/**
 * ListView 的单选和多选，以及 getView() 的调用时机
 *     boolean isItemChecked(int position) - 判断指定索引位置的 item 是否为选中状态
 *     int getCheckedItemPosition() - 单选状态下，获取选中状态的 item 的索引位置
 *     SparseBooleanArray getCheckedItemPositions() - 多选状态下，获取选中状态的 item 的索引位置集合
 *     long[] getCheckedItemIds() - 获取选中状态的 item 的 id 集合（此 id 就是在 BaseAdapter 中通过 getItemId() 返回的 id）
 *     int getCheckedItemCount() - 获取选中状态的 item 的总数
 *     setItemChecked(int position, boolean value) - 设置指定索引位置的 item 的选中状态
 *     clearChoices() - 清除全部 item 的选中状态
 *
 * 实现多选的话，很简单，只要在 xml 中设置 ListView 的 choiceMode 为 multipleChoice 即可，至于如何获取和设置 item 的选中状态只要参考上面那些 api 就好
 * 但是要实现选中状态和未选中状态样式不同的话，则要稍微麻烦一点，因为在 selector 中指定 state_selected="true 的话没有效果
 * 需要在 getView() 的时候，根据当前 item 的选中状态来决定使用哪个 selector
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo5 extends AppCompatActivity {

    private final String LOG_TAG = "ListViewDemo5";

    private ListView _listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo5);

        _listView1 = (ListView) findViewById(R.id.listView1);

        sample();
    }

    private void sample() {
        // 构造数据
        List<MyData> myDataList = new ArrayList<MyData>();
        for (int i = 0; i < 100; i++) {
            myDataList.add(new MyData(R.drawable.img_sample_son, "name " + i, "comment " + i));
        }
        final MyAdapter myAdapter = new MyAdapter(myDataList, this);
        _listView1.setAdapter(myAdapter);

        // item 被选中时触发的事件，但是实测无效
        // _listView1.setOnItemSelectedListener(...);

        // item 的点击事件
        _listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 每次点击 item 后，刷新 ListView 以便绘制当前可见 item 的选中状态
                myAdapter.notifyDataSetChanged();
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
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo5, parent, false);

                holder = new ViewHolder();
                holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                holder.button1 = (Button) convertView.findViewById(R.id.button1);
                convertView.setTag(holder); // 将 holder 保存到 convertView 中

                // 如果 ListView 的 item 中有 button 的话，默认情况下只能响应 button 的点击事件，而 item 的点击事件将被屏蔽
                // 如果需要既响应 button 的点击事件，又响应 item 的点击事件的话，则需要将 item 的 descendantFocusability 设置为 blocksDescendants（详见：item_view_listview_listviewdemo5.xml）
                holder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListViewDemo5.this, "button1 clicked: " + v.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // 不再频繁地调用 findViewById()
                holder = (ViewHolder) convertView.getTag();
            }

            holder.imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());
            holder.txtName.setText(_myDataList.get(position).getName());
            holder.txtComment.setText(_myDataList.get(position).getComment());
            holder.button1.setTag(position);

            // 如果当前 item 是选中状态则使用选中状态下的样式和点击样式，否则使用正常（未选中）状态下的样式和点击样式
            // 经测试，在 selector 中指定 state_selected="true 的话没有效果，所以就这么写了
            if (_listView1.isItemChecked(position)) {
                convertView.setBackgroundResource(R.drawable.selector_listview_item_background_selected);
            } else {
                convertView.setBackgroundResource(R.drawable.selector_listview_item_background_normal);
            }

            return convertView;
        }

        class ViewHolder {
            ImageView imgLogo;
            TextView txtName;
            TextView txtComment;
            Button button1;
        }
    }
}