/**
 * ExpandableListView - 可展开的 ListView
 *
 * ExpandableListView 的 Adapter 可以用 BaseExpandableListAdapter（最常用的）, SimpleExpandableListAdapter, SimpleCursorTreeAdapter
 * 关于 ExpandableListView 的知识点，请参见本程序的 MainActivity 中的代码
 */

package com.webabcd.androiddemo.view.collection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class ExpandableListViewDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_expandablelistviewdemo1);
    }
}
