/**
 * 布局 xml 基础
 */

package com.webabcd.androiddemo.resource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class XmlDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_xmldemo1);

        // 如果 xml 中的控件有重名，则通过 findViewById() 找到的是 xml 中最先出现的控件
        TextView xmldemo1_textView2 = findViewById(R.id.xmldemo1_textView2);
        xmldemo1_textView2.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
    }
}