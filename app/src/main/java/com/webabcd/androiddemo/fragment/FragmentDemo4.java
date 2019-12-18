/**
 * Fragment 动画
 *
 * 通过 setCustomAnimations() 来指定 fragment 的切换动画
 *     第 1 个参数：新增的 fragment 的动画
 *     第 2 个参数：移除的 fragment 的动画
 *     第 3 个参数：popBackStack 时，显示的 fragment 的动画
 *     第 4 个参数：popBackStack 时，移除的 fragment 的动画
 */

package com.webabcd.androiddemo.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class FragmentDemo4 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_fragmentdemo4);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

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
                            .setCustomAnimations(R.anim.activity_push_left_in, R.anim.activity_push_left_out, R.anim.activity_zoom_enter, R.anim.activity_zoom_exit)
                            .add(R.id.container, new Fragment4_1(), "myTag")
                            .addToBackStack(null)
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
                            .setCustomAnimations(R.anim.activity_push_left_in, R.anim.activity_push_left_out, R.anim.activity_zoom_enter, R.anim.activity_zoom_exit)
                            .replace(R.id.container, new Fragment4_1(), "myTag")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        // popBackStack()
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });
    }
}
