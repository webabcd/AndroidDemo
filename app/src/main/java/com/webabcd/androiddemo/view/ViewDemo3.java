package com.webabcd.androiddemo.view;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ViewDemo3 extends AppCompatActivity {

    private TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_viewdemo3);

        mTextView3 = findViewById(R.id.textView3);

        sample();
    }

    private void sample() {
        mTextView3.setTextColor(Color.argb(0xff, 0xff, 0x00, 0x00));
        mTextView3.setBackgroundColor(getResources().getColor(R.color.blue));
    }
}
