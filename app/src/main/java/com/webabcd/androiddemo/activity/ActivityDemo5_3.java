/**
 * Activity 堆栈（请参见 activity/ActivityDemo5 中的说明）
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Date;

public class ActivityDemo5_3 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo5_3);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mTextView1.append("\n");
        mTextView1.append("onNewIntent");
    }

    private void sample() {
        mTextView1.setText(Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mTextView1.append("\n");
        mTextView1.append("taskId: " + this.getTaskId()); // 获取当前 activity 对象所在的 task（堆栈）的 id

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5_3.this, ActivityDemo5.class);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDemo5_3.this, ActivityDemo5.class));
                finish();
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5_3.this, ActivityDemo5.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}
