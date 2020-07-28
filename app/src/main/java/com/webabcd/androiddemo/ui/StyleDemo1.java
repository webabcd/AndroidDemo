/**
 * 样式简介，自定义样式，动态更换样式
 *
 *
 * 如需要在 java 中动态更换样式资源的话
 * 1、可以通过 setTextAppearance(Context context, @StyleRes int resId) 来指定样式资源（仅文本类的样式可以生效）
 * 2、可以通过动态更换主题来实现，参见 ui/ThemeDemo2
 *
 *
 * 开发环境查看 sdk 中的样式资源，其路径类似如下：
 *   C:\Android\sdk\platforms\android-28\data\res\values\styles.xml
 *
 * androidx 包中的样式，在 build.gradle 中通过类似这样 implementation 'androidx.appcompat:appcompat:1.1.0' 引用 androidx 包
 * 然后再类似这样 Project -> External Libraries -> Gradle:androidx.appcompat:appcompat:1.1.0@arr -> res/values/values.xml 的路径下找到样式的定义
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
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class StyleDemo1 extends AppCompatActivity {

    private Button mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_styledemo1);

        mButton5 = findViewById(R.id.button5);

        // 动态更换样式资源，仅文本类的样式可以生效（如果此方式无法满足需求的话，请通过动态更换主题来实现，参见 ui/ThemeDemo2）
        mButton5.setTextAppearance(this, R.style.StyleDemo1ButtonStyle);
    }
}
