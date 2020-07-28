/**
 * Toolbar - 工具栏（导航栏）
 * 本例用于演示如果在 Toolbar 中显示自定义 view，以及如何清除自定义 view 与 Toolbar 两侧的间距，以及如何自定义弹出的 OptionMenu 的样式
 * “清除自定义 view 与 Toolbar 两侧的间距”和“自定义弹出的 OptionMenu 的样式”是通过修改主题实现的，参见 res/values/styles.xml 的 MyTheme_MyToolbarStyle
 *
 * 关于 OptionMenu 菜单的基础知识和样式修改可以参见 PopupMenu 的相关说明（请参见 view/flyout/PopupMenuDemo1, view/flyout/PopupMenuDemo2）
 * 注：如果觉得 OptionMenu 实现不了需求，就用 PopupWindow 吧
 */

package com.webabcd.androiddemo.view.navigation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.webabcd.androiddemo.R;

public class ToolBarDemo2 extends AppCompatActivity {

    private Toolbar mToolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定主题，以便“清除自定义 view 与 Toolbar 两侧的间距”和“自定义弹出的 OptionMenu 的样式”
        setTheme(R.style.MyTheme_MyToolbarStyle);

        setContentView(R.layout.activity_view_navigation_toolbardemo2);

        mToolbar1 = findViewById(R.id.toolbar1);

        sample();
    }

    private void sample() {
        // 从 xml 中加载 OptionMenu 的数据
        mToolbar1.inflateMenu(R.menu.menu_view_navigation_toolbardemo2);
    }
}
