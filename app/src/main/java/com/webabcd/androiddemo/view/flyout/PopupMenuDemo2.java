/**
 * PopupMenu - 弹出式菜单
 *
 * 本例会演示如何自定义 PopupMenu 的样式
 * 需要通过 activity 的主题来指定 PopupMenu 的样式，参见 res/values/styles.xml 中的 MyTheme_MyPopupMenuStyle
 *
 * 注：如果觉得 PopupMenu 实现不了需求，就用 PopupWindow 吧
 */


package com.webabcd.androiddemo.view.flyout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class PopupMenuDemo2 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定 activity 的主题，用于修改 PopupMenu 的样式
        setTheme(R.style.MyTheme_MyPopupMenuStyle);

        setContentView(R.layout.activity_view_flyout_popupmenudemo2);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(PopupMenuDemo2.this, mButton1);
                popup.getMenuInflater().inflate(R.menu.menu_view_flyout_popupmenudemo2, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(PopupMenuDemo2.this, String.format("id:%d, title:%s", item.getItemId(), item.getTitle()), Toast.LENGTH_SHORT).show();

                        // 如果 item 数据来自 xml 的话，则可以通过此方式来判断用户点击的是哪个 item
                        if (item.getItemId() == popup.getMenu().getItem(1).getItemId()) {

                        }

                        return true;
                    }
                });
                popup.show();
            }
        });
    }
}
