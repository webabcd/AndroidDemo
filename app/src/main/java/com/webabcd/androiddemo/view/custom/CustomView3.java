/**
 * 演示如何自定义组合控件
 *
 * 布局文件在 res/layout/customview_view_custom_customview3.xml
 * 属性定义在 res/values/attrs.xml
 *
 *
 * 本组合控件实现了一个简易的登录框，其包括
 * 1、用户名文字，用户名输入框
 * 2、密码文字，密码输入框
 * 3、登录按钮
 */

package com.webabcd.androiddemo.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

// 继承 LinearLayout 类，对应的布局文件在 res/layout/customview_view_custom_customview3.xml
public class CustomView3 extends LinearLayout {

    private Button btnLogin; // 登录按钮
    private TextView lblUsername, lblPassword; // 用户名文字，用户名输入框
    private EditText txtUsername, txtPassword; // 密码文字，密码输入框

    private Context mContext;

    public CustomView3(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        // 从布局文件中拿到各个控件的对象
        initView();

        // 为各个控件填充数据
        initAttribute(attrs);

        // 定义各个控件的行为
        initBehavior();
    }

    private void initView() {
        // 通过 LayoutInflater 加载指定的布局文件，并获取布局文件中各个控件对象
        LayoutInflater.from(mContext).inflate(R.layout.customview_view_custom_customview3, this, true);
        lblUsername = findViewById(R.id.lblUsername);
        lblPassword = findViewById(R.id.lblPassword);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void initAttribute(AttributeSet attrs) {
        // 根据此组合控件的调用者的设置，为用户名文字和密码文字赋值
        TypedArray attributes = mContext.obtainStyledAttributes(attrs, R.styleable.CustomView3);
        if (attributes != null) {
            String attrUsernameLabel = attributes.getString(R.styleable.CustomView3_usernameLabel);
            String attrPasswordLabel = attributes.getString(R.styleable.CustomView3_passwordLabel);
            if (attrUsernameLabel != null) {
                lblUsername.setText(attrUsernameLabel);
            }
            if (attrPasswordLabel != null) {
                lblPassword.setText(attrPasswordLabel);
            }
        }
    }

    private void initBehavior() {
        // 登录按钮被点击后，触发登录事件
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString().trim(); // 用户输入的用户名
                String password = txtPassword.getText().toString().trim(); // 用户输入的密码
                performLogin(v, username, password);
            }
        });
    }

    OnLoginListener mOnLoginListener;
    // 定义登录事件监听器接口
    public interface OnLoginListener {
        /**
         * 登录事件发生后的回调
         * @param v 触发登录事件的 view
         * @param username 用户输入的用户名
         * @param password 用户输入的密码
         */
        void login(View v, String username, String password);
    }
    // 注册登录事件监听器
    public void setOnLoginListener(OnLoginListener listener) {
        mOnLoginListener = listener;
    }
    // 触发登录事件
    protected void performLogin(View v, String username, String password) {
        if (mOnLoginListener != null) {
            mOnLoginListener.login(v, username, password);
        }
    }
}