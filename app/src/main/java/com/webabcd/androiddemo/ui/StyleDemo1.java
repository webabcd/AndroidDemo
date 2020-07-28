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
 * v7 包中的样式，在 build.gradle 中通过类似这样 implementation 'com.android.support:appcompat-v7:27.1.1' 引用 v7 包
 * 然后再类似这样 Project -> External Libraries -> Gradle:com.android.support:appcompat-v7:27.1.1@arr -> res/values/values.xml 的路径下找到样式的定义
 *
 * 注：
 * v7 包用于兼容老系统，但它不是只为了兼容 android 2.1 以下的操作系统
 * 实际上 google 会不断更新 v7 包的，比如本例中引用的 com.android.support:appcompat-v7:27.1.1，其表示兼容到 api 27
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
