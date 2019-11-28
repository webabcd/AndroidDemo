/**
 * 屏幕密度（物理分辨率, 逻辑分辨率, density, dpi, drawable 文件夹, mipmap 文件夹, dp, sp, px）
 */

package com.webabcd.androiddemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class DensityDemo1 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_densitydemo1);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);
        mTextView4 = findViewById(R.id.textView4);

        sample();
    }

    private void sample() {
        /**
         * 物理分辨率就是真实分辨率
         * 逻辑分辨率就是显示分辨率
         * density 等于物理分辨率除以逻辑分辨率
         *
         * dpi 就是 dots per inch
         * density 等于 dpi 值除以 160，也就是说 160dpi 的 density 为 1，420dpi 的 density 为 2.625
         */
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mTextView1.setText(String.format("density:%f, dpi:%d, 物理分辨率:%d*%d, 逻辑分辨率:%d*%d",
                dm.density, dm.densityDpi,
                dm.widthPixels, dm.heightPixels,
                (int)(dm.widthPixels / dm.density + 0.5), (int)(dm.heightPixels / dm.density + 0.5)));


        /**
         * 一般来说 app 本身的 icon 放在 mipmap 文件夹内，其他图片一律放在 drawable 文件夹
         *
         * mipmap 文件夹和 drawable 文件夹都支持类似 -mdpi, -xxhdpi 的后缀，不同 dpi 的设备会读取不同后缀文件夹内的图片，对应关系如下
         * 120dpi 对应 -ldpi，其 density 为 0.75
         * 120-160dpi 对应 -mdpi，160dpi 的 density 为 1
         * 160-240dpi 对应 -hdpi，240dpi 的 density 为 1.5
         * 240-320dpi 对应 -xhdpi，320dpi 的 density 为 2
         * 320-480dpi 对应 -xxhdpi，480dpi 的 density 为 3
         * 480-640dpi 对应 -xxxhdpi，640dpi 的 density 为 4
         *
         * 在目标后缀文件夹找不到文件时，系统会逐级找高 dpi 对应的文件夹再逐级找低 dpi 对应的文件夹
         * 比如应该在 -xhdpi 找文件，但是找不到，那么接下来找文件的顺序就是 -xxhdpi, -xxxhdpi, -hdpi, -mdpi, 无后缀文件夹, -ldpi
         * 找到文件后，系统会按照比例对其缩放，缩放公式为 (int)(设备 density / 加载图片所在文件夹的 density * 图片真实分辨率 + 0.5)，下面的示例会对此做演示
         * 在以上列出的后缀文件夹中都找不到图片的话，则会去找 -nodpi 后缀的文件夹，但是不会对其做缩放
         * 如果有 -anydpi 后缀的文件夹（其内保存 xml 文件，在 xml 中再去引用 drawable 图片），则优先从此获取文件
         * 默认 drawable 文件夹（无后缀的文件夹）的 density 为 1，与 -mdpi 一致
         * 默认 drawable 文件夹（无后缀的文件夹）通常用来保存 xml 文件，带后缀的 drawable 文件夹用来保存图片文件
         *
         * 适配时如果只想保存一套切图的话，则建议保存 -xxhdpi 规格的图片
         * 因为如果保存低密度图片的话，那么在高密度设备中会放大低密度图片时就会模糊，所以只保留一套切图的话应该尽量面向高密度设备来进行设计
         * 所以如果想保存 -xxxhdpi 规格的切图也是可以的，但是这会增加 apk 的体积，关键是设计部门可能不愿意面向过高密度设备进行设计，所以目前来说保存主流高密度设备即 -xxhdpi 规格的图片最为合适
         *
         *
         * 本例中 son01 和 son02 是完全一样的图片，一个放在 drawable-xxhdpi 文件夹，另一个放在 drawable-mdpi 文件夹
         * 图片的分辨率为 100*100，假设设备 dpi 为 420（对应 -xxhdpi 文件夹，density 为 2.625），则会有如下结果
         * 从 -xxhdpi 加载此文件，其将被缩放到 88*88，公式为 (int)(2.625 / 3 * 100 + 0.5) = 88
         * 从 -mdpi 加载此文件，其将被缩放到 263*263，公式为 (int)(2.625 / 1 * 100 + 0.5) = 263
         * 本例仅用于演示 drawable 图片的缩放逻辑，实际应用中在不同规格 drawable 文件夹内保存同样分辨率的图片是没有意义的，也就是说网上的一些基于此场景来判断内存占用大小的结论也是无意义的
         */
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.son01, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        mTextView2.setText(String.format("width:%d, height:%d", bitmap.getWidth(), bitmap.getHeight()));
        bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.son02, null);
        bitmap = bitmapDrawable.getBitmap();
        mTextView3.setText(String.format("width:%d, height:%d", bitmap.getWidth(), bitmap.getHeight()));


        /**
         * dp 值乘以 density 后就是 px
         * sp 值乘以 density 后再乘以字体放大系数后就是 px
         *
         * 在系统设置中可以指定字体大小（一共 4 种，分别是 small, default, large, largest），其来决定字体放大系数（small - 0.85, default - 1.0, large - 1.15, largest - 1.3）
         */
        float fontScale = getResources().getConfiguration().fontScale; // 获取字体放大系数
        float dp32 = getResources().getDimension(R.dimen.dp32); // 在 res/values/dimens.xml 中定义的
        float sp32 = getResources().getDimension(R.dimen.sp32); // 在 res/values/dimens.xml 中定义的
        float px32 = getResources().getDimension(R.dimen.px32); // 在 res/values/dimens.xml 中定义的
        mTextView4.setText(String.format("fontScale:%f, 32dp=%fpx, 32sp=%fpx, 32px=%fpx", fontScale, dp32, sp32, px32));
    }
}