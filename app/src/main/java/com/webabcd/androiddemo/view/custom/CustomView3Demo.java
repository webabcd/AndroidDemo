/**
 * 演示如何自定义组合控件，自定义的组合控件参见 view/custom/CustomView3.java（实现了一个简易的登录框）
 */

package com.webabcd.androiddemo.view.custom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class CustomView3Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom_customview3demo);

        CustomView3 customView3 = findViewById(R.id.customView3);
        customView3.setOnLoginListener(new CustomView3.OnLoginListener() {
            @Override
            public void login(View v, String username, String password) {
                // 用户点击登录按钮后，可以拿到用户输入的用户名和密码
                Toast.makeText(CustomView3Demo.this, String.format("username:%s, password:%s", username, password), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
