/**
 * 自定义 TabBar 控件，继承自 LinearLayout
 *
 * 本身 LinearLayout 用于水平排列每个 tab 项
 * 每个 tab 项是一个 LinearLayout，用于垂直排列此 tab 项的图标和文本
 */

package com.webabcd.androiddemo.view.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class TabBarDemo1_MyTabBar extends LinearLayout {

    // TabBar 的文字
    private String[] mTextList = {"tab1", "tab2", "tab3"};
    // TabBar 的图标（非选中状态）
    private int[] mIconDefaultList = {R.drawable.ic_expand_less, R.drawable.ic_expand_less, R.drawable.ic_expand_less};
    // TabBar 的图标（选中状态）
    private int[] mIconSelectedList = {R.drawable.ic_expand_more, R.drawable.ic_expand_more, R.drawable.ic_expand_more};
    // TabBar 的文字颜色（非选中状态）
    private int mColorDefault = getResources().getColor(R.color.red, null);
    // TabBar 的文字颜色（选中状态）
    private int mColorSelected = getResources().getColor(R.color.green, null);
    // TabBar 的背景颜色
    private int mColorBackground = getResources().getColor(R.color.white, null);

    // 当前选中的 tab 项的索引位置
    private int mSelectedIndex = 0;

    public TabBarDemo1_MyTabBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        // 本身 LinearLayout 用于水平排列每个 tab 项
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Helper.dp2px(context, 60)));
        setBackgroundColor(mColorBackground);

        // 构造每个 tab 项
        for (int i = 0; i < mTextList.length; i++) {

            // 每个 tab 项是一个 LinearLayout（用于垂直排列此 tab 项的图标和文本）
            final LinearLayout linearLayoutChild = new LinearLayout(context);
            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            linearLayoutChild.setGravity(Gravity.CENTER);
            linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
            linearLayoutChild.setWeightSum(1);
            linearLayoutChild.setTag(i); // tag 值与当前 tab 的索引位置关联
            addView(linearLayoutChild);

            // 某个 tab 项的图标
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Helper.dp2px(context, 26), Helper.dp2px(context, 26));
            lp.setMargins(0, 5, 0, 0);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(mIconDefaultList[i]);
            imageView.setTag("image_" + i); // tag 值与当前 tab 的索引位置关联
            linearLayoutChild.addView(imageView);

            // 某个 tab 项的文字
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 0);
            TextView textView = new TextView(context);
            textView.setText(mTextList[i]);
            textView.setTextSize(14);
            textView.setTextColor(mColorDefault);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(lp);
            textView.setTag("text_" + i); // tag 值与当前 tab 的索引位置关联
            linearLayoutChild.addView(textView);

            // 某个 tab 项的点击事件
            linearLayoutChild.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 指定当前选中的 tab 项
                    setSelectedTab(v);
                }
            });
        }

        // 初始化选中的 tab 项的图片
        ImageView imageView = findViewWithTag("image_" + mSelectedIndex);
        imageView.setImageResource(mIconSelectedList[mSelectedIndex]);

        // 初始化选中的 tab 项的文本颜色
        TextView textView = findViewWithTag("text_" + mSelectedIndex);
        textView.setTextColor(mColorSelected);

    }

    // 指定当前选中的 tab 项
    private void setSelectedTab(View view) {
        if (mSelectedIndex != (int) view.getTag()) {

            // 设置上一次被选中的 tab 项的图片（现在变为未选中）
            ImageView imageView1 = findViewWithTag("image_" + mSelectedIndex);
            imageView1.setImageResource(mIconDefaultList[mSelectedIndex]);

            // 设置上一次被选中的 tab 项的文本颜色（现在变为未选中）
            TextView textView1 = findViewWithTag("text_" + mSelectedIndex);
            textView1.setTextColor(mColorDefault);

            // 设置当前选中的 tab 项的索引位置
            mSelectedIndex = (int) view.getTag();

            // 设置当前选中的 tab 项的图片
            ImageView imageView = view.findViewWithTag("image_" + mSelectedIndex);
            imageView.setImageResource(mIconSelectedList[mSelectedIndex]);

            // 设置当前选中的 tab 项的文本颜色
            TextView textView = view.findViewWithTag("text_" + mSelectedIndex);
            textView.setTextColor(mColorSelected);

            // 触发选中的 tab 项发生变化时的事件
            performItemChanged(mSelectedIndex);
        }
    }

    // 选中的 tab 项发生变化时的回调
    private OnItemChangedListener mOnItemChangedListener;
    public interface OnItemChangedListener {
        void onItemChanged(int itemIndex);
    }
    public void setOnItemChangedListener(OnItemChangedListener listener) {
        mOnItemChangedListener = listener;
    }
    protected void performItemChanged(int itemIndex) {
        if (mOnItemChangedListener != null) {
            mOnItemChangedListener.onItemChanged(itemIndex);
        }
    }
}