/**
 * 读取 meta-data 数据
 *
 * 在 AndroidManifest.xml 的 application 节点或 activity 节点或 service 节点或 receiver 节点下可以配置 meta-data 数据
 * 本例以在 application 节点下配置如下数据为例，你可以获取 value 指定的值或 resource 指定的资源标识
 * <meta-data android:name="com.webabcd.androiddemo.MetaData1" android:value="abc" />
 * <meta-data android:name="com.webabcd.androiddemo.MetaData2" android:value="@string/sample_hello1" />
 * <meta-data android:name="com.webabcd.androiddemo.MetaData3" android:resource="@string/sample_hello1" />
 */

package com.webabcd.androiddemo.resource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class MetaDataDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_metadatademo1);

        mButton1 = findViewById(R.id.button1);

        sample1();
    }

    private void sample1() {

        String result = "";
        try {
            /**
             * 通过 getPackageManager().getApplicationInfo() 可以获取 application 节点下的 meta-data 数据
             * 通过 getPackageManager().getActivityInfo() 可以获取 activity 节点下的 meta-data 数据
             * 通过 getPackageManager().getServiceInfo() 可以获取 service 节点下的 meta-data 数据
             * 通过 getPackageManager().getReceiverInfo() 可以获取 receiver 节点下的 meta-data 数据
             */
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

            // 获取 <meta-data android:name="com.webabcd.androiddemo.MetaData1" android:value="abc" /> 中指定的值
            result = "MetaData1:" + info.metaData.getString("com.webabcd.androiddemo.MetaData1");

            // 获取 <meta-data android:name="com.webabcd.androiddemo.MetaData2" android:value="@string/sample_hello1" /> 中指定的值
            result += ", MetaData2:" + info.metaData.getString("com.webabcd.androiddemo.MetaData2");

            // 获取 <meta-data android:name="com.webabcd.androiddemo.MetaData3" android:resource="@string/sample_hello1" /> 中指定的资源 id
            int resourceId = info.metaData.getInt("com.webabcd.androiddemo.MetaData3");
            result += ", MetaData3:" + getResources().getString(resourceId);

        } catch (PackageManager.NameNotFoundException e) {
            result = e.toString();
        }

        final String finalResult = result;
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MetaDataDemo1.this, finalResult, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}