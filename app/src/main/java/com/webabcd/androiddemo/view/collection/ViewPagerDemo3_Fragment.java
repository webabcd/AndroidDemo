/**
 * 引用示例参见 view/collection/ViewPagerDemo3.java
 */

package com.webabcd.androiddemo.view.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class ViewPagerDemo3_Fragment extends Fragment {

    private final String LOG_TAG = "VP_Fragment";

    private int mBackgroundColor;

    public static ViewPagerDemo3_Fragment newInstance(int backgroundColor) {
        ViewPagerDemo3_Fragment fragment = new ViewPagerDemo3_Fragment();
        Bundle args = new Bundle();
        args.putInt("backgroundColor", backgroundColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBackgroundColor = getArguments().getInt("backgroundColor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_collection_viewpagerdemo3, container, false);
        view.setBackgroundColor(mBackgroundColor);

        // 用于验证 FragmentPagerAdapter 会缓存 3 个页，即当前页、左边页和右边页；未被缓存的 fragment 不会调用其 destroy
        Log.d(LOG_TAG + "_" + Helper.int2Hex(mBackgroundColor), "onCreateView");

        return view;
    }

    @Override
    public void onResume() {
        // 用于验证 FragmentPagerAdapter 会缓存 3 个页，即当前页、左边页和右边页；未被缓存的 fragment 不会调用其 destroy
        Log.d(LOG_TAG + "_" + Helper.int2Hex(mBackgroundColor), "onResume");

        super.onResume();
    }

    @Override
    public void onPause() {
        // 用于验证 FragmentPagerAdapter 会缓存 3 个页，即当前页、左边页和右边页；未被缓存的 fragment 不会调用其 destroy
        Log.d(LOG_TAG + "_" + Helper.int2Hex(mBackgroundColor), "onPause");

        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 用于验证 FragmentPagerAdapter 会缓存 3 个页，即当前页、左边页和右边页；未被缓存的 fragment 不会调用其 destroy
        Log.d(LOG_TAG + "_" + Helper.int2Hex(mBackgroundColor), "onDestroy");

        super.onDestroy();
    }
}
