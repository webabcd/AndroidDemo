/**
 * ListView 多选删除
 *
 * 本例的数据源的每条数据都有一个 isChecked 属性，选中或取消选中都要修改这个属性，是否是选中状态以及是否需要删除也都是通过这个属性判断
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo10 extends AppCompatActivity {

    private ListView mListView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    List<ListViewDemo10.MyData> mDataList;
    private ListViewDemo10.MyAdapter mAdapter;
    // 当前界面是否是编辑模式
    private boolean mIsEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo10);

        mListView1 = findViewById(R.id.listView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();
    }

    private void sample() {
        // 构造数据
        mDataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDataList.add(new ListViewDemo10.MyData("item " + i, false));
        }

        // 实例化自定义的 BaseAdapter
        mAdapter = new ListViewDemo10.MyAdapter(mDataList, this);
        mListView1.setAdapter(mAdapter);

        // 显示复选框，并进入编进模式
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton1.setVisibility(View.GONE);
                mButton2.setVisibility(View.VISIBLE);
                mButton3.setVisibility(View.VISIBLE);
                mButton4.setVisibility(View.VISIBLE);
                mIsEditing = true;

                // 刷新 ListView
                mAdapter.notifyDataSetChanged();
            }
        });

        // 隐藏复选框，并退出编进模式
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton1.setVisibility(View.VISIBLE);
                mButton2.setVisibility(View.GONE);
                mButton3.setVisibility(View.GONE);
                mButton4.setVisibility(View.GONE);
                mIsEditing = false;

                // 刷新 ListView
                mAdapter.notifyDataSetChanged();
            }
        });

        // 全选
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ListViewDemo10.MyData data : mDataList) {
                    data.setIsChecked(true);
                }

                // 刷新 ListView
                mAdapter.notifyDataSetChanged();
            }
        });

        // 删除选中
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // api level 24 或以上，且 jdk 1.8 则可以通过如下方式删除
                // mDataList.removeIf(p -> p.getIsChecked());

                int dataCount = mDataList.size();
                for (int i = 0; i < dataCount; i++) {
                    ListViewDemo10.MyData data = mDataList.get(i);
                    if (data.getIsChecked()) {
                        mDataList.remove(i);

                        dataCount--;
                        i--;
                    }
                }

                // 刷新 ListView
                mAdapter.notifyDataSetChanged();
            }
        });

        // item 的点击事件
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mIsEditing) {
                    ListViewDemo10.MyData data = mDataList.get(position);
                    data.setIsChecked(!data.getIsChecked());

                    // 刷新 ListView
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    class MyData {
        private String _name;
        private boolean _isChecked; // 是否是选中状态
        public MyData() {
        }
        public MyData(String name, boolean isChecked) {
            this._name = name;
            this._isChecked = isChecked;
        }
        public String getName() {
            return _name;
        }
        public void setName(String name) {
            this._name = name;
        }
        public boolean getIsChecked() {
            return _isChecked;
        }
        public void setIsChecked(boolean isChecked) {
            this._isChecked = isChecked;
        }
    }

    class MyAdapter extends BaseAdapter {

        private List<ListViewDemo10.MyData> _myDataList;
        private Context _context;

        public MyAdapter(List<ListViewDemo10.MyData> myDataList, Context context) {
            this._myDataList = myDataList;
            this._context = context;
        }

        @Override
        public int getCount() {
            return _myDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return _myDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewDemo10.MyData data = _myDataList.get(position);

            ListViewDemo10.MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_listview_listviewdemo10, parent, false);
                holder = new ListViewDemo10.MyAdapter.ViewHolder();
                holder.txtName = convertView.findViewById(R.id.txtName);
                holder.checkBox = convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);
            } else {
                holder = (ListViewDemo10.MyAdapter.ViewHolder) convertView.getTag();
            }

            // 复选框选中或取消选中后要更新数据源的 isChecked 属性
            //     如果使用了 convertView 复用的方式，则必须在 setChecked() 之前 setOnCheckedChangeListener()
            //     否则你的 setChecked() 可能会触发其他的 item 的 checkbox 的 setOnCheckedChangeListener()
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setIsChecked(isChecked);
                }
            });

            holder.txtName.setText(data.getName());
            // 根据当前界面是否是编辑模式决定是否显示复选框
            if (mIsEditing) {
                holder.checkBox.setVisibility(View.VISIBLE);
                // 根据数据源的 isChecked 属性决定复选框是否是选中状态
                holder.checkBox.setChecked(data.getIsChecked());
            } else {
                holder.checkBox.setVisibility(View.GONE);
            }

            return convertView;
        }

        class ViewHolder {
            TextView txtName;
            CheckBox checkBox;
        }
    }
}


