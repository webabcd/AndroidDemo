/**
 * Activity 的生命周期
 *
 * 详见 @drawable/img_activity_lifecycle 声明周期图，大概说明一下，如下
 * 1、新创建的 onCreate() -> onStart() -> onResume()
 * 2、失去焦点但是可见 onPause()，从此状态恢复时 onResume()
 * 3、失去焦点且不可见 onPause() -> onStop()，从此状态恢复时 onStart() -> onResume()
 * 4、彻底被销毁 onPause() -> onStop() -> onDestroy()，从此状态恢复就是重新创建 onCreate() -> onStart() -> onResume()
 *
 *
 * 注：关于如何通过 onSaveInstanceState() 和 onRestoreInstanceState() 来保存状态，请参见 /activity/ActivityDemo2.java
 */

package com.webabcd.androiddemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

public class ActivityDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "ActivityDemo1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo1);

        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(LOG_TAG, "onDestroy");
    }
}
