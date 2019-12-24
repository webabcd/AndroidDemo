package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemo9 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listview_listviewdemo9);



        sample();
    }

    private void sample() {

        // 选中某项弹出上下文菜单
        // 编辑，删除操作
    }


}
