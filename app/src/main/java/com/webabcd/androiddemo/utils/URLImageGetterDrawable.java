/**
 * 用于绘制 ImageGetter 获得的 Drawable 对象
 *
 * 因为 ImageGetter 返回的 Drawable 是不可变的，但是在网络图片下载完成后需要将它绘制出来，所以需要对 Drawable 再做一层封装
 */

package com.webabcd.androiddemo.utils;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class URLImageGetterDrawable extends BitmapDrawable {

    // 需要被绘制的 Drawable 对象
    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}