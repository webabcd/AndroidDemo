/**
 * Fragment 与 Activity 的交互
 *
 * activity 向 fragment 传值
 * 1、实例化 fragment 之后，通过 setArguments() 设置参数，之后可通过 getArguments() 获取参数
 * 2、直接调用 fragment 对象中的方法传值
 * 3、直接获取 fragment 对象中的组件
 *
 * fragment 向 activity 传值（fragment 可通过 getActivity() 获取父 activity 对象）
 * 1、通过回调的方式实现，推荐
 * 2、直接调用父 activity 对象中的方法传值（因为 fragment 可能会被不同的 activity 引用，所以不推荐这种方式）
 * 3、直接获取父 activity 对象中的组件（因为 fragment 可能会被不同的 activity 引用，所以不推荐这种方式）
 */

package com.webabcd.androiddemo.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class FragmentDemo3 extends AppCompatActivity implements Fragment3_1.OnFragmentInteractionListener {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_fragmentdemo3);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            // 添加一个 fragment 对象，并在构造 fragment 对象时为其传值
                            // 实例化 fragment 对象后，通过其 setArguments() 设置参数，之后可以通过 getArguments() 获取参数
                            .add(R.id.container, Fragment3_1.newInstance("aaaaaa"), "myTag")
                            .commit();
                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment3_1 fragment = (Fragment3_1)fragmentManager.findFragmentByTag("myTag");
                if (fragment != null) {
                    // 直接调用 fragment 对象的方法为其传值
                    fragment.setParam("bbbbbb");
                }
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment != null) {
                    // 获取 fragment 对象中的组件
                    TextView textView = fragment.getView().findViewById(R.id.textView1);
                    textView.append("cccccc" + "\n");
                }
            }
        });
    }

    // 用于 fragment 直接调用 activity 中的方法传值
    public void setParam(String param) {
        mTextView1.append(param + "\n");
    }

    // 用于接收 fragment 回调 activity 的数据
    @Override
    public void onFragmentInteraction(String param) {
        mTextView1.append(param + "\n");
    }
}
