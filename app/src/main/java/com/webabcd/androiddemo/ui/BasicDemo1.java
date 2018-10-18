package com.webabcd.androiddemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class BasicDemo1 extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_basic_demo1);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        textView2.setText("你好 wanglei");
        textView2.setTextColor(Color.argb(0xff, 0x00, 0xff, 0x00));

        textView4.setText(getResources().getString(R.string.sample_hello2));
        textView4.setTextColor(ContextCompat.getColor(this,R.color.blue));


        // 如何在后台从资源文件中获取字符串（分为有Context和没有Context如何获取）
        // 插件的使用，findviewbyme，还有json转实体类那个
    }
}
