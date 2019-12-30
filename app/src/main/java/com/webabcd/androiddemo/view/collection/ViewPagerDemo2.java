/**
 * ViewPager - 页面切换控件
 *     setCurrentItem() - 指定 ViewPager 当前显示的页面的索引位置
 *     setAdapter() - 指定用于为 ViewPager 呈现数据的 PagerAdapter 对象
 *
 * 本例演示
 * 1、如何通过自定义的 PagerAdapter 来为 ViewPager 对象提供数据
 * 2、PagerAdapter 会缓存 3 个页，即当前页、左边页和右边页
 *    需要被缓存就调用 instantiateItem()，不需要被缓存就调用 destroyItem()
 * 3、如何为 ViewPager 添加自定义的类似 Tab 控件的标题
 * 4、如何在 ViewPager 的页面切换时为 Tab 控件的标题切换增加动画效果
 * 5、如何监听 ViewPager 的页面切换事件
 */

package com.webabcd.androiddemo.view.collection;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.ArrayList;

public class ViewPagerDemo2 extends AppCompatActivity {

    private final String LOG_TAG = "ViewPagerDemo2";

    private ViewPager mViewPager1;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    // ViewPager 中当前选中的页面的 Tab 控件标题的背景
    private ImageView mImageViewTabItemBackground;

    // 用于保存 ViewPager 中当前选中的页面的索引位置
    private int mCurrentTabItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_viewpagerdemo2);

        mViewPager1 = findViewById(R.id.viewPager1);
        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);
        mImageViewTabItemBackground = findViewById(R.id.imageViewTabItemBackground);

        sample();
    }

    private void sample() {
        // 用于保存 ViewPager 的每个 view
        final ArrayList viewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater();
        viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo2_page1, null, false));
        viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo2_page2, null, false));
        viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo2_page3, null, false));

        // 实例化自定义的 PagerAdapter
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
        // 指定 ViewPager 的 PagerAdapter
        mViewPager1.setAdapter(myPagerAdapter);


        // 初始化 Tab 控件标题背景的位置
        // 由于要根据控件的尺寸计算其位置，为了避免控件尺寸还没测量出来，所以用 Handler 来避免此问题
        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                // 用 0 秒动画来指定位置（因为已将 ImageView 的 scaleType 设置为 fitXY 了，所以不能用 Matrix 变换了）
                animateTabItemBackground(0, getTabItemX(viewList.size(), 0), 0);
            }
        });

        // ViewPager 的显示页发生变化时的事件监听
        mViewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 当前显示的页发生变化时
            @Override
            public void onPageSelected(int position) {
                // position - 选中的页的索引位置

                // ViewPager 的当前页发生变化时，Tab 控件标题切换时，其背景变化的动画
                animateTabItemBackground(getTabItemX(viewList.size(), mCurrentTabItemIndex), getTabItemX(viewList.size(), position), 100);
                mCurrentTabItemIndex = position;

                Log.d(LOG_TAG, String.format("onPageSelected %d", position));
            }

            // 页面滚动时
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // position - 选中的页的索引位置
                // positionOffset - 当前显示的页与选中的页的偏移比例（0 - 1 之间）
                // positionOffsetPixels - 当前显示的页与选中的页的偏移像素数
                Log.d(LOG_TAG, String.format("onPageScrolled, position:%d, positionOffset:%f, positionOffsetPixels:%d", position, positionOffset, positionOffsetPixels));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 0 - 手指不在 ViewPager 上且 ViewPager 处于稳定状态（ViewPager.SCROLL_STATE_IDLE）
                // 1 - 手指正在 ViewPager 上拖动时（ViewPager.SCROLL_STATE_DRAGGING）
                Log.d(LOG_TAG, String.format("onPageScrollStateChanged %d", state));
            }
        });


        // 通过程序指定 ViewPager 当前需要显示的页
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager1.setCurrentItem(0);
            }
        });
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager1.setCurrentItem(1);
            }
        });
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager1.setCurrentItem(2);
            }
        });
    }

    // ViewPager 的当前页发生变化时，Tab 控件标题切换时，其背景变化的动画
    private void animateTabItemBackground(float currentPosition, float targetPosition, int duration) {
        Animation animation = new TranslateAnimation(currentPosition, targetPosition, 0, 0);
        animation.setFillAfter(true); // 停留在动画结束位置
        animation.setDuration(duration);
        mImageViewTabItemBackground.startAnimation(animation);
    }

    // 计算当前显示页的 Tab 控件标题背景的位置
    private int getTabItemX(int titleCount, int index) {
        return (int)((Helper.getScreenSize(this).x / titleCount) * (index + 0.5)) - (mImageViewTabItemBackground.getWidth() / 2);
    }

    /**
     * 自定义 PagerAdapter 用于为 ViewPager 提供数据
     */
    class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> mViewList;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(ArrayList<View> viewList) {
            super();

            this.mViewList = viewList;
        }

        // ViewPager 的页面的数量
        @Override
        public int getCount() {
            return mViewList.size();
        }

        // 固定这么写就好
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 将 ViewPager 的指定索引位置的 view 添加进容器，并返回这个 view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);

            // 用于验证 PagerAdapter 会缓存 3 个页，即当前页、左边页和右边页
            Log.d(LOG_TAG, "instantiateItem: " + position);

            return view;
        }

        // 将 ViewPager 的指定索引位置的 view 从容器中删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));

            // 用于验证 PagerAdapter 会缓存 3 个页，即当前页、左边页和右边页
            Log.d(LOG_TAG, "destroyItem: " + position);
        }
    }
}
