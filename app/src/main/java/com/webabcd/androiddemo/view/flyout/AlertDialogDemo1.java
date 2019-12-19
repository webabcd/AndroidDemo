/**
 * AlertDialog 基础
 *
 * AlertDialog - 弹出对话框，有 4 种内置样式，分别是：普通对话框、普通列表对话框、单选框列表对话框和复选框列表对话框
 *     show() - 显示
 *     dismiss() - 隐藏
 * AlertDialog.Builder - 用于方便地创建 AlertDialog 对象（支持链式语法）
 *     setCancelable() - 点击空白处是否自动隐藏对话框（默认值为 true）
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class AlertDialogDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_alertdialogdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);

        sample();
    }

    private void sample() {
        // 普通对话框
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo1.this);
                AlertDialog alert = builder
                        // 图标
                        .setIcon(R.drawable.img_sample_son)
                        // 标题（字符串）
                        .setTitle("title")
                        // 标题（自定义 view）
                        // .setCustomTitle(View customTitleView)
                        // 内容（普通对话框）
                        .setMessage("message1\nmessage2")
                        // 点击空白处是否自动隐藏对话框（默认值为 true）
                        .setCancelable(true)
                        // 否定按钮
                        .setNegativeButton("否定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogDemo1.this, "negative（否定）", Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 确定按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogDemo1.this, "positive（确定）", Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 中立按钮
                        .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogDemo1.this, "neutral（中立）", Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 创建 AlertDialog 对象
                        .create();

                // 对话框因用户点击了空白处而消失时触发的事件（调用 alert.dismiss() 时不会触发此事件）
                alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(AlertDialogDemo1.this, "消失了", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

        // 普通列表对话框
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = getResources().getStringArray(R.array.arrayCountry);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo1.this);
                AlertDialog alert = builder
                        // 图标
                        .setIcon(R.drawable.img_sample_son)
                        // 标题（字符串）
                        .setTitle("title")
                        // 普通列表对话框（需要一个字符串数组用于显示数据）
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // 项被点击时
                                // which - 选中的项的索引
                                Toast.makeText(AlertDialogDemo1.this, "selected: " + items[which], Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 创建 AlertDialog 对象
                        .create();
                alert.show();
            }
        });

        // 单选框列表对话框
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = getResources().getStringArray(R.array.arrayCountry);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo1.this);
                AlertDialog alert = builder
                        // 图标
                        .setIcon(R.drawable.img_sample_son)
                        // 标题（字符串）
                        .setTitle("title")
                        // 单选框列表对话框（需要一个字符串数组用于显示数据，以及初始选中项的索引）
                        .setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // 项被点击时
                                // which - 选中的项的索引
                                Toast.makeText(AlertDialogDemo1.this, "selected: " + items[which], Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 创建 AlertDialog 对象
                        .create();
                alert.show();
            }
        });

        // 复选框列表对话框
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 需要显示的数组数据
                final String[] items = getResources().getStringArray(R.array.arrayCountry);
                // 显示的数组数据中每一项所对应的选中状态
                final boolean[] checkItems = new boolean[]{false, false, true};

                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo1.this);
                AlertDialog alert = builder
                        // 图标
                        .setIcon(R.drawable.img_sample_son)
                        // 标题（字符串）
                        .setTitle("title")
                        // 复选框列表对话框（需要一个字符串数组用于显示数据，以及一个布尔类型数组用于指定显示的数组数据中每一项所对应的选中状态）
                        .setMultiChoiceItems(items, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) { // 项被点击时
                                // which - 被点击的项的索引
                                // isChecked - 被点击的项的选中状态
                                checkItems[which] = isChecked;
                            }
                        })
                        // 确定按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String result = "";
                                for (int i = 0; i < checkItems.length; i++) {
                                    if (checkItems[i]) {
                                        result += items[i] + " ";
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "selected: " + result, Toast.LENGTH_SHORT).show();
                            }
                        })
                        // 创建 AlertDialog 对象
                        .create();
                alert.show();
            }
        });
    }
}
