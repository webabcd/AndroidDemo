/**
 * 演示 view 边距相关，隐藏相关
 */

package com.webabcd.androiddemo.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ViewDemo2 extends AppCompatActivity {

    private TextView _textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_viewdemo2);

        _textView2 = findViewById(R.id.textView2);

        sample();
    }

    private void sample() {
        // 设置 margin（left, top, right, bottom），单位是 px
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)_textView2.getLayoutParams();
        layoutParams.setMargins(20, 20,20, 20);
        _textView2.setLayoutParams(layoutParams);

        // 设置 padding（left, top, right, bottom），单位是 px
        _textView2.setPadding(20, 20, 20, 20);
    }
}
