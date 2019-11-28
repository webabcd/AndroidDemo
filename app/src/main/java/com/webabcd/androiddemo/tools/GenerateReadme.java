package com.webabcd.androiddemo.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webabcd.androiddemo.MainExpandableListAdapter;
import com.webabcd.androiddemo.MainNavigationBean;
import com.webabcd.androiddemo.OnGroupExpandedListener;
import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GenerateReadme extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_generatereadme);

        mEditText = findViewById(R.id.editText1);

        sample();
    }

    private void sample() {
        StringBuilder sb = new StringBuilder();

        String jsonString = Helper.getAssetString("site_map.json", this);
        Type type = new TypeToken<List<MainNavigationBean>>() { }.getType();
        Gson gson = new Gson();
        final ArrayList<MainNavigationBean> navigationBeanList = gson.fromJson(jsonString, type);

        sb.append("# Android Demo\n\n\n");
        for (MainNavigationBean l1 : navigationBeanList) {
            sb.append("#### " + l1.getTitle() + "\n");
            int i = 1;
            for (MainNavigationBean.NodeBean l2 : l1.getNode()) {
                sb.append(i + ". " + l2.getTitle() + "\n");
                i++;
            }
            sb.append("\n");
        }

        mEditText.setText(sb.toString());
    }
}
