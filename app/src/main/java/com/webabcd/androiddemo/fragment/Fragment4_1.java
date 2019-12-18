/**
 * Fragment4_1，由 FragmentDemo4 动态加载，用于演示 Fragment 动画
 */

package com.webabcd.androiddemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webabcd.androiddemo.R;

import java.util.Random;

public class Fragment4_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_fragment4_1, container, false);

        Random random = new Random();
        view.setBackgroundColor(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));

        return view;
    }
}
