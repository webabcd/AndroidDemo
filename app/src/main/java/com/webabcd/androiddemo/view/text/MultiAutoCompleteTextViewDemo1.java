/**
 * MultiAutoCompleteTextView - 自动完成文本框（支持多选），MultiAutoCompleteTextView 继承自 AutoCompleteTextView
 * 与 AutoCompleteTextView 不同的是，MultiAutoCompleteTextView 支持多选，需要通过 setTokenizer() 指定分隔符（不指定则不工作）
 *
 * 本例演示
 * 1、如何通过 ArrayAdapter 绑定数据和模板
 * 2、如何通过自定义的 BaseAdapter 并实现 Filterable 接口来绑定数据和模板
 * 3、如何监听下拉列表的点击事件，并获取选中项的数据对象
 */

package com.webabcd.androiddemo.view.text;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class MultiAutoCompleteTextViewDemo1 extends AppCompatActivity {

    private MultiAutoCompleteTextView mMultiAutoCompleteTextView1;
    private MultiAutoCompleteTextView mMultiAutoCompleteTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_multiautocompletetextviewdemo1);

        mMultiAutoCompleteTextView1 = findViewById(R.id.multiAutoCompleteTextView1);
        mMultiAutoCompleteTextView2 = findViewById(R.id.multiAutoCompleteTextView2);

        sample1();
        sample2();
    }

    // 通过 ArrayAdapter 绑定数据和模板
    private void sample1() {
        String[] data = new String[]{"abc", "abcd", "abcde", "abcdef", "abcdefg"};
        mMultiAutoCompleteTextView1.setAdapter(new ArrayAdapter<String>(MultiAutoCompleteTextViewDemo1.this, android.R.layout.simple_dropdown_item_1line, data));
        // 指定分隔符（不指定则不工作）
        mMultiAutoCompleteTextView1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    // 通过自定义的 BaseAdapter 并实现 Filterable 接口来绑定数据和模板
    private void sample2() {
        // 构造数据
        final List<MyData> myDataList = new ArrayList<MyData>();
        myDataList.add(new MyData("abc", "abc"));
        myDataList.add(new MyData("abcd", "abcd"));
        myDataList.add(new MyData("abcde", "abcde"));
        myDataList.add(new MyData("abcdef", "abcdef"));
        myDataList.add(new MyData("abcdefg", "abcdefg"));

        // 实例化自定义的 BaseAdapter（注：为 MultiAutoCompleteTextView 提供数据必须要实现 Filterable 接口）
        final MyAdapter myAdapter = new MyAdapter(myDataList, this);
        mMultiAutoCompleteTextView2.setAdapter(myAdapter);
        // 指定分隔符（不指定则不工作）
        mMultiAutoCompleteTextView2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // 点击了下拉列表中的某一项时的事件
        mMultiAutoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 下面这个获取的不是选中项的对象
                // MyData myData = myDataList.get(position);

                // 下面这个获取的才是选中项的对象
                MyData myData = (MyData) parent.getItemAtPosition(position);
            }
        });
    }

    // 自定义实体类
    class MyData {
        private String _name;
        private String _comment;

        public MyData() {
        }

        public MyData( String name, String comment) {
            this._name = name;
            this._comment = comment;
        }

        public String getName() {
            return _name;
        }

        public String getComment() {
            return _comment;
        }

        public void setName(String name) {
            this._name = name;
        }

        public void setComment(String comment) {
            this._comment = comment;
        }

        // 选中下拉列表的某一项后，将调用此项对应的对象的 toString() 方法，并将其结果赋值给 MultiAutoCompleteTextView 文本框
        // 所以如果绑定的数据是自定义对象的话，要重写其 toString() 方法，以便在 MultiAutoCompleteTextView 的文本框中显示合适的值
        @Override
        public String toString() {
            return _name;
        }
    }

    // 自定义 BaseAdapter，并实现 Filterable 接口（注：绑定到 MultiAutoCompleteTextView 时，必须要实现 Filterable 接口）
    class MyAdapter extends BaseAdapter implements Filterable {

        private List<MyData> _myDataListOriginal; // 原始数据
        private List<MyData> _myDataListFiltered; // 筛选后的数据（下拉列表需要显示的数据）

        private Context _context;

        public MyAdapter(List<MyData> myDataList, Context context) {
            this._myDataListFiltered = new ArrayList<MyData>();
            this._myDataListOriginal = myDataList;
            this._context = context;
        }

        @Override
        public int getCount() {
            return _myDataListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return _myDataListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(R.layout.item_view_text_multiautocompletetextviewdemo1, parent, false);

                holder = new ViewHolder();
                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtName.setText(_myDataListFiltered.get(position).getName());
            holder.txtComment.setText(_myDataListFiltered.get(position).getComment());

            return convertView;
        }

        // 实现 Filterable 接口
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                /**
                 * 删选出符合条件的数据的集合
                 * @param constraint 用户输入的数据
                 */
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<MyData> list = new ArrayList<>();
                    if(constraint != null){
                        for (MyData myData : _myDataListOriginal) {
                            if (myData.getName().contains(constraint)) {
                                list.add(myData);
                            }
                        }
                    }

                    // 符合条件的数据的数量
                    results.count = list.size();
                    // 符合条件的数据的集合
                    results.values = list;

                    return results;
                }

                /**
                 * 筛选逻辑执行完毕
                 * @param constraint 用户输入的数据
                 * @param results 由 performFiltering() 返回的筛选结果
                 */
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    // 更新下拉框需要显示的数据
                    _myDataListFiltered = (List<MyData>) results.values;
                    // 刷新下拉框
                    notifyDataSetChanged();
                }
            };

            return filter;
        }

        class ViewHolder {
            TextView txtName;
            TextView txtComment;
        }
    }
}
