/**
 * ProgressDialog - 进度对话框
 *
 * 注：ProgressDialog 继承自 AlertDialog
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ProgressDialogDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_progressdialogdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ProgressDialog.show() - 显示一个简单的进度对话框（使用默认的 ProgressDialog.STYLE_SPINNER 样式，即圆圈进度条）
                //     第 1 个参数：context
                //     第 2 个参数：title
                //     第 3 个参数：message
                //     第 4 个参数：indeterminate - 进度是否是不确定的
                //     第 5 个参数：cancelable - 点击空白处是否自动隐藏对话框
                //     第 6 个参数：cancelListener - 对话框因用户点击了空白处而消失时触发的事件（调用 dialog.dismiss() 时不会触发此事件）
                ProgressDialog progressDialog = ProgressDialog.show(ProgressDialogDemo1.this, "标题", "内容", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogDemo1.this, "消失了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(ProgressDialogDemo1.this);
                progressDialog.setTitle("标题");
                progressDialog.setMessage("内容");
                progressDialog.setCancelable(true);

                // setProgressStyle() - 设置进度条的样式
                //     ProgressDialog.STYLE_SPINNER - 圆圈进度条（默认值）
                //     ProgressDialog.STYLE_HORIZONTAL - 水平进度条
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // setIndeterminate() - 进度是否是不确定的
                progressDialog.setIndeterminate(true);
                // setButton() - 添加按钮以及指定其行为（可以设置 3 种按钮，分别是 DialogInterface.BUTTON_POSITIVE, DialogInterface.BUTTON_NEGATIVE, DialogInterface.BUTTON_NEUTRAL）
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐藏对话框
                        dialog.dismiss();
                    }
                });
                // 对话框因用户点击了空白处而消失时触发的事件（调用 dialog.dismiss() 时不会触发此事件）
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogDemo1.this, "消失了", Toast.LENGTH_SHORT).show();
                    }
                });

                // 显示对话框
                progressDialog.show();
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(ProgressDialogDemo1.this);
                mProgressDialog.setTitle("标题");
                mProgressDialog.setMessage("内容");
                mProgressDialog.setCancelable(false);

                // setProgressStyle() - 设置进度条的样式
                //     ProgressDialog.STYLE_SPINNER - 圆圈进度条（默认值）
                //     ProgressDialog.STYLE_HORIZONTAL - 水平进度条
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // setIndeterminate() - 进度是否是不确定的
                mProgressDialog.setIndeterminate(false);
                // setMax() - 指定进度的最大值（最小值为 0）
                mProgressDialog.setMax(100);

                // 显示对话框
                mProgressDialog.show();

                // 用于模拟长时任务以及更新进度
                MyTask myTask = new MyTask();
                myTask.execute();
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // 模拟长时任务，以及更新进度
            for (int i = 0; i <= 100; i++) {
                publishProgress(i);
                SystemClock.sleep(10);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
            // 更新进度条的进度
            mProgressDialog.setProgress(progresses[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 隐藏进度条
            mProgressDialog.dismiss();
        }
    }
}
