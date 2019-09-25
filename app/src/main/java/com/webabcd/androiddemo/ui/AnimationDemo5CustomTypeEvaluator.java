/**
 * 自定义 TypeEvaluator
 *
 * 本例用于根据从 TimeInterpolator 获取到的比例值，计算出 Point 的值
 */

package com.webabcd.androiddemo.ui;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class AnimationDemo5CustomTypeEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        // startValue - Point 的开始值
        // endValue - Point 的结束值
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        // fraction 是通过 TimeInterpolator 计算出的比例值，需要根据此值以及 startValue 和 endValue 计算出 fraction 对应的 Point 值
        float x = startPoint.x + (endPoint.x - startPoint.x) * fraction;
        float y = startPoint.y + (endPoint.y - startPoint.y) * fraction;

        return new Point((int)x, (int)y);
    }
}
