/**
 * 主题简介，继承主题并重写其中的一些样式，指定主题
 *
 *
 * 开发环境查看 sdk 中的主题资源，其路径类似如下：
 *   C:\Android\sdk\platforms\android-28\data\res\values\themes.xml
 * 其中常用的主题说明如下（一般不会用到这些主题，因为通常习惯用 v7 包下的主题）：
 *   @android:style/Theme.Ligh - 白色背景
 *   @android:style/Theme.Light.NoTitleBar - 白色背景，无标题栏
 *   @android:style/Theme.Light.NoTitleBar.Fullscreen - 白色背景，无标题栏，全屏
 *
 *
 * v7 包中的主题，在 build.gradle 中通过类似这样 implementation 'com.android.support:appcompat-v7:27.1.1' 引用 v7 包
 * 然后再类似这样 Project -> External Libraries -> Gradle:com.android.support:appcompat-v7:27.1.1@arr -> res/values/values.xml 的路径下找到主题的定义
 * 其中常用的主题说明如下：
 *   Theme.AppCompat.Light - 白色背景，浅色背景的 ActionBar
 *   Theme.AppCompat.Light.DarkActionBar - 白色背景，深色背景的 ActionBar
 *   Theme.AppCompat.Light.NoActionBar - 白色背景，无 ActionBar
 *   Theme.AppCompat.Light.Dialog - 白色背景，类似对话框的样式去呈现 activity
 *
 * 注：
 * v7 包用于兼容老系统，但它不是只为了兼容 android 2.1 以下的操作系统
 * 实际上 google 会不断更新 v7 包的，比如本例中引用的 com.android.support:appcompat-v7:27.1.1，其表示兼容到 api 27
 */

package com.webabcd.androiddemo.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.webabcd.androiddemo.R;

public class ThemeDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定主题（注：在 java 中指定主题需要在 setContentView() 之前指定才会生效）
        // 也可以在 AndroidManifest.xml 中针对 application 或 activity 指定主题
        // ThemeDemo1Theme 主题的定义参见 res/values/styles.xml（其继承了 Theme.AppCompat.Light.DarkActionBar 主题，并重写了其中的一些样式）
        setTheme(R.style.ThemeDemo1Theme);

        // 在 java 中指定 application 级别的主题
        // getApplication().setTheme(R.style.ThemeDemo1Theme);

        setContentView(R.layout.activity_ui_themedemo1);
    }
}
