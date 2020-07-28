/**
 * 主题简介，继承主题并重写其中的一些样式，指定主题
 *
 *
 * 开发环境查看 sdk 中的主题资源，其路径类似如下：
 *   C:\Android\sdk\platforms\android-28\data\res\values\themes.xml
 * 其中常用的主题说明如下（一般不会用到这些主题，因为通常习惯用 androidx 包下的主题）：
 *   @android:style/Theme.Ligh - 白色背景
 *   @android:style/Theme.Light.NoTitleBar - 白色背景，无标题栏
 *   @android:style/Theme.Light.NoTitleBar.Fullscreen - 白色背景，无标题栏，全屏
 *
 *
 * androidx 包中的主题，在 build.gradle 中通过类似这样 implementation 'androidx.appcompat:appcompat:1.1.0' 引用 androidx 包
 * 然后再类似这样 Project -> External Libraries -> Gradle:androidx.appcompat:appcompat:1.1.0@arr -> res/values/values.xml 的路径下找到主题的定义
 * 其中常用的主题说明如下：
 *   Theme.AppCompat.Light - 白色背景，浅色背景的 ActionBar
 *   Theme.AppCompat.Light.DarkActionBar - 白色背景，深色背景的 ActionBar
 *   Theme.AppCompat.Light.NoActionBar - 白色背景，无 ActionBar
 *   Theme.AppCompat.Light.Dialog - 白色背景，类似对话框的样式去呈现 activity
 *
 * 注：
 * v4 包具有 android 的新 api 并向下兼容到 android 1.6
 * v7 包具有 android 的新 api 并向下兼容到 android 2.1
 * 但是 android 1.6 和 2.1 都淘汰了，继续按这种方式命名不合适，所以干脆就把扩展库的名称定为 androidx
 * 也就是说 android.* 下的 api 都是随着 android 操作系统发布的，而 androidx.* 下的 api 都是随着扩展库发布的（其不依赖于操作系统的版本）
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
