/**
 * DrawerLayout - 抽屉布局（侧滑菜单）
 *
 * 本例演示
 * 1、如何通过手势侧滑显示侧边菜单，然后再点击空白处可隐藏侧边菜单
 * 2、如何禁止侧边菜单响应手势操作，而只能通过程序来控制侧边菜单的显示和隐藏
 * 3、如何修改当显示侧边菜单时，覆盖在主界面上的遮罩层的颜色
 * 4、如何自定义侧边菜单显示和隐藏的动画效果
 */

package com.webabcd.androiddemo.view.navigation;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class DrawerLayoutDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "DrawerLayoutDemo1";

    private DrawerLayout mDrawerLayout;
    private Button mButtonOpenRightDrawer;
    private Button mButtonCloseRightDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_navigation_drawerlayoutdemo1);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mButtonOpenRightDrawer = findViewById(R.id.buttonOpenRightDrawer);
        mButtonCloseRightDrawer = findViewById(R.id.buttonCloseRightDrawer);

        sample();
    }

    private void sample() {

        // 修改当显示侧边菜单时，覆盖在主界面上的遮罩层的颜色
        mDrawerLayout.setScrimColor(0x88ff0000);

        // 侧滑菜单在显示和隐藏的过程中的事件监听
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            // 侧边菜单显示的完整性发生变化时
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // slideOffset - 侧边菜单显示的完整性（0 - 1 之间）
                Log.d(LOG_TAG, String.format("%s onDrawerSlide %f", drawerView.getTag(), slideOffset));

                // 通过 slideOffset 这个值可以自定义侧边菜单显示和隐藏的动画效果，比如可以像下面这样为侧边菜单添加透明度动画效果
                // drawerView.setAlpha(slideOffset);
            }

            // 侧边菜单完全打开时
            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(LOG_TAG, String.format("%s onDrawerOpened", drawerView.getTag()));
            }

            // 侧边菜单完全关闭时
            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(LOG_TAG, String.format("%s onDrawerClosed", drawerView.getTag()));
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // 0 - 手指不在侧滑菜单上且侧边菜单处于稳定状态（DrawerLayout.STATE_IDLE）
                // 1 - 手指正在侧滑菜单上拖动时（DrawerLayout.STATE_DRAGGING）
                Log.d(LOG_TAG, String.format("onDrawerStateChanged %d", newState));
            }
        });

        /**
         * setDrawerLockMode() - 指定侧边菜单的锁定状态
         *     DrawerLayout.LOCK_MODE_UNLOCKED - 不锁定，可以通过手势打开或关闭
         *     DrawerLayout.LOCK_MODE_LOCKED_CLOSED - 锁定为关闭状态，不可以通过手势打开（可以通过程序打开），但是可以通过手势关闭
         *     DrawerLayout.LOCK_MODE_LOCKED_OPEN - 锁定为打开状态，不可以通过手势关闭（可以通过程序关闭），但是可以通过手势打开
         * openDrawer(int gravity, boolean animate), closeDrawer(int gravity, boolean animate) - 通过程序打开或关闭指定的侧边菜单
         *     gravity - Gravity.START 代表左侧侧边菜单；Gravity.END 代表右侧侧边菜单
         *     animate - 是否显示动画效果
         */

        // 不允许手势打开右边侧滑菜单
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        mButtonOpenRightDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开右边侧滑菜单
                mDrawerLayout.openDrawer(Gravity.END, true);
                // 不允许手势关闭右边侧滑菜单
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.END);
            }
        });
        mButtonCloseRightDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭右边侧滑菜单
                mDrawerLayout.closeDrawer(Gravity.END, true);
                // 不允许手势打开右边侧滑菜单
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            }
        });
    }
}
