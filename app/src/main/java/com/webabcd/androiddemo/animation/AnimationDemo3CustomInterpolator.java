/**
 * 自定义 Interpolator 实现一个先慢后快的效果
 *
 * Interpolator 用于计算不同时间点的动画结果的比例值
 */

package com.webabcd.androiddemo.animation;

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
    // 这个插值结果就是指定时间点的动画结果的比例值，这个比例值乘以（目标位置 - 初始位置）就是动画当前时间点的相对于初始位置的位置
    @Override
    public float getInterpolation(float input) {
        return (float) Math.pow(input, mPow);
    }
}
