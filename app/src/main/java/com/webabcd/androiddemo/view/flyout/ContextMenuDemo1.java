/**
 * ContextMenu - 上下文菜单（长按 view 弹出的菜单）
 *
 * 本例会演示如何弹出一个包含父菜单和子菜单的 ContextMenu（数据可以来自 xml 或 java）
 *
 * 注：如果觉得 ContextMenu 实现不了需求，就用 PopupWindow 吧
 */

package com.webabcd.androiddemo.view.flyout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ContextMenuDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    // 用于保存当前显示的上下文菜单的关联的 view 的 id
    private int mCurrentViewId;
    // 用于保存当前显示的上下文菜单数据
    private ContextMenu mCurrentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_contextmenudemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // registerForContextMenu() - 为指定的 view 注册上下文菜单（注册后，长按指定的 view 就会显示上下文菜单）
        registerForContextMenu(mButton1);
        registerForContextMenu(mButton2);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentViewId = v.getId();

                // showContextMenu() - 手动显示上下文菜单
                v.showContextMenu();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentViewId = v.getId();
                v.showContextMenu();
            }
        });
    }

    // 长按通过 registerForContextMenu() 注册的 view 时，会调用此方法来构造并显示上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        mCurrentViewId = v.getId();
        mCurrentMenu = menu;

        if (v.getId() == R.id.button1) {
            // 从 xml 中加载 ContextMenu 的显示数据（参见 res/menu/menu_view_flyout_contextmenudemo1.xml）
            // 加载的数据会赋值给 menu，然后可以在 java 中对 menu 增删
            new MenuInflater(this).inflate(R.menu.menu_view_flyout_contextmenudemo1, menu);
        }
        else if (v.getId() == R.id.button2) {
            // 通过 java 构造 ContextMenu 的选项数据，增删都可以
            // add() - 添加菜单项
            // addSubMenu() - 添加具有子菜单的菜单项
            //     第 1 个参数：分组 id（没用）
            //     第 2 个参数：选项 id
            //     第 3 个参数：选项排序
            //     第 4 个参数：选项 title
            // setEnabled() - 指定当前菜单是否可用
            // setHeaderTitle() - 指定当前具有子菜单的菜单项展开后，它的标题需要显示的内容
            menu.add(2, 10002, 2, "item 2").setEnabled(false);
            menu.add(1, 10001, 1, "item 1");
            SubMenu subMenu = menu.addSubMenu(0, 10000, 0, "item 0").setHeaderTitle("item 0 下的子菜单");
            subMenu.add(0, 10000, 0, "item 0_0");
            subMenu.add(0, 10001, 1, "item 0_1");
            subMenu.add(0, 10002, 2, "item 0_2");
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // 选中 ContextMenu 中的项时会调用此方法
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // item.getItemId() - 选项的 id
        // item.getTitle() - 选项的 title

        if (mCurrentViewId == R.id.button1) {
            Toast.makeText(ContextMenuDemo1.this, String.format("我是页面上第 1 个按钮的上下文菜单，选中项为 id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();

            // 如果 item 数据来自 xml 的话，则可以通过此方式来判断用户点击的是哪个 item
            if (item.getItemId() == mCurrentMenu.getItem(1).getItemId()) {

            }
        }
        else if (mCurrentViewId == R.id.button2) {
            Toast.makeText(ContextMenuDemo1.this, String.format("我是页面上第 2 个按钮的上下文菜单，选中项为 id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
