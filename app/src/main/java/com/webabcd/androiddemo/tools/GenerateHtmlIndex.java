package com.webabcd.androiddemo.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webabcd.androiddemo.MainNavigationBean;
import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GenerateHtmlIndex extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_generatehtmlindex);

        mEditText = findViewById(R.id.editText1);

        sample();
    }

    private void sample() {
        StringBuilder sb = new StringBuilder();

        String jsonString = Helper.getAssetString("site_map.json", this);
        Type type = new TypeToken<List<MainNavigationBean>>() { }.getType();
        Gson gson = new Gson();
        final ArrayList<MainNavigationBean> navigationBeanList = gson.fromJson(jsonString, type);

        sb.append("<li>");
        sb.append("<a title=\"一手遮天 Android 系列文章\" href=\"#\" target=\"_blank\">一手遮天 Android 系列文章</a>\n");
        sb.append("<ul>\n");
        int i = 1;
        for (MainNavigationBean l1 : navigationBeanList) {
            for (MainNavigationBean.NodeBean l2 : l1.getNodeList()) {
                sb.append(String.format(Locale.ENGLISH, "<li><a title=\"一手遮天 Android (%d) - %s: %s\" href=\"http://www.cnblogs.com/webabcd/p/%s.html\" target=\"_blank\">一手遮天 Android (%d) - %s: %s</a></li>\n",
                        i, l1.getTitle(), l2.getTitle(), "android" + l2.getUrl().replace(".", "_"), i, l1.getTitle(), l2.getTitle()));
                i++;
            }
        }
        sb.append("</ul>\n");
        sb.append("</li>");

        mEditText.setText(sb.toString());

        ClipboardManager clipboardManager = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, sb.toString());
        clipboardManager.setPrimaryClip(clipData);
    }
}