/**
 * EditText - 文本编辑控件（继承自 TextView）
 *
 * 演示 EditText 的软键盘相关的知识点（EditText 不自动获取焦点，显示或隐藏软键盘，获取软键盘的高度）
 */

package com.webabcd.androiddemo.view.text;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.view.text.utils.KeyboardHeightObserver;
import com.webabcd.androiddemo.view.text.utils.KeyboardHeightProvider;

public class EditTextDemo3 extends AppCompatActivity implements KeyboardHeightObserver {

    private EditText _editText1;
    private Button _button1;

    // 用于获取软键盘高度的对象
    private KeyboardHeightProvider _keyboardHeightProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_edittextdemo3);

        _editText1 = (EditText) findViewById(R.id.editText1);
        _button1 = (Button) findViewById(R.id.button1);

        _keyboardHeightProvider = new KeyboardHeightProvider(this);
        _editText1.post(new Runnable() {
            public void run() {
                // 确保在 Activity 的 onResume 完成之后调用 KeyboardHeightProvider 的 start()，否则无法注册 PopupWindow
                _keyboardHeightProvider.start();
            }
        });

        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(_keyboardHeightProvider.softInputHeight > 0){
                    // 如果软键盘是弹出状态，则将其隐藏
                    imm.hideSoftInputFromWindow(_editText1.getWindowToken(),0);
                }
                else {
                    // 如果软键盘是隐藏状态，则将其弹出
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        _keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        _keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _keyboardHeightProvider.close();
    }

    /**
     * 监听软键盘高度变化的通知
     */
    @Override
    public void onKeyboardHeightChanged(int height) {
        _editText1.setText("软键盘的高度：" + String.valueOf(height));
    }
}



