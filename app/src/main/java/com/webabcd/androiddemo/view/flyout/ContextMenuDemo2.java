/**
 * ContextMenu - 上下文菜单（长按 view 弹出的菜单）
 *
 * 本例会演示如何自定义 ContextMenu 的样式
 * 需要通过 activity 的主题来指定 ContextMenu 的样式，参见 res/values/styles.xml 中的 MyTheme_MyContextMenuStyle
 *
 * 注：如果觉得 ContextMenu 实现不了需求，就用 PopupWindow 吧
 */


package com.webabcd.androiddemo.view.flyout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ContextMenuDemo2 extends AppCompatActivity {

    private Button mButton1;

    // 用于保存当前显示的上下文菜单数据
    private ContextMenu mCurrentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定 activity 的主题，用于修改 ContextMenu 的样式
        setTheme(R.style.MyTheme_MyContextMenuStyle);

        setContentView(R.layout.activity_view_flyout_contextmenudemo2);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        registerForContextMenu(mButton1);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showContextMenu() - 手动显示上下文菜单
                v.showContextMenu();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        mCurrentMenu = menu;

        new MenuInflater(this).inflate(R.menu.menu_view_flyout_contextmenudemo2, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(ContextMenuDemo2.this, String.format("id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();

        // 如果 item 数据来自 xml 的话，则可以通过此方式来判断用户点击的是哪个 item
        if (item.getItemId() == mCurrentMenu.getItem(1).getItemId()) {

        }

        return true;
    }
}
