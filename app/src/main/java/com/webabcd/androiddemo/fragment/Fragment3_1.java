/**
 * Fragment3_1，由 FragmentDemo3 动态加载，用于演示 Fragment 与 Activity 的交互
 *
 * Fragment
 *     setArguments() - 设置参数
 *     getArguments() - 获取参数
 *     getActivity() - 获取父 activity 对象
 */

package com.webabcd.androiddemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class Fragment3_1 extends Fragment {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private String mParam;

    // 用于 fragment 向 activity 传值
    private OnFragmentInteractionListener mListener;

    public Fragment3_1() {

    }

    // 在 fragment 对象的构造阶段，通过 setArguments() 设置参数
    public static Fragment3_1 newInstance(String param) {
        Fragment3_1 fragment = new Fragment3_1();
        Bundle args = new Bundle();
        args.putString("p1", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 可以在 onCreate() 阶段，通过 getArguments() 获取参数
        if (getArguments() != null) {
            mParam = getArguments().getString("p1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_fragment3_1, container, false);

        mTextView1 = view.findViewById(R.id.textView1);
        mTextView1.setText(mParam + "\n");

        mButton1 = view.findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // fragment 向 activity 传值（通过回调的方式）
                    mListener.onFragmentInteraction("xxxxxx");
                }
            }
        });

        mButton2 = view.findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取 fragment 对象的父 activity
                FragmentDemo3 parent = (FragmentDemo3)getActivity();
                if (parent != null) {
                    // 直接调用父 activity 对象的方法传值
                    // 因为 fragment 可能会被不同的 activity 引用，所以不建议这样做，还是建议通过回调的方式实现 fragment 向 activity 传值
                    parent.setParam("yyyyyy");
                }
            }
        });

        mButton3 = view.findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取 fragment 对象的父 activity
                Activity parent = getActivity();
                if (parent != null) {
                    // 获取父 activity 中的组件
                    TextView tt = parent.findViewById(R.id.textView1);
                    tt.append("zzzzzz\n");
                }
            }
        });

        return view;
    }

    // 用于 activity 直接调用 fragment 中的方法传值
    public void setParam(String param) {
        mTextView1.append(param + "\n");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 在 onAttach() 阶段保存回调对象
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // 在 onDetach() 阶段置空回调对象
        mListener = null;
    }

    // 用于 fragment 向 activity 传值的接口
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String param);
    }
}
