/**
 * ListView 通过 SimpleAdapter 显示数据
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewDemo2 extends AppCompatActivity {

    private ListView _listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo2);

        _listView1 = (ListView) findViewById(R.id.listView1);

        sample();
    }

    private void sample() {
        String[] nameList = new String[]{"中国", "美国", "日本"};
        String[] commentList = new String[]{"我是中国", "我是美国", "我是日本"};
        int[] imgIdList = new int[]{R.drawable.img_sample_son, R.drawable.img_sample_son, R.drawable.img_sample_son};

        // List<Map<String, Object>> - 数据源
        //   Map<String, Object> - 每一个 item 数据
        //     String - 此 item 数据的某一个数据的 key
        //     Object - 此 item 数据的某一个数据的 key 对应的 value
        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < nameList.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("logo", imgIdList[i]);
            item.put("name", nameList[i]);
            item.put("comment", commentList[i]);
            itemList.add(item);
        }

        // 实例化 SimpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(this,
                itemList, // 数据源
                R.layout.item_view_listview_listviewdemo2, // 自定义项模板（参见 res/layout/item_view_listview_listviewdemo2.xml）
                new String[]{"logo", "name", "comment"}, // 数据的 key
                new int[]{R.id.imgLogo, R.id.txtName, R.id.txtComment}); // 项模板中的控件 id（控件的数据会被自动赋值为上面相同位置的 key 对应的 value）
        _listView1.setAdapter(myAdapter);
    }
}
