/**
 * Toolbar - 工具栏（导航栏）
 * 本例用于演示 Toolbar 的基础知识，以及如何通过 Toolbar 右侧的按钮弹出 OptionMenu 菜单，以及如何在 Toolbar 上显示 OptionMenu 菜单中的选项
 *
 * 左侧按钮一般用于“返回键”
 *     这个左侧按钮我们称之为 navigation button
 * 右侧按钮一般用于弹出 OptionMenu 菜单（通过点击设备的“menu”键也会弹出 OptionMenu 菜单）
 *     这个右侧按钮以及在 Toolbar 上显示的 OptionMenu 菜单中的选项我们称之为 action menu
 *
 * 关于 OptionMenu 菜单的基础知识和样式修改可以参见 PopupMenu 的相关说明（请参见 view/flyout/PopupMenuDemo1, view/flyout/PopupMenuDemo2）
 * 注：如果觉得 OptionMenu 实现不了需求，就用 PopupWindow 吧
 *
 *
 * 另：Toolbar 替代了 ActionBar
 */

package com.webabcd.androiddemo.view.navigation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ToolBarDemo1 extends AppCompatActivity {

    private Toolbar mToolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_navigation_toolbardemo1);

        mToolbar1 = findViewById(R.id.toolbar1);

        sample();
    }

    private void sample() {
        // 点击 navigationIcon（左侧按钮的图标）的回调
        mToolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBarDemo1.this, "点击了左侧按钮", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置右侧用于弹出 OptionMenu 的按钮的图标，不指定的话会使用默认图标
        // 注：只有当 toolbar.getMenu() 有数据时，Toolbar 才会显示右侧的图标
        mToolbar1.setOverflowIcon(getResources().getDrawable(R.drawable.img_sample_son, null));
        // 从 xml 中加载 OptionMenu 的数据
        mToolbar1.inflateMenu(R.menu.menu_view_navigation_toolbardemo1);

        // 上面通过 toolbar.inflateMenu() 加载的 OptionMenu 数据会被自动同步到 toolbar.getMenu() 中
        // 只有当 toolbar.getMenu() 有数据时，Toolbar 才会显示右侧的图标
        // 支持将 OptionMenu 中的选项显示在 Toolbar 上，即支持 MenuItem 的 icon 和 showAsAction（参见 res/menu/menu_view_navigation_toolbardemo1.xml 中的说明）
        final Menu menu = mToolbar1.getMenu();

        // 可以在 java 中对 menu 做增删操作，类似 PopupMenu（参见 view/flyout/PopupMenuDemo1）
        // OptionMenu 也支持显示多级菜单，类似 PopupMenu（参见 view/flyout/PopupMenuDemo1）
        // 不同于 PopupMenu 的是，Toolbar 的 OptionMenu 的 menuItem.setIcon(), menuItem.setShowAsAction() 是有效果的，即支持将 OptionMenu 中的选项显示在 Toolbar 上（参见 res/menu/menu_view_navigation_toolbardemo1.xml 中的说明）
        // MenuItem menuItem = menu.add("item");

        // 弹出的 OptionMenu 中的选项被单击后的回调
        mToolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // item.getItemId() - 选项的 id
                // item.getTitle() - 选项的 title
                Toast.makeText(ToolBarDemo1.this, String.format("id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();

                // 如果 item 数据来自 xml 的话，则可以通过此方式来判断用户点击的是哪个 item
                if (item.getItemId() == menu.getItem(1).getItemId()) {

                }

                return true;
            }
        });
    }



    // 如果点击了设备的“menu”键，就会调用此方法来构造 OptionMenu 的数据
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_flyout_contextmenudemo1, menu);
        return true;
    }
    // 点击了设备的“menu”键，弹出 OptionMenu 后，其选项被单击后的回调
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(ToolBarDemo1.this, String.format("id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();
        return true;
    }
}
