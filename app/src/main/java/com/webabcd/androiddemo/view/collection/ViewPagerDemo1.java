/**
 * ViewPager - 页面切换控件
 *     setCurrentItem() - 指定 ViewPager 当前显示的页面的索引位置
 *     setAdapter() - 指定用于为 ViewPager 呈现数据的 PagerAdapter 对象
 *
 * 本例演示
 * 1、如何通过自定义的 PagerAdapter 来为 ViewPager 对象提供数据
 * 2、如何为 ViewPager 的每个页面添加类似 Tab 控件的标题
 */

package com.webabcd.androiddemo.view.collection;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;

public class ViewPagerDemo1 extends AppCompatActivity {

    private ViewPager mViewPager1;
    private ViewPager mViewPager2;
    private ViewPager mViewPager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_viewpagerdemo1);

        mViewPager1 = findViewById(R.id.viewPager1);
        mViewPager2 = findViewById(R.id.viewPager2);
        mViewPager3 = findViewById(R.id.viewPager3);

        sample();
    }

    private void sample() {
        ViewPager[] viewPagerList = {mViewPager1, mViewPager2, mViewPager3};
        for (ViewPager viewPager: viewPagerList) {
            // 用于保存 ViewPager 的每个 view
            ArrayList viewList = new ArrayList<View>();
            LayoutInflater layoutInflater = getLayoutInflater();
            viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo1_page1, null, false));
            viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo1_page2, null, false));
            viewList.add(layoutInflater.inflate(R.layout.activity_view_collection_viewpagerdemo1_page3, null, false));

            // 用于保存 ViewPager 的每个 view 的标题
            ArrayList titleList = new ArrayList<String>();
            titleList.add("红色");
            titleList.add("绿色");
            titleList.add("蓝色");

            // 实例化自定义的 PagerAdapter
            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList, titleList);
            // 指定 ViewPager 的 PagerAdapter
            viewPager.setAdapter(myPagerAdapter);
        }
    }

    /**
     * 自定义 PagerAdapter 用于为 ViewPager 提供数据
     */
    class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> mViewList;
        private ArrayList<String> mTitleList;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(ArrayList<View> viewList, ArrayList<String> titleList) {
            super();

            this.mViewList = viewList;
            this.mTitleList = titleList;
        }

        // ViewPager 的页面的数量
        @Override
        public int getCount() {
            return mViewList.size();
        }

        // 不知道有啥用，固定这么写就好
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 将 ViewPager 的指定索引位置的 view 添加进容器，并返回这个 view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);

            return view;
        }

        // 将 ViewPager 的指定索引位置的 view 从容器中删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        // 返回 ViewPager 的指定索引位置的 view 的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
