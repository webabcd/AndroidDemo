/**
 * 在 AndroidManifest.xml 中通过如下方式指定普通图标和圆形图标
 * <application android:icon="@mipmap/ic_launcher" android:roundIcon="@mipmap/ic_launcher_round" />
 *
 * 关于屏幕密度的说明请参见 /ui/DensityDemo1.java
 */

package com.webabcd.androiddemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class IconDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_icondemo1);
    }
}