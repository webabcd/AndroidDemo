/**
 * Spinner - 列表选择框控件
 *     setOnItemSelectedListener(OnItemSelectedListener listener) - 选择项发生变化时的回调
 *
 * 本例主要介绍 Spinner 的基础
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class SpinnerDemo1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mSpinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_spinnerdemo1);

        mSpinner1 = findViewById(R.id.spinner1);

        // 选择项发生变化时触发的事件（注：第一次加载时也会触发此事件）
        mSpinner1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // 选择项发生变化时通过此回调来获取选中项的索引位置
        Toast.makeText(this,String.format("click position:%d", position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // 列表选择框控件中的数据被清空时会触发此回调
        Toast.makeText(this,"nothing selected",Toast.LENGTH_SHORT).show();
    }
}
