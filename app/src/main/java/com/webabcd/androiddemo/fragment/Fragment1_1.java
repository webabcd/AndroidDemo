/**
 * Fragment1_1，由 FragmentDemo1 在 xml 中引用
 */

package com.webabcd.androiddemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webabcd.androiddemo.R;

public class Fragment1_1 extends Fragment {

    private final String LOG_TAG = "Fragment1_1";

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach");

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        // 通过 LayoutInflater 加载 xml（注意：这里的第 3 个参数必须是 false，因为对于 Fragment 来说，其会自动把该布局添加到 container 中）
        View view = inflater.inflate(R.layout.fragment_fragment_fragment1_1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");

        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "onPause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, "onStop");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(LOG_TAG, "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(LOG_TAG, "onDetach");

        super.onDetach();
    }
}
