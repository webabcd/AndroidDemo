/**
 * 通过 ExpandableListView 做本程序的导航
 */

package com.webabcd.androiddemo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webabcd.androiddemo.utils.Helper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // 获取导航数据
        String jsonString = Helper.getAssetString("site_map.json", this);
        Type type = new TypeToken<List<MainNavigationBean>>() { }.getType();
        Gson gson = new Gson();
        final ArrayList<MainNavigationBean> navigationBeanList = gson.fromJson(jsonString, type);

        // 为 ExpandableListView 提供 Adapter
        final MainExpandableListAdapter adapter = new MainExpandableListAdapter(navigationBeanList);
        mExpandableListView.setAdapter(adapter);

        // 父的展开事件监听
        adapter.setOnGroupExpandedListener(new OnGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition) {
                // 每次展开一个分组后，关闭其他的分组
                int groupLength = mExpandableListView.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < groupLength; i++) {
                    if (i != groupPosition && mExpandableListView.isGroupExpanded(i)) {
                        mExpandableListView.collapseGroup(i);
                    }
                }
            }
        });

        // 父的点击事件监听
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // 要返回 false
                return false;
            }
        });

        // 子的点击事件监听
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String className = "com.webabcd.androiddemo" + navigationBeanList.get(groupPosition).getNodeList().get(childPosition).getUrl();
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.webabcd.androiddemo", className);
                intent.setComponent(componentName);
                startActivity(intent);

                return true;
            }
        });
    }
}
