package com.webabcd.androiddemo.ipc;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class URLSchemeDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_urlschemedemo1);

        // <a href="caishilive://caishi:8080/loadtooldetail?tool_id=100">打开APP工具详情页</a>


        Intent action = new Intent(Intent.ACTION_VIEW);
        StringBuilder builder = new StringBuilder();
        builder.append("webabcd://abc:8888/xyz?tool_id=100");
        action.setData(Uri.parse(builder.toString()));
        startActivity(action);
        /*

        如下就是一个自定义的URL
caishilive://caishi:8080/loadtooldetail?tool_id=100
caishilive：即Scheme 该Scheme协议名称
caishi：即Host,代表Scheme作用于哪个地址域
8080：即port，代表端口号
loadtooldetail：即path，代表打开的页面
tool_id：即query，代表传递的参数

作者：皓皓amous
链接：https://www.jianshu.com/p/160d0470ad5b
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
    }

    /*
    private boolean schemeValid() {
        PackageManager manager = mContext.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("caishilive://caishi:8080/loadtooldetail?tool_id=100"));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

     */
}