/**
 * Fragment 的动态加载和生命周期，以及 Fragment 的返回堆栈
 *
 * FragmentManager - fragment 管理器（v4 包通过 FragmentActivity 的 getSupportFragmentManager() 来获取 FragmentManager 对象）
 *     beginTransaction()/commit() - 开头调用 beginTransaction()，结尾调用 commit()，中间的多个操作会当做一个事务来执行
 *         在 beginTransaction() 和 commit() 中间可以有多个操作，它们会当做一个事务提交
 *     add()/replace()/remove() - “添加/替换/删除”操作，会走 fragment 的生命周期
 *     findFragmentById()/findFragmentByTag() - 通过 id 或 tag 获取 fragment 对象
 *     show/hide() - “显示/隐藏”操作，类似 setVisibility() 操作，所以不会走 fragment 的生命周期
 *
 *     addToBackStack() - 将 fragment 加入返回栈（类似 activity 堆栈），按返回键时会先恢复 fragment 返回栈中的对象
 *         加入 Fragment 返回堆栈，然后再离开的话则会走到 onDestroyView()，恢复时会从 onCreateView() 开始走
 *     getBackStackEntryCount() - fragment 返回栈中的对象的数量
 *     popBackStack() - 移出 fragment 返回栈的栈顶对象
 *     popBackStack(String tag,int flags)
 *         tag == null, flags == 0 - 移出返回栈的栈顶对象
 *         tag == null, flags == 1 - 移出返回栈的全部对象
 *         tag == 有值, flags == 0 - 移出返回栈中指定 tag 的 fragment 之上的全部 fragment
 *         tag == 有值, flags == 1 - 移出返回栈中指定 tag 的 fragment 本身，和其之上的全部 fragment
 *
 *
 *
 * 举个例子：
 * 1、点击 add a fragment
 * Fragment2_1: onAttach
 * Fragment2_1: onCreate
 * Fragment2_1: onCreateView
 * Fragment2_1: onActivityCreated
 * Fragment2_1: onStart
 * Fragment2_1: onResume
 *
 * 2、点击 replace the fragment
 * Fragment2_2: onAttach
 * Fragment2_2: onCreate
 * Fragment2_1: onPause
 * Fragment2_1: onStop
 * Fragment2_1: onDestroyView
 * Fragment2_1: onDestroy
 * Fragment2_1: onDetach
 * Fragment2_2: onCreateView
 * Fragment2_2: onActivityCreated
 * Fragment2_2: onStart
 * Fragment2_2: onResume
 *
 * 3、点击 remove the fragment
 * Fragment2_2: onPause
 * Fragment2_2: onStop
 * Fragment2_2: onDestroyView
 * Fragment2_2: onDestroy
 * Fragment2_2: onDetach
 *
 * 4、点击 add a fragment with addToBackStack()
 * Fragment2_1: onAttach
 * Fragment2_1: onCreate
 * Fragment2_1: onCreateView
 * Fragment2_1: onActivityCreated
 * Fragment2_1: onStart
 * Fragment2_1: onResume
 *
 * 5、点击 replace the fragment with addToBackStack()
 * Fragment2_2: onAttach
 * Fragment2_2: onCreate
 * Fragment2_1: onPause
 * Fragment2_1: onStop
 * Fragment2_1: onDestroyView
 * Fragment2_2: onCreateView
 * Fragment2_2: onActivityCreated
 * Fragment2_2: onStart
 * Fragment2_2: onResume
 *
 * 6、点击 popBackStack() 按钮或者按返回键
 * Fragment2_2: onPause
 * Fragment2_2: onStop
 * Fragment2_2: onDestroyView
 * Fragment2_2: onDestroy
 * Fragment2_2: onDetach
 * Fragment2_1: onCreateView
 * Fragment2_1: onActivityCreated
 * Fragment2_1: onStart
 * Fragment2_1: onResume
 *
 * 7、点击 popBackStack() 按钮或者按返回键
 * Fragment2_1: onPause
 * Fragment2_1: onStop
 * Fragment2_1: onDestroyView
 * Fragment2_1: onDestroy
 * Fragment2_1: onDetach
 */

package com.webabcd.androiddemo.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class FragmentDemo2 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_fragmentdemo2);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);

        sample();
    }

    private void sample() {
        // add a fragment
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            // 在指定的 container 中添加指定的 fragment 对象，并指定其 tag
                            // 注：
                            // 1、在 beginTransaction() 和 commit() 中间可以有多个操作，它们会当做一个事务提交
                            // 2、添加 fragment 时允许 tag 重复，之后通过 findFragmentByTag() 获取到的是最后一个相同 tag 的 fragment（建议 tag 不要重复）
                            // 3、commit() 是异步的，同步的是 commitNow()
                            .add(R.id.container, new Fragment2_1(), "myTag")
                            .commit();
                }
            }
        });

        // replace the fragment
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment != null) {
                    fragmentManager
                            .beginTransaction()
                            // 在指定的 container 中，替换指定的 fragment 对象
                            .replace(R.id.container, new Fragment2_2(), "myTag")
                            .commit();
                }
            }
        });

        // remove the fragment
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment != null) {
                    fragmentManager
                            .beginTransaction()
                            // 删除指定的 fragment 对象
                            .remove(fragment)
                            .commit();
                }
            }
        });

        // show/hide the fragment
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag");
                if (fragment != null) {
                    if (fragment.isVisible()) {
                        fragmentManager
                                .beginTransaction()
                                // 隐藏指定的 fragment 对象
                                .hide(fragment)
                                .commit();
                    } else {
                        fragmentManager
                                .beginTransaction()
                                // 显示指定的 fragment 对象
                                .show(fragment)
                                .commit();
                    }
                }
            }
        });

        // add a fragment with addToBackStack()
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag_BackStack");
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            // 在指定的 container 中添加指定的 fragment 对象，并将其压入 fragment 返回栈
                            .add(R.id.container, new Fragment2_1(), "myTag_BackStack")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        // replace the fragment with addToBackStack()
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("myTag_BackStack");
                if (fragment != null) {
                    fragmentManager
                            .beginTransaction()
                            // 在指定的 container 中替换指定的 fragment 对象
                            .replace(R.id.container, new Fragment2_2(), "myTag_BackStack")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        // popBackStack()
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                /**
                 * popBackStack() - 移出 fragment 返回栈的栈顶对象
                 * popBackStack(String tag,int flags)
                 *     tag == null, flags == 0 - 移出返回栈的栈顶对象
                 *     tag == null, flags == 1 - 移出返回栈的全部对象
                 *     tag == 有值, flags == 0 - 移出返回栈中指定 tag 的 fragment 之上的全部 fragment
                 *     tag == 有值, flags == 1 - 移出返回栈中指定 tag 的 fragment 本身，和其之上的全部 fragment
                 */
                fragmentManager.popBackStack(); // popBackStack() 是异步的，同步的是 popBackStackImmediate()

                Toast.makeText(FragmentDemo2.this, String.format("getBackStackEntryCount: %d", fragmentManager.getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
