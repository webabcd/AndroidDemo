/**
 * ListView 通过 ArrayAdapter 显示数据
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.webabcd.androiddemo.R;

public class ListViewDemo1 extends AppCompatActivity {

    private ListView _listView2;
    private ListView _listView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo1);

        _listView2 = (ListView) findViewById(R.id.listView2);
        _listView3 = (ListView) findViewById(R.id.listView3);

        sample();
    }

    private void sample() {
        // 通过指定的数组资源（需要显示的数据）和项模板（需要显示的样式）创建 ArrayAdapter
        // ArrayAdapter - 数组适配器（另外还有 SimpleAdapter, BaseAdapter 自定义适配器）
        //     数据来自指定的数组资源
        //     项模板为 simple_list_item_1（另外还有其他很多系统内置项模板，不一一列列举了，因为实际最常用的还是自定义项模板）
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.arrayCountry, android.R.layout.simple_list_item_1 );
        _listView2.setAdapter(adapter2);

        // 通过指定的项模板（需要显示的样式）和数组（需要显示的数据）创建 ArrayAdapter
        //     项模板为自定义的 res/layout/item_view_listview_listviewdemo1.xml
        //     数据来自指定的数组
        String[] arrayData = {"中国", "美国", "日本"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,R.layout.item_view_listview_listviewdemo1, arrayData);
        _listView3.setAdapter(adapter3);
    }
}
