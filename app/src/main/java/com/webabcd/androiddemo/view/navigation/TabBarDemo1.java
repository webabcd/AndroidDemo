/**
 * 虽然没有官方的 TabBar 控件，但是可以自己实现一个，参见 view/navigation/TabBarDemo1_MyTabBar.java
 */

package com.webabcd.androiddemo.view.navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class TabBarDemo1 extends AppCompatActivity {

    private TabBarDemo1_MyTabBar mTabBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_navigation_tabbardemo1);

        mTabBar1 = findViewById(R.id.tabBar1);

        // 选中的 tab 项发生变化时的回调
        mTabBar1.setOnItemChangedListener(new TabBarDemo1_MyTabBar.OnItemChangedListener() {
            @Override
            public void onItemChanged(int itemIndex) {
                Toast.makeText(TabBarDemo1.this, "selected tab: " + itemIndex, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
