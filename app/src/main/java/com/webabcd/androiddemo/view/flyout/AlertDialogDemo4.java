/**
 * AlertDialog 样式
 *
 * 本例演示如何通过 java 和 xml theme 修改 AlertDialog 的样式
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.lang.reflect.Field;

public class AlertDialogDemo4 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_alertdialogdemo4);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // 修改 AlertDialog 的样式（通过 java 修改）
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo4.this);
                AlertDialog alert = builder
                        .setIcon(R.drawable.img_sample_son)
                        .setTitle("title")
                        .setMessage("message1\nmessage2")
                        .setPositiveButton("确定", null)
                        .create();
                alert.show();

                // 设置 AlertDialog 的背景颜色
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                // 设置 AlertDialog 的 POSITIVE 按钮的文字颜色
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.GREEN);

                // 通过反射修改 AlertDialog 的样式
                try {
                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(alert);

                    // 设置 AlertDialog 的内容颜色
                    Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                    mMessage.setAccessible(true);
                    TextView mMessageView = (TextView) mMessage.get(mAlertController);
                    mMessageView.setTextColor(Color.BLUE);

                    // 设置 AlertDialog 的标题颜色
                    Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                    mTitle.setAccessible(true);
                    TextView mTitleView = (TextView) mTitle.get(mAlertController);
                    mTitleView.setTextColor(Color.YELLOW);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 修改 AlertDialog 的样式（通过 xml theme 修改）
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 第 2 个参数用于指定 AlertDialog 的样式（参见 values/styles.xml 中的 MyTheme_MyAlertDialogStyle）
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo4.this, R.style.MyTheme_MyAlertDialogStyle);
                AlertDialog alert = builder
                        .setIcon(R.drawable.img_sample_son)
                        .setTitle("title")
                        .setMessage("message1\nmessage2")
                        .setPositiveButton("确定", null)
                        .create();
                alert.show();
            }
        });
    }
}
