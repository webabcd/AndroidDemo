/**
 * ViewPager - 页面切换控件
 *     setCurrentItem() - 指定 ViewPager 当前显示的页面的索引位置
 *     setAdapter() - 指定用于为 ViewPager 呈现数据的 PagerAdapter 对象
 *
 * 本例演示
 * 1、如何通过自定义的 FragmentStatePagerAdapter 来为 ViewPager 对象提供数据
 * 2、FragmentStatePagerAdapter 会缓存 3 个页，即当前页、左边页和右边页
 *    未被缓存的 fragment 会调用其 destroy
 * 3、FragmentPagerAdapter 会缓存 3 个页，即当前页、左边页和右边页
 *    未被缓存的 fragment 不会调用其 destroy
 */

package com.webabcd.androiddemo.view.collection;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDemo4 extends AppCompatActivity {

    private ViewPager mViewPager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_viewpagerdemo4);

        mViewPager1 = findViewById(R.id.viewPager1);

        sample();
    }

    private void sample() {
        // 用于保存 ViewPager 的每个 fragment
        final ArrayList fragmentList = new ArrayList<Fragment>();
        fragmentList.add(ViewPagerDemo4_Fragment.newInstance(0xffff0000));
        fragmentList.add(ViewPagerDemo4_Fragment.newInstance(0xff00ff00));
        fragmentList.add(ViewPagerDemo4_Fragment.newInstance(0xff0000ff));
        fragmentList.add(ViewPagerDemo4_Fragment.newInstance(0xff000000));
        fragmentList.add(ViewPagerDemo4_Fragment.newInstance(0xffffffff));

        // 实例化自定义的 FragmentStatePagerAdapter
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        // 指定 ViewPager 的 FragmentStatePagerAdapter
        mViewPager1.setAdapter(myFragmentPagerAdapter);
    }

    /**
     * 自定义 FragmentStatePagerAdapter 用于为 ViewPager 提供数据
     */
    class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;

        public MyFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
            super(fragmentManager);

            this.mFragmentManager = fragmentManager;
            this.mFragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
    }
}
