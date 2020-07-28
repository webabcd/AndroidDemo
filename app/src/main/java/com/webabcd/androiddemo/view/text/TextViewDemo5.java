/**
 * TextView - 文本显示控件
 *
 * 演示如何通过 SpannableString 或 SpannableStringBuilder 来设置 TextView 中的文本的显示样式
 * SpannableStringBuilder 对比 SpannableString 的区别就是，可以对 SpannableStringBuilder 中的文本做编辑（比如 append, insert 之类的）
 *
 * ForegroundColorSpan - 文本颜色
 * BackgroundColorSpan - 背景颜色
 * StrikethroughSpan - 删除线
 * UnderlineSpan - 下划线
 * SuperscriptSpan - 上标
 * SubscriptSpan - 下标
 * StyleSpan - 样式（粗体，斜体）
 * TypefaceSpan - 字体
 * AbsoluteSizeSpan - 文本绝对大小
 * RelativeSizeSpan - 文本相对大小（当前文本应该显示的大小的缩放倍数）
 * ScaleXSpan - X 轴方向的缩放倍数
 * TextAppearanceSpan - 文本样式（通过指定 style 资源的方式）
 * URLSpan - 超链接
 * ClickableSpan - 可点击
 * MaskFilterSpan - 修饰效果（模糊，浮雕）
 * DynamicDrawableSpan - 将指定的文字替换为图片（抽象类）
 * ImageSpan - 将指定的文字替换为图片（ImageSpan 继承自 DynamicDrawableSpan）
 */

package com.webabcd.androiddemo.view.text;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class TextViewDemo5 extends AppCompatActivity {

    private TextView _textView1;
    private TextView _textView2;
    private TextView _textView3;
    private TextView _textView4;
    private TextView _textView5;
    private TextView _textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_textviewdemo5);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _textView3 = (TextView) findViewById(R.id.textView3);
        _textView4 = (TextView) findViewById(R.id.textView4);
        _textView5 = (TextView) findViewById(R.id.textView5);
        _textView6 = (TextView) findViewById(R.id.textView6);

        // 设置文本颜色，背景颜色，删除线，下划线，上标，下标
        sample1();
        // 设置文本样式（粗体，斜体），字体，大小
        sample2();
        // 设置文本样式（通过指定 style 资源的方式），超链接，可点击
        sample3();
        // 设置文本的修饰效果（模糊，浮雕）
        sample4();
        // 将指定的文字替换为图片
        sample5();
        // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE, Spannable.SPAN_EXCLUSIVE_INCLUSIVE, Spannable.SPAN_INCLUSIVE_EXCLUSIVE, Spannable.SPAN_INCLUSIVE_INCLUSIVE 的区别
        sample6();
    }

    private void sample1() {
        String content = "hello: webabcdwanglei";

        // 文本颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        // 背景颜色
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#00FF00"));
        // 删除线
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        // 下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        // 上标
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        // 下标
        SubscriptSpan subscriptSpan = new SubscriptSpan();

        // 将需要显示的文字转换为 SpannableStringBuilder 对象
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        // 设置 span（可以设置多个，但是同一个 span 对象只能使用一次）
        // 第一个参数：需要设置的 span 对象
        // 第二个参数：需要设置该 span 的文字的起始位置
        // 第三个参数：需要设置该 span 的文字的结束位置
        // 第四个参数：后面再说
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(backgroundColorSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(strikethroughSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(underlineSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(superscriptSpan, 7, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(subscriptSpan, 14, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 删除指定的 span
        // spannableStringBuilder.removeSpan(subscriptSpan);

        // 显示指定的 SpannableStringBuilder 对象
        _textView1.setText(spannableStringBuilder);
    }

    private void sample2() {
        String content = "hello: webabcd wanglei android";

        // 设置样式（Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC），具体的 style 的说明参见 TextViewDemo2 中的说明
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        // 设置字体，具体的 typeface 的说明参见 TextViewDemo2 中的说明
        TypefaceSpan typefaceSpan = new TypefaceSpan("monospace");
        // 文本绝对大小
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(24, true);
        // 文本相对大小（当前文本应该显示的大小的缩放倍数）
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5f);
        // X 轴方向的缩放倍数
        ScaleXSpan scaleXSpan = new ScaleXSpan(3.0f);

        // 将需要显示的文字转换为 SpannableString 对象
        SpannableString spanString = new SpannableString(content);
        // 设置 span（可以设置多个，但是同一个 span 对象只能使用一次）
        // 第一个参数：需要设置的 span 对象
        // 第二个参数：需要设置该 span 的文字的起始位置
        // 第三个参数：需要设置该 span 的文字的结束位置
        // 第四个参数：后面再说
        spanString.setSpan(styleSpan, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(typefaceSpan, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(absoluteSizeSpan, 7, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(relativeSizeSpan, 15, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(scaleXSpan, 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 删除指定的 span
        // spanString.removeSpan(scaleXSpan);

        // 显示指定的 SpannableString 对象
        _textView2.setText(spanString);
    }

    private void sample3() {
        String content = "hello: clickme clickme";

        // 文本的样式，此处指定的样式资源如下
        /*
         * <style name="TextAppearanceSpanStyle" parent="@android:style/Widget.TextView">
         *     <item name="android:textColor">#187117</item>
         *     <item name="android:textStyle">italic</item>
         *     <item name="android:fontFamily">sans-serif-thin</item>
         *     <item name="android:textSize">32dp</item>
         * </style>
         */
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(this, R.style.TextAppearanceSpanStyle);
        // 超链接，需要设置 TextView 的 setMovementMethod(LinkMovementMethod.getInstance())
        URLSpan urlSpan = new URLSpan("http://webabcd.cnblogs.com");
        // 可点击，需要设置 TextView 的 setMovementMethod(LinkMovementMethod.getInstance())
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) { // 点击后
                Toast.makeText(TextViewDemo5.this, "被点击了", Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) { // 可以在此处设置可点击文本的样式
                ds.setColor(Color.parseColor("#FF0000"));
                ds.bgColor = Color.parseColor("#00FF00");
                // 显示下划线
                ds.setUnderlineText(true);
            }
        };

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(textAppearanceSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(urlSpan, 7, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(clickableSpan, 15, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 为了可跳转或者可点击，需要设置 TextView 的 setMovementMethod(LinkMovementMethod.getInstance())
        _textView3.setMovementMethod(LinkMovementMethod.getInstance());
        _textView3.setText(spannableStringBuilder);
    }

    private void sample4() {
        String content = "什么玩意";

        // 修饰效果：模糊（有的时候会有问题）
        MaskFilter maskFilter = new BlurMaskFilter(3.0f, BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(maskFilter);

        // 修饰效果：浮雕（看不到什么效果）
        EmbossMaskFilter embossMaskFilter = new EmbossMaskFilter(new float[]{1, 2, 3}, 1.5f, 8, 3);
        MaskFilterSpan askFilterSpan = new MaskFilterSpan(embossMaskFilter);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(maskFilterSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(askFilterSpan, 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        _textView4.setText(spannableStringBuilder);
    }

    private void sample5() {
        String content = "abcxabcxabc";

        // 显示图片（抽象类）
        DynamicDrawableSpan dynamicDrawableSpan = new DynamicDrawableSpan() {
            @Override
            public Drawable getDrawable() {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                drawable.setBounds(0, 0, 50, 50);
                return drawable;
            }
        };

        // 显示图片（ImageSpan 继承自 DynamicDrawableSpan）
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 50, 50);
        ImageSpan imageSpan = new ImageSpan(drawable);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        // 将指定的文字替换为图片
        spannableStringBuilder.setSpan(dynamicDrawableSpan, 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将指定的文字替换为图片
        spannableStringBuilder.setSpan(imageSpan, 7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        _textView5.setText(spannableStringBuilder);
    }

    private void sample6() {
        String content = "0123456789";

        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#FF0000")); // 红色
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#FF0000")); // 红色

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);

        /*
         * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE - 左不包括，右不包括
         * Spannable.SPAN_EXCLUSIVE_INCLUSIVE - 左不包括，右包括
         * Spannable.SPAN_INCLUSIVE_EXCLUSIVE - 左包括，右不包括
         * Spannable.SPAN_INCLUSIVE_INCLUSIVE - 左包括，右包括
         *
         * 以上值用于指定当对应的 span 的左右相邻处插入了新字符时候，这些新字符是否需要应用此 span
         */

        // 在位置 1 - 2 处指定的是左不包括右不包括，其意思是在位置 1 的左边相邻处插入的字符不应用 span，在位置 2 的右边相邻处插入的字符不应用 span
        spannableStringBuilder.setSpan(foregroundColorSpan1, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 在位置 8 - 9 处指定的是左包括右包括，其意思是在位置 8 的左边相邻处插入的字符也应用 span，在位置 9 的右边相邻处插入的字符也应用 span
        spannableStringBuilder.setSpan(foregroundColorSpan2, 8, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        spannableStringBuilder.insert(1, "a"); // 此处插入的字符 a 不会变红
        spannableStringBuilder.insert(3, "b"); // 此处插入的字符 b 不会变红
        spannableStringBuilder.insert(10, "c"); // 此处插入的字符 c 会变红
        spannableStringBuilder.insert(12, "d"); // 此处插入的字符 d 会变红

        _textView6.setText(spannableStringBuilder);
    }
}