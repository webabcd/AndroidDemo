/**
 * Spinner - 列表选择框控件（演示如何通过 SimpleAdapter 显示数据）
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerDemo4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mSpinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_spinnerdemo4);

        mSpinner1 = findViewById(R.id.spinner1);

        // 选择项发生变化时触发的事件（注：第一次加载时也会触发此事件）
        mSpinner1.setOnItemSelectedListener(this);

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
                R.layout.item_view_selection_spinnerdemo4, // 自定义项模板（参见 res/layout/item_view_selection_spinnerdemo4.xml）
                new String[]{"logo", "name", "comment"}, // 数据的 key
                new int[]{R.id.imgLogo, R.id.txtName, R.id.txtComment}); // 项模板中的控件 id（控件的数据会被自动赋值为上面相同位置的 key 对应的 value）
        mSpinner1.setAdapter(myAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // parent 就是 ListView
        // view 就是被点击的 item
        // position 就是被点击的 item 的索引位置
        Toast.makeText(this,String.format("click position:%d", position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
