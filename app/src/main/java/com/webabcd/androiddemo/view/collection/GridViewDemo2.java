/**
 * GridView - 网格控件
 *
 * 本例演示
 * 1、GridView 通过 BaseAdapter 显示数据
 * 2、GridView 的常用属性的说明（参见 xml 中的说明）
 * 3、其他更多知识点请参见 ListView 的说明（GridView 和 ListView 都继承自 AbsListView）
 */

package com.webabcd.androiddemo.view.collection;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewDemo2 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private GridView mGridView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_gridviewdemo2);

        mGridView1 = findViewById(R.id.gridView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();
    }

    private void sample() {
        // 构造数据
        List<MyData> myDataList = new ArrayList<MyData>();
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));
        myDataList.add(new MyData(R.drawable.img_sample_son));

        // 实例化自定义的 BaseAdapter
        MyAdapter myAdapter = new MyAdapter(myDataList, this);
        mGridView1.setAdapter(myAdapter);

        // 设置 stretchMode 属性
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 相当于 xml 中的 stretchMode="none"
                mGridView1.setStretchMode(GridView.NO_STRETCH);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 相当于 xml 中的 stretchMode="spacingWidth"
                mGridView1.setStretchMode(GridView.STRETCH_SPACING);
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 相当于 xml 中的 stretchMode="columnWidth"
                mGridView1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 相当于 xml 中的 stretchMode="spacingWidthUniform"
                mGridView1.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
            }
        });
    }

    // 自定义实体类
    private static class MyData {
        private int _logoId;

        public MyData() {
        }

        public MyData(int logoId) {
            this._logoId = logoId;
        }

        public int getLogoId() {
            return _logoId;
        }

        public void setLogoId(int logoId) {
            this._logoId = logoId;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                // 只 inflate() 一次 xml
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_collection_gridviewdemo2, parent, false);

                holder = new ViewHolder();
                holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                convertView.setTag(holder); // 将 holder 保存到 convertView 中
            } else {
                // 不再频繁地调用 findViewById()
                holder = (ViewHolder) convertView.getTag();
            }

            holder.imgLogo.setBackgroundResource(_myDataList.get(position).getLogoId());

            return convertView;
        }

        class ViewHolder {
            ImageView imgLogo;
        }
    }
}
