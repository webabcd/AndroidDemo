/**
 * ListView 长按弹出上下文菜单
 *
 * 有两种方式可以实现
 * 第 1 种方式: registerForContextMenu()
 * 第 2 种方式: setOnCreateContextMenuListener()
 */

package com.webabcd.androiddemo.view.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.Locale;

public class ListViewDemo9 extends AppCompatActivity {

    private ListView _listView1;
    private ListView _listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo9);

        _listView1 = (ListView) findViewById(R.id.listView1);
        _listView2 = (ListView) findViewById(R.id.listView2);

        sample();
    }

    private void sample() {
        String[] arrayData = {"中国", "美国", "日本"};
        _listView1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayData));
        _listView2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayData));

        // 第 1 种方式: 通过 registerForContextMenu() 实现 ListView 长按弹出上下文菜单
        this.registerForContextMenu(_listView1);

        // 第 2 种方式: 通过 setOnCreateContextMenuListener() 实现 ListView 长按弹出上下文菜单
        _listView2.setOnCreateContextMenuListener(mCreateContextMenuListener);
    }

    // 第 1 种方式: 通过 registerForContextMenu() 构造上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("title");
        menu.add(0, 11, 1, "item 1");
        menu.add(0, 22, 2, "item 2");
        menu.add(0, 33, 3, "item 3");
    }

    // 第 2 种方式: 通过 setOnCreateContextMenuListener() 构造上下文菜单
    private View.OnCreateContextMenuListener mCreateContextMenuListener= new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("title");
            contextMenu.add(0, 111, 1, "item 1");
            contextMenu.add(0, 222, 2, "item 2");
            contextMenu.add(0, 333, 3, "item 3");
        }
    };

    // 第 1 种方式和第 2 种方式实现的 ListView 长按弹出上下文菜单，在选中菜单项后都会走到这里
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 获取当前被选中的 item 的信息
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        // item.getItemId() - 被选中的菜单项的 id
        // menuInfo.position - 被选中的数据项的索引位置
        Toast.makeText(this, String.format(Locale.US, "menuItemId:%d, dataItemPosition:%d", item.getItemId(), menuInfo.position), Toast.LENGTH_SHORT).show();

        return true;
    }




}


