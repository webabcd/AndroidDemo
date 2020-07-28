/**
 * 视图动画（View Animation）插值器（Interpolator）
 * 视图动画只是改变了 View 的视觉效果，而并没有改变 View 的属性（比如 left, top, right, bottom 之类的都是不变的）
 * View Animation（视图动画）即 Tween Animation（补间动画）
 * 视图动画支持插值器（用于计算不同时间点的动画结果的比例值），相当于 easing 动画，默认值是 LinearInterpolator 匀速变化
 *
 *
 * BounceInterpolator - 动画结束前来回弹几下
 *
 * AccelerateInterpolator - 先慢后快
 *     构造函数有一个 float factor 参数，用于指定运动的变化程度，默认值为 1
 *
 * DecelerateInterpolator - 先快后慢
 *     构造函数有一个 float factor 参数，用于指定运动的变化程度，默认值为 1
 *
 * AccelerateDecelerateInterpolator - 始末慢，中间快
 *
 * LinearInterpolator - 匀速
 *
 * CycleInterpolator - 按正弦曲线来回运动
 *     构造函数有一个 float cycles 参数，用于指定正弦的周期数，默认值为 1
 *
 * AnticipateInterpolator - 开始时垃一下
 *     构造函数有一个 float tension 参数，用于指定拉力的程度，默认值为 1
 *
 * OvershootInterpolator - 结束前垃一下
 *     构造函数有一个 float tension 参数，用于指定拉力的程度，默认值为 1
 *
 * AnticipateOvershootInterpolator - 开始结束都垃一下
 *     构造函数有一个 float tension 参数，用于指定拉力的程度，默认值为 1
 *
 * AnimationUtils.loadInterpolator(Context context, int id) - 获取 xml 中定义的 Interpolator 对象
 *
 *
 * 注：以上所有插值器都有一个 public float getInterpolation(float input) 方法，用于获取指定值（0 - 1 之间）的插值后的结果
 */

package com.webabcd.androiddemo.animation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class AnimationDemo2 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;
    private Button _button5;
    private Button _button6;
    private Button _button7;
    private Button _button8;
    private Button _button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo2);

        _textView1 = findViewById(R.id.textView1);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);
        _button4 = findViewById(R.id.button4);
        _button5 = findViewById(R.id.button5);
        _button6 = findViewById(R.id.button6);
        _button7 = findViewById(R.id.button7);
        _button8 = findViewById(R.id.button8);
        _button9 = findViewById(R.id.button9);

        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AnimationUtils.loadAnimation() - 从 xml 中加载 Animation
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                _textView1.startAnimation(animation);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                // 设置动画的插值器（插值器来自资源文件）
                animation.setInterpolator(AnimationDemo2.this, R.anim.accelerateinterpolator_animation_animationdemo2);
                _textView1.startAnimation(animation);
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                // 实例化插值器
                DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(1.0f);
                // 设置动画的插值器
                animation.setInterpolator(decelerateInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
                animation.setInterpolator(accelerateDecelerateInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                LinearInterpolator linearInterpolator = new LinearInterpolator();
                animation.setInterpolator(linearInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                CycleInterpolator cycleInterpolator = new CycleInterpolator(2f);
                animation.setInterpolator(cycleInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                AnticipateInterpolator anticipateInterpolator = new AnticipateInterpolator(1f);
                animation.setInterpolator(anticipateInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1f);
                animation.setInterpolator(overshootInterpolator);
                _textView1.startAnimation(animation);
            }
        });

        _button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(AnimationDemo2.this, R.anim.translate_animationdemo2);
                AnticipateOvershootInterpolator anticipateOvershootInterpolator = new AnticipateOvershootInterpolator(1f);
                animation.setInterpolator(anticipateOvershootInterpolator);
                _textView1.startAnimation(animation);
            }
        });
    }
}
