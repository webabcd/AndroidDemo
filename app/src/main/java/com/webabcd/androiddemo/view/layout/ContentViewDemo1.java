/**
 * 演示 setContentView() 和 addContentView() 的用法
 */

package com.webabcd.androiddemo.view.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.webabcd.androiddemo.R;

public class ContentViewDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过指定资源文件 id 的方式设置当前 activity 的布局（这就是最常见的方式，就不说了）
        // setContentView(int layoutResID);

        // 通过指定 view 的方式设置当前 activity 的布局
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.GREEN);
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(linearLayout, params1);

        // 通过指定 view 的方式为当前的 activity 添加布局
        Button button = new Button(this);
        button.setText("webabcd");
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(button, params2);

        /*
         * 运行本例之后，你打开 Layout Inspector 会看到如下文档结构（LinearLayout 和 Button 在文档结构上是平级的）
         * ContentFrameLayout - 这个是 activity 的父容器
         *   LinearLayout - 这个是你通过 setContentView(linearLayout, params1); 设置的布局
         *   Button - 这个是你通过 addContentView(button, params2); 添加的布局
         */
    }
}