/**
 * 参见 view/custom/CustomView1.java
 * 通过一个自定义 View 来演示 measure, layout, draw
 */

package com.webabcd.androiddemo.view.custom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class CustomView1Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom_customview1demo);
    }
}
