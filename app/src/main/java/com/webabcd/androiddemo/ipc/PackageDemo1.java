/**
 * 本例用于演示如何
 * 1、打开系统自带应用程序
 * 2、通过指定 package 打开指定的 apk
 * 3、通过指定 package, activity 打开指定的 apk（并传递参数）
 * 正常演示此示例需要安装 AndroidDemoIpc 项目生成的 app
 *
 * 注：本例打开的 activity 的具体实现请参见 AndroidDemoIpc 项目中的 PackageDemo1.java
 */

package com.webabcd.androiddemo.ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webabcd.androiddemo.R;

public class PackageDemo1 extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_packagedemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        // 演示如何打开系统自带应用程序
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开系统浏览器，并搜索“webabcd”（其他更多的打开系统程序的方法网上搜吧，方式都是类似的）
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "webabcd");
                startActivity(intent);
            }
        });

        // 演示如何通过指定 package 打开指定的 apk
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 指定需要打开的程序的包名
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.webabcd.androiddemoipc");
                startActivity(intent);
            }
        });

        // 演示如何通过指定 package, activity 打开指定的 apk（并传递参数）
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 指定需要打开的程序的 package 和 activity
                ComponentName componentName = new ComponentName("com.webabcd.androiddemoipc", "com.webabcd.androiddemoipc.PackageDemo1");

                // 传递参数
                Bundle bundle = new Bundle();
                bundle.putString("p1", "v1");
                bundle.putString("p2", "v2");

                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}