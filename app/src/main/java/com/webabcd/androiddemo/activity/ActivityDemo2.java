/**
 * Activity 的横屏和竖屏，以及横竖屏切换与状态保存
 *
 *
 * 关于横屏和竖屏
 * 1、在 activity 级别设置横屏或竖屏
 * 2、可以在 AndroidManifest.xml 的 activity 节点设置 screenOrientation 属性
 * 3、可以在 java 中通过 setRequestedOrientation() 方法动态修改横竖屏属性
 *    如果动态修改后，当前方向与修改后的方向不符，则系统会一步一步地结束当前 activity 的生命周期，然后再重新创建
 * 4、screenOrientation 和 setRequestedOrientation() 常用的值的说明如下：
 *    unspecified, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED - 默认值，由系统决定（根据你在系统设置中指定的“纵向/横向/自动”来决定）
 *    fullSensor, ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR - 根据传感器来决定 4 个方向
 *    landscape, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE - 强制横屏（左转）
 *    reverseLandscape, ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE - 强制横屏（右转）
 *    sensorLandscape, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE - 根据传感器决定是左横屏还是右横屏
 *    portrait, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT - 强制竖屏
 *    reversePortrait, ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT - 强制反向竖屏
 *    sensorPortrait, ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT - 根据传感器决定是竖屏还是反向竖屏
 *    behind, ActivityInfo.SCREEN_ORIENTATION_BEHIND - 上一个 activity 是什么方向，我就是什么方向
 *
 *
 * 关于横竖屏切换与状态保存
 * 1、横竖屏切换时会销毁并重新创建 Activity，即生命周期为 onPause() -> onStop() -> onDestroy() -> onCreate() -> onStart() -> onResume()
 * 2、横竖屏切换时的状态保存可以通过 @Override onSaveInstanceState(Bundle outState) 和 @Override onRestoreInstanceState(Bundle savedInstanceState) 实现
 *
 *
 * 关于 onSaveInstanceState() 和 onRestoreInstanceState() 的调用的常见场景总结一下：
 * 1、当因横竖屏切换或内存不足而导致需要销毁 activity 时会调用 onSaveInstanceState()，之后系统试图恢复 app 时会调用 onRestoreInstanceState()
 * 2、当用户按 home 键后，系统会调用此 activity 的 onSaveInstanceState()
 *    a）如果之后这个 activity 没被系统销毁，之后再回到此 app 时是不会调用 onRestoreInstanceState() 的
 *    b）如果之后这个 activity 被系统销毁了，之后系统试图恢复此 app 时会调用 onRestoreInstanceState()
 * 注：关于状态保存不见得非要用 onSaveInstanceState() 和 onRestoreInstanceState()，可以自己写自己的逻辑
 */

package com.webabcd.androiddemo.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Date;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class ActivityDemo2 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 动态修改横竖屏属性
        // 因为如果动态修改后，当前方向与修改后的方向不符，则系统会一步一步地结束当前 activity 的生命周期，然后再重新创建
        // 所以建议需要动态修改的时候再这样写，如果只是想初始化时一次性地指定 activity 的横竖屏属性，建议还是在 AndroidManifest.xml 中去设置
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // 获取当前的屏幕方向，然后根据不同的方向加载不同的布局（注：也可以通过资源文件夹的限定符 -land -port 来自动加载指定方向的资源）
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_activity_activitydemo2_landscape);
        }
        else if (getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_activity_activitydemo2_portrait);
        }

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);

        sample();
    }

    private void sample() {
        mTextView1.setText(Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mTextView2.setText(Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    // 当遇到用户不想离开 activity 却离开的情况时，比如横竖屏切换，内存不足、用户按 home 键等，此种情况系统会调用此方法
    // 当遇到用户确实想离开 activity 的情况时，比如 activity 之间的正常导航，此种情况系统是不会调用此方法的
    // 如果系统调用了这个方法，那么会在 onPause() 之后调用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 保存数据
        outState.putString("mTextView2_text", mTextView2.getText().toString());
    }

    // 调用了 onSaveInstanceState() 之后不一定会调用 onRestoreInstanceState()
    // 调用了 onSaveInstanceState() 之后，且 activity 被销毁了，然后再试图恢复时一般会调用 onRestoreInstanceState()
    // 如果系统调用了这个方法，那么会在 onStart() 之后调用
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 恢复数据
        String mTextView2_text = savedInstanceState.getString("mTextView2_text");
        mTextView2.setText(mTextView2_text);
    }
}
