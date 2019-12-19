/**
 * AlertDialog 自定义，即自定义 AlertDialog 显示的 view
 *
 * 通过 AlertDialog.Builder 的 setView() 来指定 AlertDialog 需要显示的 view
 *
 * 本例的自定义 view 的布局文件请参见：res/layout/customview_view_flyout_alertdialogdemo2.xml
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class AlertDialogDemo2 extends AppCompatActivity {

    private Button mButton1;

    private View mCustomView;
    private AlertDialog mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_alertdialogdemo2);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        // 从布局文件中加载 AlertDialog 需要显示的 view
        LayoutInflater inflater = AlertDialogDemo2.this.getLayoutInflater();
        mCustomView = inflater.inflate(R.layout.customview_view_flyout_alertdialogdemo2, null,false);

        AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogDemo2.this);
        // 指定 AlertDialog 需要显示的 view
        builder.setView(mCustomView);
        // 点击空白处是否自动隐藏对话框（默认值为 true）
        builder.setCancelable(false);
        // 创建 AlertDialog 对象
        mAlert = builder.create();

        // 弹出自定义对话框
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlert.show();
            }
        });



        // 设置自定义 view 中的显示内容
        ((TextView)mCustomView.findViewById(R.id.textViewTitle)).setText("标题");
        ((TextView)mCustomView.findViewById(R.id.textViewContent)).setText("内容");
        ((Button)mCustomView.findViewById(R.id.buttonClose)).setText("X");
        ((Button)mCustomView.findViewById(R.id.buttonConfirm)).setText("确认");
        ((Button)mCustomView.findViewById(R.id.buttonCancel)).setText("取消");

        // 自定义 view 中的关闭按钮的点击事件
        mCustomView.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlertDialogDemo2.this, "点击了关闭按钮", Toast.LENGTH_SHORT).show();
                mAlert.dismiss();
            }
        });

        // 自定义 view 中的确认按钮的点击事件
        mCustomView.findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlertDialogDemo2.this, "点击了确认按钮", Toast.LENGTH_SHORT).show();
                mAlert.dismiss();
            }
        });

        // 自定义 view 中的取消按钮的点击事件
        mCustomView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlertDialogDemo2.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                mAlert.dismiss();
            }
        });
    }
}
