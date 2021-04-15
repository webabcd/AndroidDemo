/**
 * Application 的与优化相关的知识点
 */

package com.webabcd.androiddemo.optimize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.MyApplication;
import com.webabcd.androiddemo.R;

public class Demo3 extends AppCompatActivity {

    private final String LOG_TAG = "optimize_Demo3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_demo3);

        // 通过自定义 Application 管理全局属性（当然，要实现全局变量也可以不通过 Application 实现，在一个自定义类中使用静态属性来实现也是挺好的）
        String globalParam = ((MyApplication)this.getApplication()).getParam();
        // String globalParam = MyApplication.getInstance().getParam();
        Log.i(LOG_TAG, "globalParam: " + globalParam);

        // 建议在 application 中构造全局使用的 thread
        MyApplication.getInstance().startThread();
    }

    @Override
    protected void onDestroy() {
        MyApplication.getInstance().stopThread();
        super.onDestroy();
    }
}