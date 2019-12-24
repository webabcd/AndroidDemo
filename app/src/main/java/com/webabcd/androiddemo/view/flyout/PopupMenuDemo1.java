/**
 * PopupMenu - 弹出式菜单
 *
 * 本例会演示如何弹出一个包含父菜单和子菜单的 PopupMenu（数据可以来自 xml 或 java）
 *
 * 注：如果觉得 PopupMenu 实现不了需求，就用 PopupWindow 吧
 */

package com.webabcd.androiddemo.view.flyout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class PopupMenuDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_popupmenudemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // 弹出一个 PopupMenu（通过 xml 构造选项数据）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化 PopupMenu，并指定参照 view
                final PopupMenu popup = new PopupMenu(PopupMenuDemo1.this, mButton1);

                // setGravity() - 设置 PopupMenu 弹出后，相对于参照 view 的位置
                popup.setGravity(Gravity.LEFT);
                // 从 xml 中加载 PopupMenu 的显示数据（参见 res/menu/menu_view_flyout_popupmenudemo1.xml）
                // 加载的数据会赋值给 popup.getMenu()，然后可以在 java 中对 popup.getMenu() 增删
                popup.getMenuInflater().inflate(R.menu.menu_view_flyout_popupmenudemo1, popup.getMenu());

                // 选项单击后的回调
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // item.getItemId() - 选项的 id
                        // item.getTitle() - 选项的 title
                        Toast.makeText(PopupMenuDemo1.this, String.format("id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();

                        // 如果 item 数据来自 xml 的话，则可以通过此方式来判断用户点击的是哪个 item
                        if (item.getItemId() == popup.getMenu().getItem(1).getItemId()) {

                        }

                        return true;
                    }
                });

                // 显示 PopupMenu
                popup.show();

                // 隐藏 PopupMenu
                // popup.dismiss();
            }
        });

        // 弹出一个 PopupMenu（通过 java 构造选项数据）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化 PopupMenu，并指定参照 view
                PopupMenu popup = new PopupMenu(PopupMenuDemo1.this, mButton2);

                // setGravity() - 设置 PopupMenu 弹出后，相对于参照 view 的位置
                popup.setGravity(Gravity.RIGHT);
                // getMenu() - 获取当前 PopupMenu 的选项列表
                Menu menu = popup.getMenu();
                // 通过 java 构造 PopupMenu 的选项数据，增删都可以
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

                // 选项单击后的回调
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // item.getItemId() - 选项的 id
                        // item.getOrder() - 选项的排序
                        // item.getTitle() - 选项的 title
                        Toast.makeText(
                                PopupMenuDemo1.this,
                                String.format("groupId%d, id:%d, order:%d, title:%s", item.getGroupId(), item.getItemId(), item.getOrder(), item.getTitle()),
                                Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                // 显示 PopupMenu
                popup.show();

                // 隐藏 PopupMenu
                // popup.dismiss();
            }
        });
    }
}
