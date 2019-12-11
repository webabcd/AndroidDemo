/**
 * 本例用于演示如何显示对话框样式的 activity
 *
 * 如果需要显示对话框样式的 activity，则需要在 AndroidManifest.xml 中设置 activity 节点的 theme 属性为 @style/Theme.AppCompat.Dialog
 * 注：经测试，如果是在 java 的 setTheme() 中设置主题的话，背景只能是黑色的，无法设置为透明的
 *
 * 对话框样式的 activity 是有标题栏的，可以通过自定义主题将其去掉，参见 res/values/styles.xml 中的“MyActivityDialog”
 */

package com.webabcd.androiddemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class ActivityDemo6_2 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo6_2);

        // 设置当前 activity 的宽和高
        android.view.WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int)(Helper.getScreenSize(this).x * 0.8);
        layoutParams.height = (int)(Helper.getScreenSize(this).y * 0.8);
        getWindow().setAttributes(layoutParams);

        // setFinishOnTouchOutside(boolean finish)
        //     true - 点击空白区域（非此 activity 区域）自动关闭此 activity，默认值
        //     false - 点击空白区域（非此 activity 区域）不会自动关闭此 activity
        setFinishOnTouchOutside(false);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
