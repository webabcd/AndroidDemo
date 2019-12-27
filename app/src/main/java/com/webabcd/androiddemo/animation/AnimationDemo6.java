/**
 * 属性动画（Property Animation）中的 ObjectAnimator
 * 1、Animator - 包括 ValueAnimator 和 ObjectAnimator，是动画控制的核心类
 * 2、TimeInterpolator - 就是 Interpolator，用于计算不同时间点的动画结果的比例值，默认值是 LinearInterpolator 匀速变化
 * 3、TypeEvaluator - 用于根据从 TimeInterpolator 获取到的比例值，计算出对应的实际位置
 *
 * ObjectAnimator - 用于将对象的某属性的变化动画化，继承自 ValueAnimator（在 xml 定义的各种值控制器均需要放在 res/animator 目录下）
 *     因为 ObjectAnimator 继承自 ValueAnimator，这部分仔细看 ValueAnimator 就好，关于 ValueAnimator 请参见 animation/AnimationDemo5.java
 *     相对于 ValueAnimator 来说 ObjectAnimator 的 ofInt(), ofFloat(), ofArgb(), ofObject() 方法均多了两个参数
 *         第一个参数：用于指定当前 ObjectAnimator 需要绑定到的对象
 *         第二个参数：用于指定当前 ObjectAnimator 需要绑定到的属性名称
 *             比如某对象有 getAlpha(), setAlpha() 方法，那么这里需要绑定的属性名称就是 alpha，对于自定义控件来说也要遵守这个规范
 *
 * AnimatorSet - 用于定义一组 ObjectAnimator 动画，支持嵌套，继承自 Animator
 *     play(Animator anim) - 执行指定 Animator
 *     play(Animator anim1).before(Animator anim2) - 先执行 anim1 再执行 anim2
 *     play(Animator anim1).after(Animator anim2) - 先执行 anim2 再执行 anim1
 *     play(Animator anim1).with(Animator anim2) - 同时执行 anim1 和 anim2
 *     以上，在调用 play() 之后就支持无限调用 before(), after(), with() 的链式语法了
 *     以上，只是用于设置不同 ObjectAnimator 之间的调用顺序，要启动此 AnimatorSet 的话还是要调用 start() 方法
 *
 * Animator - 控制器
 *     这部分的说明，请参见 animation/AnimationDemo5.java
 *     setTarget(Object target) - 如果 Animator 是 ObjectAnimator 或 AnimatorSet 的话就通过此方法将 xml 中定义的 ObjectAnimator 或 AnimatorSet 绑定到指定的对象
 *
 * AnimatorInflater.loadAnimator(Context context, int id) - 获取 xml 中定义的 Animator 对象
 */


package com.webabcd.androiddemo.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class AnimationDemo6 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo6);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);

        sample1();
        sample2();
    }

    private void sample1() {
        // 从 xml 中加载 Animator
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set_animation_animationdemo6);
        // 将 Animator 绑定到指定的对象
        animator.setTarget(mTextView1);
        // 启动动画
        animator.start();
    }

    private void sample2() {
        AnimatorSet animatorSet = new AnimatorSet();

        /**
         * 相对于 ValueAnimator 来说 ObjectAnimator 的 ofInt(), ofFloat(), ofArgb(), ofObject() 方法均多了两个参数
         * 第一个参数：用于指定当前 ObjectAnimator 需要绑定到的对象
         * 第二个参数：用于指定当前 ObjectAnimator 需要绑定到的属性名称
         *     比如某对象有 getAlpha(), setAlpha() 方法，那么这里需要绑定的属性名称就是 alpha，对于自定义控件来说也要遵守这个规范
         */
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mTextView2, "translationX",0f, Helper.dp2px(this, 100));
        objectAnimator1.setDuration(1000);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mTextView2, "alpha",1f, 0f, 1f);
        objectAnimator2.setDuration(1000);

        // 先执行 objectAnimator1 再执行 objectAnimator2
        animatorSet.play(objectAnimator1).before(objectAnimator2);

        // 启动动画
        animatorSet.start();
    }
}
