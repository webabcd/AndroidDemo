/**
 * TextView - 文本显示控件
 *
 * 演示 TextView 的常用行为
 */

package com.webabcd.androiddemo.view.text;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextViewDemo6 extends AppCompatActivity {

    private TextView _textView1;
    private TextView _textView2;
    private TextView _textView3;
    private TextView _textView4;
    private TextView _textView5;

    private Button _button1;
    private Button _button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo6);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _textView3 = (TextView) findViewById(R.id.textView3);
        _textView4 = (TextView) findViewById(R.id.textView4);
        _textView5 = (TextView) findViewById(R.id.textView5);

        _button1 = (Button) findViewById(R.id.button1);
        _button4 = (Button) findViewById(R.id.button4);

        // 文本滚动
        sample1();
        // 可点击并跳转（通过 html 的超链接方式）
        sample2();
        // 可点击并跳转（通过监听 TextView 的点击事件的方式）
        sample3();
        // 监听 TextView 的文本变化事件
        sample4();
        // 文本的选中
        sample5();
    }

    private void sample1() {
        // 当文本显示不下时，允许滚动
        _textView1.setMovementMethod(ScrollingMovementMethod.getInstance());

        // 滚动到底部（由于滚动到底部需要计算文本内容的高度和文本框的高度，所以需要等 TextView 绘制完成，所以这里在 post() 中执行滚动到底部的代码）
        _textView1.post(new Runnable() {
            @Override
            public void run() {
                scrollToBottom();
            }
        });

        _textView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(TextViewDemo6.this, "beforeTextChanged", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(TextViewDemo6.this, "onTextChanged", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(TextViewDemo6.this, "afterTextChanged", Toast.LENGTH_LONG).show();
            }
        });
        // _textView1.removeTextChangedListener();

        // 追加文本并滚动到底部
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 追加文本
                _textView1.append("\n");
                _textView1.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                // 滚动到底部
                scrollToBottom();
            }
        });
    }

    // 将 TextView 中的文本滚动到底部
    private void scrollToBottom() {
        int lineCount = _textView1.getLineCount(); // 行数
        int lineHeight = _textView1.getLineHeight(); // 行高
        int offset = lineCount * lineHeight; // 文本内容的高度

        int textViewHeight = _textView1.getHeight(); // 文本框的高度
        if (offset > textViewHeight) { // 如果文本内容的高度大于文本框的高度
            _textView1.scrollTo(0, offset - textViewHeight); // 滚动到底部（滚动距离为：文本内容的高度 - 文本框的高度）
        }
    }

    private void sample2() {
        String html = "<a href='http://webabcd.cnblogs.com'>webabcd blog</a>";

        // 让 TextView 解析并显示指定的 html
        _textView2.setText(Html.fromHtml(html));
        // 设置连接的颜色
        _textView2.setLinkTextColor(Color.parseColor("#FF0000"));
        // 允许点击 html 中的链接并跳转
        _textView2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void sample3() {
        String text = "click me";
        _textView3.setText(text);

        // 指定 TextView 是可点击的（其实这里不指定也是可以的，因为 setOnClickListener() 会自动将其设置为可点击）
        // _textView3.setClickable(true);

        // 处理 TextView 的点击事件
        _textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(TextViewDemo6.this, "被点击了", Toast.LENGTH_LONG).show();

                // 跳转到指定的 url
                Uri uri = Uri.parse("http://webabcd.cnblogs.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // 注：如果需要指定 TextView 中的指定部分的文本可点击，请参见 TextViewDemo5 中的关于 ClickableSpan 的相关示例
    }

    private void sample4() {
        // 添加文本变化事件的监听
        _textView4.addTextChangedListener(_textWatcher);
        // 移除文本变化事件的监听
        // _textView4.removeTextChangedListener(_textWatcher);

        // 追加文本
        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _textView4.append(new SimpleDateFormat("HH:mm:ss ").format(new Date()));
            }
        });
    }
    private TextWatcher _textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Toast.makeText(TextViewDemo6.this, "beforeTextChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Toast.makeText(TextViewDemo6.this, "onTextChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void afterTextChanged(Editable s) {
            Toast.makeText(TextViewDemo6.this, "afterTextChanged", Toast.LENGTH_SHORT).show();
        }
    };

    private void sample5() {
        // 文本是否可以被选中
        _textView5.setTextIsSelectable(true);
        // 获取焦点后是否自动选中全部文本
        _textView5.setSelectAllOnFocus(false);
        // 选中文本的背景色
        _textView5.setHighlightColor(getResources().getColor(R.color.orange));
    }
}
