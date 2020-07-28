/**
 * 国际化（多语言）
 * 本例会演示如何获取当前语言，如何引用当前语言的资源，如何引用指定语言的资源，如何动态切换语言
 *
 * 通过资源文件夹中的限定符来支持多语言，比如
 * values-zh 保存中文资源，values-en 保存英文资源，values 保存默认资源（都匹配不到时就会找这里）
 *
 * 注：资源文件夹可以同时指定多个限定符，类似 drawable-xxhdpi-v26-zh
 */

package com.webabcd.androiddemo.resource;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Locale;

public class LocalizationDemo1 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    // 用于保存当前用户选择的语言，本例仅演示用
    static private Locale mLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_localizationdemo1);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    // 默认会引用系统设置中的首选语言的资源，所以我们需要通过如下方式来指定当前 activity 的首选语言
    @Override
    protected void attachBaseContext(Context newBase) {
        if (mLocale == null) {
            super.attachBaseContext(newBase);
        } else {
            Context myContext = applyLanguage(newBase, mLocale);
            super.attachBaseContext(myContext);
        }
    }

    // 指定当前 activity 的首选语言
    private Context applyLanguage(Context context, Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // v24 或以上，需要将指定的语言绑定到 context，然后再 attachBaseContext()
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            Context myContext = context.createConfigurationContext(configuration);

            return myContext;
        } else { // v24 以下，直接将指定的语言绑定到 resource，不需要 attachBaseContext()
            Resources resource = context.getResources();
            Configuration configuration = resource.getConfiguration();
            configuration.locale = locale;
            DisplayMetrics dm = resource.getDisplayMetrics();
            resource.updateConfiguration(configuration, dm);

            return context;
        }
    }

    private void sample() {
        // 引用当前语言的资源
        mTextView1.setText("前语言的资源：" + getResources().getString(R.string.localization_demo));


        // 获取当前语言
        mTextView2.setText("当前语言：");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // v24 或以上的获取方式（在 v24 或以上系统中，用户可以在系统设置中指定多个首选语言）
            LocaleList localeList = getResources().getConfiguration().getLocales();
            for (int i = 0; i < localeList.size(); i++) {
                mTextView2.append(localeList.get(i).getLanguage());
                if (i + 1 < localeList.size()) {
                    mTextView2.append(",");
                }
            }
        } else { // v24 以下的获取方式（用户只能在系统设置中指定一个首选语言）
            Locale locale = getResources().getConfiguration().locale;
            mTextView2.append(locale.getLanguage());
        }


        // 引用指定语言的资源
        Context myContext = applyLanguage(this, Locale.SIMPLIFIED_CHINESE);
        mTextView3.setText("中文资源：" + myContext.getResources().getString(R.string.localization_demo));


        // 以下用于演示如何动态切换语言（参见 @Override attachBaseContext() 中的逻辑和说明）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocale = Locale.SIMPLIFIED_CHINESE;
                // 要想在当前 activity 中看到切换语言后的效果则需要通过 recreate() 重新加载 activity，以便在 @Override attachBaseContext() 中加载指定的语言资源
                // 调用 recreate() 后屏幕会闪一下，如果不满足需求的话，那在本页你就自行引用指定语言的资源然后再赋值给需要的控件即可
                LocalizationDemo1.this.recreate();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocale = Locale.ENGLISH;
                LocalizationDemo1.this.recreate();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocale = Locale.JAPANESE;
                LocalizationDemo1.this.recreate();
            }
        });
    }
}
