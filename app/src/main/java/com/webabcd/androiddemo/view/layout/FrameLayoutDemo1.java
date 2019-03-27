/**
 * FrameLayout - 帧布局控件
 */

package com.webabcd.androiddemo.view.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.webabcd.androiddemo.R;

public class FrameLayoutDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_framelayoutdemo1);

        sample();
    }

    private void sample() {
        FrameLayout frameLayout = new FrameLayout(this);
        // 对应 xml 中的 foreground
        frameLayout.setForeground(this.getResources().getDrawable(R.drawable.img_sample_son));
        // 对应 xml 中的 foregroundGravity
        frameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
    }
}
