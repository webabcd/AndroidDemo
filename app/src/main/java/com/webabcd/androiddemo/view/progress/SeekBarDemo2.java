/**
 * SeekBar - 拖动条
 *     setProgressDrawable() - 设置进度条的样式
 *     setThumb() - 设置 thumb 的样式
 */

package com.webabcd.androiddemo.view.progress;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.lang.reflect.Field;

public class SeekBarDemo2 extends AppCompatActivity {

    private SeekBar _seekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress_seekbardemo2);

        _seekBar3 = (SeekBar) findViewById(R.id.seekBar3);

        sample();
    }

    private void sample() {
        _seekBar3.setProgressDrawable(Helper.id2drawable(this, R.drawable.layerlist_seekbar_progress));
        _seekBar3.setThumb(Helper.id2drawable(this, R.drawable.selector_seekbar_thumb));

        // 自定义 SeekBar 的样式时，默认 progressBar 会变成与 thumb 一样高，可以通过设置 SeekBar 的 minHeight, maxHeight 来避免这种情况
        // 在 java 中无法直接修改 SeekBar 的 minHeight, maxHeight
        // 所以要通过如下的反射的方法来修改 SeekBar 的 minHeight, maxHeight
        try {
            // 注：mMinHeight 和 mMaxHeight 在 SeekBar 的基类 ProgressBar 中
            // 注：要看看当前 SeekBar 到底是 AppCompatSeekBar 还是 SeekBar 以便决定调用几次 getSuperclass() 从而找到 ProgressBar
            // _seekBar3.getClass() 是 android.support.v7.widget.AppCompatSeekBar
            // _seekBar3.getClass().getSuperclass() 是 android.widget.SeekBar
            // _seekBar3.getClass().getSuperclass().getSuperclass() 是 android.widget.AbsSeekBar
            // _seekBar3.getClass().getSuperclass().getSuperclass().getSuperclass() 是 android.widget.ProgressBar

            Class<?> superclass = _seekBar3.getClass().getSuperclass().getSuperclass().getSuperclass();

            Field mMaxHeight = superclass.getDeclaredField("mMaxHeight");
            mMaxHeight.setAccessible(true);
            mMaxHeight.set(_seekBar3, Helper.dp2px(this, 10));

            Field mMinHeight = superclass.getDeclaredField("mMinHeight");
            mMinHeight.setAccessible(true);
            mMinHeight.set(_seekBar3, Helper.dp2px(this, 10));
        } catch (Exception e) {
            Log.e("SeekBarDemo2", e.toString());
        }
    }
}
