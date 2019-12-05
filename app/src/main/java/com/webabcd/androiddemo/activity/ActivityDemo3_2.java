/**
 * Activity 之间的跳转和数据传递（本例用于演示子 activity）
 *
 * 通过 intent 打开其他 activity，通过 bundle 传递数据（bundle 的大小有限制，大概是不到 1 MB）
 * 通过 finish() 结束当前的 activity，如果需要返回结果则在 finish() 之前调用 setResult()
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ActivityDemo3_2 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo3_2);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // 获取父 activity 传递过来的数据，此为简便写法，实际上是通过 bundle 对象来接收数据的（参见后面的注释部分）
        Intent intent = this.getIntent();
        String p1 = intent.getStringExtra("param1");
        int p2 = intent.getIntExtra("param2", 0);
        /*
        // 获取 intent 的 bundle 对象，用于接收数据（与上面的两行代码所做的工作是一样的）
        Bundle bundle = intent.getExtras();
        String p1 = bundle.getString("param1");
        String p2 = bundle.getInt("param2", 0);
        */
        mTextView1.setText(String.format("接收到的数据 param1:%s, param2:%d", p1, p2));

        // 关闭，不返回结果
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // isFinishing() - 判断当前 activity 是否在结束中
                // finish() - 结束当前 activity
                finish();
            }
        });

        // 关闭，并返回数据
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("param1", "wanglei");
                intent.putExtra("param2", 999);

                // 返回数据给 activity
                //     第 1 个参数 - 返回的结果代码
                //     第 2 个参数 - 返回的结果数据
                setResult(456, intent);

                // isFinishing() - 判断当前 activity 是否在结束中
                // finish() - 结束当前 activity
                finish();
            }
        });
    }
}
