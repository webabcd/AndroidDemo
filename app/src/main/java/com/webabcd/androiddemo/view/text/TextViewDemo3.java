package com.webabcd.androiddemo.view.text;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class TextViewDemo3 extends AppCompatActivity {

    private TextView _textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo3);

        _textView2 = (TextView) findViewById(R.id.textView2);

        sample();
    }

    private void sample() {
        Drawable[] drawable = _textView2.getCompoundDrawables();
        // 数组下表0~3,依次是:左上右下
        drawable[1].setBounds(0, 0, 20, 20);
        _textView2.setCompoundDrawables(drawable[0], drawable[1], drawable[2],
                drawable[3]);
    }
}
