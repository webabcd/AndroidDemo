/**
 * 自定义 Interpolator 实现一个先慢后快的效果
 */

package com.webabcd.androiddemo.ui;

import android.view.animation.BaseInterpolator;

// 继承 BaseInterpolator 实现一个自定义的 Interpolator
public class AnimationDemo3CustomInterpolator extends BaseInterpolator {

    private float mPow;

    /**
     * @param pow 值越大，先慢后快的效果越明显
     */
    public AnimationDemo3CustomInterpolator(float pow) {
        mPow = pow;
    }

    // 传入一个动画的时间点值（0 - 1 之间，0 对应动画开始的时间点，1 对应动画结束的时间点），返回插值结果（允许不在 0 - 1 之间）
    // 动画的目标位置乘以插值结果就是动画当前时间点的位置
    public float getInterpolation(float input) {
        return (float) Math.pow(input, mPow);
    }
}
