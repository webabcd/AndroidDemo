/**
 * Toast
 *
 * 本例用于演示
 * 1、短时间显示的 toast（2秒）
 * 2、长时间显示的 toast（3.5秒）
 * 3、修改 toast 的位置和样式
 * 4、自定义 view 的 toast
 *
 * 注：
 * 1、toast 的显示时间就 2 种，Toast.LENGTH_SHORT 2 秒，Toast.LENGTH_LONG 3.5 秒
 * 2、显示 toast 的时候，如果之前的 toast 正在显示中，则会先隐藏掉之前的 toast
 */

package com.webabcd.androiddemo.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ToastDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_toastdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample1();
        sample2();
        sample3();
    }

    private void sample1() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 短时间显示的 toast（2秒），第 2 个参数除了可以指定字符串外也可以指定字符串的资源id
                Toast toast = Toast.makeText(ToastDemo1.this, "abc", Toast.LENGTH_SHORT);
                // 显示
                toast.show();
                // 隐藏
                // toast.cancel();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 长时间显示的 toast（3.5秒），第 2 个参数除了可以指定字符串外也可以指定字符串的资源id
                Toast toast = Toast.makeText(ToastDemo1.this, "xyz", Toast.LENGTH_LONG); // 3.5
                // 显示
                toast.show();
                // 隐藏
                // toast.cancel();
            }
        });
    }

    // 演示如何修改 toast 的位置和样式
    private void sample2() {
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(ToastDemo1.this, "aaaaaa", Toast.LENGTH_LONG);

                // 设置 toast 的显示位置
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL , 0, 0);
                // setMargin() 也用于指定显示位置，其两个参数的取值范围为 0 - 1
                // toast.setMargin(0.3f, 0.3f);

                // 取出系统 toast 的 layout，并指定其背景色，以及插入一张图片
                LinearLayout layout = (LinearLayout) toast.getView();
                layout.setBackgroundColor(Color.RED);
                ImageView image = new ImageView(ToastDemo1.this);
                image.setImageResource(R.drawable.img_sample_son);
                layout.addView(image, 0);

                // 取出系统 toast 的 TextView，并指定其颜色
                TextView v = layout.findViewById(android.R.id.message);
                v.setTextColor(Color.YELLOW);

                toast.show();
            }
        });
    }

    // 演示如何实现自定义 view 的 toast
    private void sample3() {
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 加载自定义 view 的 layout，并指定其显示的文字
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.activity_notification_toastdemo1_customtoast, (ViewGroup) findViewById(R.id.root));
                TextView message = (TextView) layout.findViewById(R.id.message);
                message.setText("bbbbbb");

                // 实例化 toast 对象，指定显示位置和显示时长
                Toast toast = new Toast(ToastDemo1.this);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL , 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);

                // 指定 toast 的自定义 view 并显示
                toast.setView(layout);
                toast.show();
            }
        });
    }
}