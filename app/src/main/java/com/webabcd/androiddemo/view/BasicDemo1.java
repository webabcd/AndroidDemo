package com.webabcd.androiddemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class BasicDemo1 extends AppCompatActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_basic_demo1);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setTranslationX(200);
                button.setTranslationY(100);

                button.setLeft(20);
                button.setTop(10);

                showMessage();
            }
        });

        showMessage();

    }

    private void showMessage()
    {
        // 怎么java中创建view并添加到布局中

        // @string @color @style

        textView.setText(String.format("left:%d, top:%d, right:%d, bottom:%d, x:%f, y:%f, translationX:%f, translationY:%f",
                button.getLeft(), button.getTop(), button.getRight(), button.getBottom(), button.getX(), button.getY(), button.getTranslationX(), button.getTranslationY()));
    }
}
