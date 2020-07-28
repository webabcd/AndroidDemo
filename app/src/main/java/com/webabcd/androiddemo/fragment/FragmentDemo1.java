/**
 * Fragment 的生命周期
 *
 * 详见 @drawable/img_fragment_lifecycle 生命周期图，大概说明一下，如下
 * 1、完整的生命周期 onAttach() -> onCreate() -> onCreateView() -> onActivityCreated() - onStart() -> onResume() -> onPause() -> onStop() -> onDestroyView() -> onDestroy() -> onDetach()
 * 2、在 onCreateView() 中加载布局文件
 * 3、父 Activity 的 onCreate() 完成后会调用子 Fragment 的 onActivityCreated()
 * 4、加入 Fragment 返回堆栈，然后再离开的话则会走到 onDestroyView()，恢复时会从 onCreateView() 开始走（这部分请参见 fragment/FragmentDemo2 中的说明）
 *
 *
 * 举个例子：
 * 1、打开 FragmentDemo1（在 xml 中引用了 Fragment1_1）
 * FragmentDemo1: onCreate start
 * Fragment1_1: onAttach
 * Fragment1_1: onCreate
 * Fragment1_1: onCreateView
 * FragmentDemo1: onCreate end
 * FragmentDemo1: onStart start
 * Fragment1_1: onActivityCreated
 * Fragment1_1: onStart
 * FragmentDemo1: onStart end
 * FragmentDemo1: onResume start
 * FragmentDemo1: onResume end
 * Fragment1_1: onResume
 *
 * 2、然后打开另一个 activity
 * FragmentDemo1: onPause start
 * Fragment1_1: onPause
 * FragmentDemo1: onPause end
 * FragmentDemo1: onStop start
 * Fragment1_1: onStop
 * FragmentDemo1: onStop end
 *
 * 3、关闭这个新打开的 activity
 * FragmentDemo1: onStart start
 * Fragment1_1: onStart
 * FragmentDemo1: onStart end
 * FragmentDemo1: onResume start
 * FragmentDemo1: onResume end
 * Fragment1_1: onResume
 *
 * 4、关闭当前 activity
 * FragmentDemo1: onPause start
 * Fragment1_1: onPause
 * FragmentDemo1: onPause end
 * FragmentDemo1: onStop start
 * Fragment1_1: onStop
 * FragmentDemo1: onStop end
 * FragmentDemo1: onDestroy
 * Fragment1_1: onDestroyView
 * Fragment1_1: onDestroy
 * Fragment1_1: onDetach
 * FragmentDemo1: onDestroy
 */

package com.webabcd.androiddemo.fragment;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class FragmentDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "FragmentDemo1";

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_fragmentdemo1);

        // 通过如下方式获取 xml 中的 fragment 对象
        // androidx.fragment.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment1);

        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentDemo1.this, FragmentDemo1_2.class);
                startActivity(intent);
            }
        });

        Log.d(LOG_TAG, "onCreate end");
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart start");
        super.onStart();
        Log.d(LOG_TAG, "onStart end");
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume start");
        super.onResume();
        Log.d(LOG_TAG, "onResume end");
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause start");
        super.onPause();
        Log.d(LOG_TAG, "onPause end");
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop start");
        super.onStop();
        Log.d(LOG_TAG, "onStop end");
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}