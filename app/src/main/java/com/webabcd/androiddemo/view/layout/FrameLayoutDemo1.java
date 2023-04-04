/**
 * FrameLayout - 叠加布局控件
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class FrameLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_framelayoutdemo1);

        // 演示如何在 java 中控制 FrameLayout 布局，仅代码演示，没有对应的显示效果
        sample1();

        // 用于演示如何在 java 中设置 gravity 和 layout_gravity
        sample2();
    }

    private void sample1() {
        FrameLayout frameLayout = new FrameLayout(this);
        // 对应 xml 中的 foreground
        frameLayout.setForeground(this.getResources().getDrawable(R.drawable.img_sample_son));
        // 对应 xml 中的 foregroundGravity
        frameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
    }

    private void sample2() {
        TextView textView1 = findViewById(R.id.textView1);

        // 在 java 中设置 gravity
        textView1.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

        // 在 java 中设置 layout_gravity
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(textView1.getLayoutParams());
        fl.gravity = Gravity.LEFT | Gravity.BOTTOM;
        textView1.setLayoutParams(fl);
    }
}
