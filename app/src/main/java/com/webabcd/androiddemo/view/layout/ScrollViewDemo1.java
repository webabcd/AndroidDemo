/**
 * ScrollView - 滚动容器
 */

package com.webabcd.androiddemo.view.layout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ScrollViewDemo1 extends AppCompatActivity {

    private ScrollView mScrollView1;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout_scrollviewdemo1);

        mScrollView1 = findViewById(R.id.scrollView1);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.append("item " + i);
            if (i < 999) {
                sb.append("\n");
            }
        }
        mTextView1.setText(sb.toString());

        // 将 ScrollView 滚到到底部
        // 如果通过程序让 ScrollView 中的内容发生变化（比如增加一些子控件，或者让原有子控件的内容变多），然后再通过程序同步滚动到底部，这样做可能不会成功，因为变化后的结果可能还没有测量出来
        // 所以这里用 Handler 来避免上述问题
        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                // 计算底部的位置
                int offset = mTextView1.getMeasuredHeight() - mScrollView1.getHeight();
                if (offset < 0) {
                    offset = 0;
                }

                /**
                 * scrollTo() - 滚动到指定位置（无动画效果）
                 * smoothScrollTo() - 滚动到指定位置（有动画效果）
                 * fullScroll() - 滚动到指定位置（有动画效果），并将焦点设置到指定位置的子控件
                 *     ScrollView.FOCUS_DOWN - 滚动到底部
                 *     ScrollView.FOCUS_UP - 滚动到顶部
                 *     ScrollView.FOCUS_LEFT - 滚动到左部
                 *     ScrollView.FOCUS_RIGHT - 滚动到右部
                 */

                mScrollView1.scrollTo(0, offset);
                // mScrollView1.smoothScrollTo(0, offset);
                // mScrollView1.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
