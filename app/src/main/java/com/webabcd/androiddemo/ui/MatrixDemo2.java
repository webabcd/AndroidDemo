/**
 * Matrix 变换（通过自定义控件实现）
 *
 * ImageView 有设置其 Matrix 变换的方法，但是其他控件都没有，那么如何设置其他控件的 Matrix 变换呢
 * 本例演示如何重写 TextView 控件的 onDraw() 方法，以使其支持 Matrix 变换（详细代码参见：ui/MatrixDemo2CustomView.java）
 */

package com.webabcd.androiddemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class MatrixDemo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_matrixdemo2);
    }
}
