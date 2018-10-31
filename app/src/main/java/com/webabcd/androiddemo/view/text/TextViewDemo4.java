/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的 html 支持
 */

package com.webabcd.androiddemo.view.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.HelloTagHandler;
import com.webabcd.androiddemo.utils.MyFontTagHandler;
import com.webabcd.androiddemo.utils.URLImageGetter;

public class TextViewDemo4 extends AppCompatActivity {

    private TextView _textView1;
    private TextView _textView2;
    private TextView _textView3;
    private TextView _textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo4);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _textView3 = (TextView) findViewById(R.id.textView3);
        _textView4 = (TextView) findViewById(R.id.textView4);

        sample1();
        sample2();
        sample3();
        sample4();
    }

    // TextView 解析并显示 html（TextView 仅支持部分 html 标签）
    private void sample1() {
        String html = "";
        // 支持 h1, h2, h3, h4, h5, h6
        html += "<h6>webabcd</h6>";
        // 支持 p, a
        html += "<p><a href='http://webabcd.cnblogs.com'>webabcd blog</a></p>";
        // 支持 b, i, u, big, small, strong, em
        html += "<b>bbb</b><i>iii</i><u>uuu</u><big>big</big><small>small</small><strong>strong</strong><em>em</em><br />";
        // 支持 sup（上标）, sub（下标）
        html += "hello<sup>sup</sup><sub>sub</sub><br />";
        // 支持 font（属性仅支持 color 和 face）
        html += "<font color='#ff0000'>font color</font>";
        // 支持 blockquote
        html += "<blockquote>blockquote</blockquote>";

        // 让 TextView 解析并显示指定的 html
        _textView1.setText(Html.fromHtml(html));
        // 允许点击 html 中的链接并跳转
        _textView1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // TextView 显示 html 中的 img（img 的 src 来自一个 url）
    private void sample2() {
        String html = "<img src='https://www.baidu.com/img/bd_logo.png' />"; // 一个 http 图片
        html += "<img src='" + R.drawable.pic_sample_son + "' />"; // 一个 drawable 图片

        // 在 setText() 中指定一个自定义 ImageGetter 去解析并显示 img 标签
        URLImageGetter urlImageGetter = new URLImageGetter(_textView2, this);
        _textView2.setText(Html.fromHtml(html, urlImageGetter, null));
    }

    // TextView 显示自定义 html 标签
    private void sample3() {
        String html = "<hello>webabcd</hello>";

        // 在 setText() 中指定一个自定义 TagHandler 去解析并显示自定义标签（本例是 <hello /> 标签）
        HelloTagHandler helloTagHandler = new HelloTagHandler();
        _textView3.setText(Html.fromHtml(html, null, helloTagHandler));
    }

    // TextView 显示自定义 html 标签（处理标签属性）
    private void sample4() {
        // TextView 是不支 font 的 size 的，本例用于演示如何在自定义标签中处理 size 属性
        String html = "<myfont size='32px'>webabcd</myfont>";

        // 在 setText() 中指定一个自定义 TagHandler 去解析并显示自定义标签（本例是 <myfont /> 标签）
        MyFontTagHandler myFontTagHandler = new MyFontTagHandler();
        _textView4.setText(Html.fromHtml(html, null, myFontTagHandler));
    }
}
