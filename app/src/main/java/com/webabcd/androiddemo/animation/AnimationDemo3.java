/**
 * 视图动画（View Animation）自定义 Interpolator
 * 视图动画只是改变了 View 的视觉效果，而并没有改变 View 的属性（比如 left, top, right, bottom 之类的都是不变的）
 * View Animation（视图动画）即 Tween Animation（补间动画）
 * 视图动画支持插值器（用于计算不同时间点的动画结果的比例值），相当于 easing 动画，默认值是 LinearInterpolator 匀速变化
 *
 *
 * 继承 BaseInterpolator 实现一个自定义的 Interpolator 请参见 animation/AnimationDemo3CustomInterpolator.java
 */

package com.webabcd.androiddemo.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class AnimationDemo3 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo3);

        _textView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        // 定义一个动画
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(Animation.REVERSE);

        // 实例化自定义的 Interpolator 用于实现一个先慢后快的效果
        AnimationDemo3CustomInterpolator customInterpolator = new AnimationDemo3CustomInterpolator(6f);
        translateAnimation.setInterpolator(customInterpolator);

        // 启动指定的动画
        _textView1.startAnimation(translateAnimation);
    }
}