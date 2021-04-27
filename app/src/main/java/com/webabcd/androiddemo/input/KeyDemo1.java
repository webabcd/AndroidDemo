/**
 * 本例用于演示如何监听按键事件
 */

package com.webabcd.androiddemo.input;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Locale;

public class KeyDemo1 extends AppCompatActivity {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_keydemo1);

        mTextView1 = findViewById(R.id.textView1);

        // 注：
        // 弹出 AlertDialog 后则当前 Activity 就监听不到按键事件了，因为 Activity 失去焦点了
        // 此时，可以通过 AlertDialog 的 setOnKeyListener() 来监听按键事件，示例代码如下
        /*
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // 不再需要其他人来处理此按键了
                    return true;
                }
                return false;
            }
        });
        */
    }

    // 按键按下的事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // keyCode - 按键的键值（比如 KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP 等等）
        // event.getRepeatCount() - 按键按下不动时，会重复触发 onKeyDown 事件，这个方法返回的就是这个重复次数
        String msg = String.format(Locale.US, "onKeyDown keyCode:%d, repeatCount:%d", keyCode, event.getRepeatCount());
        mTextView1.setText(msg);

        // 返回 true 的意思就是不再需要其他人来处理此按键了
        // 比如你想按下返回键后不返回，那就在这里判断，如果按了返回键就直接 return true
        // return true;

        return super.onKeyDown(keyCode, event);
    }

    // 按键抬起的事件
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        String msg = String.format(Locale.US, "onKeyUp keyCode:%d", keyCode);
        mTextView1.setText(msg);

        return super.onKeyUp(keyCode, event);
    }
}