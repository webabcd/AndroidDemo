/**
 * Activity 之间的跳转和数据传递（本例用于演示父 activity）
 *
 * 通过 intent 打开其他 activity，通过 bundle 传递数据（bundle 的大小有限制，大概是不到 1 MB）
 * startActivity() 用于打开其他 activity
 * startActivityForResult() 用于打开其他 activity 并通过当前 activity 的 @Override onActivityResult() 接收返回结果
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ActivityDemo3 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo3);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // 启动另一个 activity 并传递数据，不接收返回结果
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化 intent（需要指定当前上下文，以及需要跳转到的 activity）
                Intent intent = new Intent(ActivityDemo3.this, ActivityDemo3_2.class);
                // intent.setClass(ActivityDemo3.this, ActivityDemo3_2.class);

                // 设置需要传递的数据，此为简便写法，实际上是通过 bundle 对象来传递数据的（参见后面的注释部分）
                intent.putExtra("param1", "webabcd");
                intent.putExtra("param2", 100);

                /*
                // 构造 intent 的 bundle 对象，用于传递数据（与上面的两行代码所做的工作是一样的）
                Bundle bundle = new Bundle();
                bundle.putString("param1", "webabcd");
                bundle.putInt("param2", 100);
                intent.putExtras(bundle);
                */

                // startActivity() - 通过 intent 打开指定的 activity 并传递数据
                startActivity(intent);
            }
        });

        // 启动另一个 activity 并传递数据，并接收返回结果
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo3.this, ActivityDemo3_2.class);
                intent.putExtra("param1", "webabcd");
                intent.putExtra("param2", 100);

                // startActivityForResult() - 通过 intent 打开指定的 activity 并传递数据，让后通过当前 activity 的 @Override onActivityResult() 接收返回结果
                //     第二个参数是整型的，会通过 @Override onActivityResult() 传递回来，用于区分当前结果是通过哪个 startActivityForResult() 返回的
                startActivityForResult(intent, 123);
            }
        });
    }

    // 接收 startActivityForResult() 打开的 activity 返回的结果
    //     requestCode - 就是 startActivityForResult() 的第二个参数，用于区分当前结果是通过哪个 startActivityForResult() 返回的
    //     resultCode - 返回的结果代码
    //     data - 返回的结果数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTextView1.setText(String.format("requestCode:%d, resultCode:%d", requestCode, resultCode));

        if (data != null) {
            String p1 = data.getStringExtra("param1");
            int p2 = data.getIntExtra("param2", 0);

            mTextView1.append(String.format("\n接收到的返回数据 param1:%s, param2:%d", p1, p2));
        }
    }
}
