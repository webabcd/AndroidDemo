/**
 * Activity 的生命周期
 *
 * 详见 @drawable/img_activity_lifecycle 生命周期图，大概说明一下，如下
 * 1、新创建的 onCreate() -> onStart() -> onResume()
 * 2、失去焦点但是可见 onPause()，从此状态恢复时 onResume()
 * 3、失去焦点且不可见 onPause() -> onStop()，从此状态恢复时 onStart() -> onResume()
 * 4、彻底被销毁 onPause() -> onStop() -> onDestroy()，从此状态恢复就是重新创建 onCreate() -> onStart() -> onResume()
 *
 *
 * 举个例子，打开 ActivityDemo1，然后点击按钮跳转到 ActivityDemo1_2，再点击按钮关闭 ActivityDemo1_2，则会显示 ActivityDemo1，此场景相关的生命周期日志如下
 * ActivityDemo1: onCreate
 * ActivityDemo1: onStart
 * ActivityDemo1: onResume
 * ActivityDemo1: onPause
 * ActivityDemo1_2: onCreate
 * ActivityDemo1_2: onStart
 * ActivityDemo1_2: onResume
 * ActivityDemo1: onStop
 * ActivityDemo1_2: onPause
 * ActivityDemo1: onStart
 * ActivityDemo1: onResume
 * ActivityDemo1_2: onStop
 * ActivityDemo1_2: onDestroy
 *
 *
 * 注：关于如何通过 onSaveInstanceState() 和 onRestoreInstanceState() 来保存状态，请参见 /activity/ActivityDemo2.java
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class ActivityDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "ActivityDemo1";

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo1);

        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo1.this, ActivityDemo1_2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");

        super.onDestroy();
    }
}
