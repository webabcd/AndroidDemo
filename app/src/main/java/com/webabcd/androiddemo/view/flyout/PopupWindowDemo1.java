/**
 * PopupWindow - 悬浮框（与 AlertDialog 不同的是，PopupWindow 没有背景自动灰色半透明的效果）
 *
 * 本例会演示如何弹出一个自定义 view 的悬浮框，如何控制点击空白处和返回键的逻辑，以及如何指定悬浮框的位置和动画等
 */

package com.webabcd.androiddemo.view.flyout;

import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class PopupWindowDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_popupwindowdemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从布局文件中加载 PopupWindow 需要显示的 view
                View customView = LayoutInflater.from(PopupWindowDemo1.this).inflate(R.layout.popupwindow_view_layout_popupwindowdemo1, null, false);

                // 设置自定义 view 中的显示内容
                ((TextView) customView.findViewById(R.id.textViewTitle)).setText("标题");
                ((TextView) customView.findViewById(R.id.textViewContent)).setText("内容");
                ((Button) customView.findViewById(R.id.buttonClose)).setText("X");
                ((Button) customView.findViewById(R.id.buttonConfirm)).setText("确认");
                ((Button) customView.findViewById(R.id.buttonCancel)).setText("取消");


                // 实例化 PopupWindow，并指定其需要显示的 view 以及宽和高
                final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // setFocusable()
                //     true - 点击空白处或点击返回键会自动隐藏 PopupWindow，如果是 android 5.0 以下系统还需要另外设置 setBackgroundDrawable()
                //     false - 点击空白处不会自动隐藏 PopupWindow，点击返回键会连同父页面一起退出
                //         如果需要点击返回键只让 PopupWindow 退出的话，可以重写页面的 onBackPressed() 方法并在其中增加 popupWindow.dismiss() 逻辑
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

                // 设置 PopupWindow 的宽和高（也可以在构造函数中指定）
                // popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                // popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                // setAnimationStyle() - 设置 PopupWindow 的显示和隐藏动画（参见 res/values/styles.xml 中的 MyPopupWindowStyle）
                popupWindow.setAnimationStyle(R.style.MyPopupWindowStyle);

                // showAsDropDown() - 设置 PopupWindow 的显示位置并显示
                //     第 1 个参数：参照 view
                //     第 2 个参数：相对于参照 view 的左侧的 x 方向的偏移量
                //     第 3 个参数：相对于参照 view 的下侧的 y 方向的偏移量
                // popupWindow.showAsDropDown(v, 0, 0);

                // showAtLocation() - 设置 PopupWindow 的显示位置并显示
                //     第 1 个参数：页面上的任意 view 均可
                //     第 2 个参数：参照点相对于整个页面的位置
                //     第 3 个参数：相对于参照点的 x 方向的距离
                //     第 4 个参数：相对于参照点的 y 方向的距离
                popupWindow.showAtLocation(v, Gravity.TOP | Gravity.LEFT, 0, 0);


                // 自定义 view 中的关闭按钮的点击事件
                customView.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowDemo1.this, "点击了关闭按钮", Toast.LENGTH_SHORT).show();
                        // dismiss() - 隐藏 PopupWindow
                        popupWindow.dismiss();
                    }
                });

                // 自定义 view 中的确认按钮的点击事件
                customView.findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowDemo1.this, "点击了确认按钮", Toast.LENGTH_SHORT).show();
                        // dismiss() - 隐藏 PopupWindow
                        popupWindow.dismiss();
                    }
                });

                // 自定义 view 中的取消按钮的点击事件
                customView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PopupWindowDemo1.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        // dismiss() - 隐藏 PopupWindow
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
}