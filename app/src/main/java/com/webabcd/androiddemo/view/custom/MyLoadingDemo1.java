/**
 * 开发一个自定义圆形带进度提示的 loading 控件
 *
 * 参见 view/custom/MyLoading1.java
 */

package com.webabcd.androiddemo.view.custom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;

import com.webabcd.androiddemo.R;

public class MyLoadingDemo1 extends AppCompatActivity {

    private MyLoading1 myLoading1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom_myloadingdemo1);

        myLoading1 = findViewById(R.id.myLoading1);

        sample();
    }

    private void sample() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (true) {
                    // 更新当前进度值
                    myLoading1.setProgress(progress);
                    progress++;

                    if (progress > 100) {
                        break;
                    }

                    SystemClock.sleep(50);
                }
            }
        });
        thread.setName("thread_MyLoadingDemo1");
        thread.setDaemon(true);
        thread.start();
    }
}
