/**
 * AlertDialog 大小、位置和动画
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class AlertDialogDemo3 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_alertdialogdemo3);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(AlertDialogDemo3.this).setMessage("内容").create();
                alertDialog.show();

                Window dialogWindow = alertDialog.getWindow();

                // 设置对话框的 Gravity 位置
                dialogWindow.setGravity(Gravity.CENTER);

                // 设置对话框的位置（这里的 x, y 位置是相对于 Gravity 的位置），大小和透明度
                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
                layoutParams.x = 100;
                layoutParams.y = 100;
                layoutParams.width = 300;
                layoutParams.height = 300;
                layoutParams.alpha = 0.3f;
                dialogWindow.setAttributes(layoutParams);

                // 设置对话框的显示和隐藏动画（参见 res/values/styles.xml 中的 MyAlertDialogAnimationStyle）
                dialogWindow.setWindowAnimations(R.style.MyAlertDialogAnimationStyle);
            }
        });
    }
}
