/**
 * Spinner - 列表选择框控件（演示如何通过 ArrayAdapter 显示数据）
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class SpinnerDemo3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mSpinner1;
    private Spinner mSpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_spinnerdemo3);

        mSpinner1 = findViewById(R.id.spinner1);
        mSpinner2 = findViewById(R.id.spinner2);

        // 选择项发生变化时触发的事件（注：第一次加载时也会触发此事件）
        mSpinner1.setOnItemSelectedListener(this);
        mSpinner2.setOnItemSelectedListener(this);

        sample();
    }

    private void sample() {
        // 通过指定的数组资源（需要显示的数据）和项模板（需要显示的样式）创建 ArrayAdapter
        // ArrayAdapter - 数组适配器（另外还有 SimpleAdapter, BaseAdapter 自定义适配器）
        //     数据来自指定的数组资源
        //     项模板为 simple_list_item_1（另外还有其他很多系统内置项模板，不一一列列举了，因为实际最常用的还是自定义项模板）
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.arrayCountry, android.R.layout.simple_list_item_1 );
        mSpinner1.setAdapter(adapter2);

        // 通过指定的项模板（需要显示的样式）和数组（需要显示的数据）创建 ArrayAdapter
        //     项模板为自定义的 res/layout/item_view_selection_spinnerdemo3.xml
        //     数据来自指定的数组
        String[] arrayData = {"中国", "美国", "日本"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,R.layout.item_view_selection_spinnerdemo3, arrayData);
        mSpinner2.setAdapter(adapter3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // parent 就是 ListView
        // view 就是被点击的 item
        // position 就是被点击的 item 的索引位置
        if (parent.getId() == R.id.spinner1) {
            Toast.makeText(this, String.format("spinner1 click position:%d", position), Toast.LENGTH_SHORT).show();
        }
        else if (parent.getId() == R.id.spinner2) {
            Toast.makeText(this, String.format("spinner2 click position:%d", position), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
