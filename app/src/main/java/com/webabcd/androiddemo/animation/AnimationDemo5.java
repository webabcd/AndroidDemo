/**
 * 属性动画（Property Animation）中的 ValueAnimator
 * 1、Animator - 包括 ValueAnimator 和 ObjectAnimator，是动画控制的核心类
 * 2、TimeInterpolator - 就是 Interpolator，用于计算不同时间点的动画结果的比例值，默认值是 LinearInterpolator 匀速变化
 * 3、TypeEvaluator - 用于根据从 TimeInterpolator 获取到的比例值，计算出对应的实际位置
 *
 * ValueAnimator - 用于将某类型的值的变化动画化，继承自 Animator（在 xml 定义的各种值控制器均需要放在 res/animator 目录下）
 *     ofInt(int... values) - 创建 ValueAnimator 用于控制指定的 n 个 int 类型的值之间的动画（内置 TypeEvaluator）
 *     ofFloat(float... values) - 创建 ValueAnimator 用于控制指定的 n 个 float 类型的值之间的动画（内置 TypeEvaluator）
 *     ofArgb(int... values) - 创建 ValueAnimator 用于控制指定的 n 个 color 类型的值之间的动画（内置 TypeEvaluator）
 *     ofObject(TypeEvaluator evaluator, Object... values) - 创建 ValueAnimator 用于控制指定的 n 个 object 类型的值之间的动画（需要自定义 object 的 TypeEvaluator）
 *     start(), pause(), resume(), stop() - 启动控制器，暂停控制器，继续控制器，停止控制器
 *     setRepeatCount() - 重复次数（-1 为无限循环）
 *     setRepeatMode() - 动画结束后的重复方式
 *         ValueAnimator.RESTART - 重头播放
 *         ValueAnimator.REVERSE - 来回播放
 *     setCurrentPlayTime() - 用于强行指定当前的动画的时间点
 *     setCurrentFraction() - 用于强行指定当前的动画结果的比例值
 *     addUpdateListener() - 值控制器更新值的时候触发的事件
 *         onAnimationUpdate() - 动画更新了
 *     getAnimatedValue() - 获取当前值
 * 注：在 addUpdateListener() 中通过 getAnimatedValue() 拿到此刻动画后的值，将此值赋值给需要动画化的属性即可
 *
 * Animator - 控制器
 *     setStartDelay() - 设置动画启动的延迟时间，单位：毫秒
 *     setDuration() - 设置动画的持续时间，单位：毫秒
 *     setInterpolator() - 指定此动画的 Interpolator（关于 Interpolator 参见 animation/AnimationDemo2 和 animation/AnimationDemo3）
 *     addListener() - 动画事件
 *         onAnimationStart() - 动画开始
 *         onAnimationEnd() - 动画结束
 *         onAnimationCancel() - 动画取消
 *         onAnimationRepeat() - 动画重复
 *
 * AnimatorInflater.loadAnimator(Context context, int id) - 获取 xml 中定义的 Animator 对象
 */

package com.webabcd.androiddemo.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class AnimationDemo5 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo5);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);

        sample1();
        sample2();
    }

    private void sample1() {
        // AnimatorInflater.loadAnimator() - 从 xml 中加载 ValueAnimator
        ValueAnimator animator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_animationdemo5);
        // 启动动画
        animator.start();

        // 在 java 中创建 ValueAnimator 的话就像下面这样写，其意思是在 0f - 200f 之间控制动画（当然根据你的 Interpolator 的不同，计算出的结果可能不在此范围内）
        // 下面这个是控制 float 类型的动画的，其他的还有 ofInt(), ofArgb(), ofObject()，均可以指定 n 个值，控制这 n 个值之间的动画（当然根据你的 Interpolator 的不同，计算出的结果可能不在此范围内）
        // ValueAnimator animator = ValueAnimator.ofFloat(0f, 200f);

        // 监听值的变化
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取变化后的值
                float currentValue = (float) animation.getAnimatedValue();
                // 将变化后的值赋值给需要动画化的属性
                mTextView1.setX(currentValue);
            }
        });

        // 动画事件
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // 动画开始
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // 动画取消
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // 动画重复
            }
        });
    }

    private void sample2() {
        // 定义动画的起始点
        Point point1 = new Point(0, 0);
        Point point2 = new Point(200, 50);

        // 创建 ValueAnimator 对象，指定 TypeEvaluator（参见 animation/AnimationDemo5CustomTypeEvaluator.java）和动画的起始点（当然根据你的 Interpolator 的不同，计算出的结果可能不在此起始点之间）
        ValueAnimator animator = ValueAnimator.ofObject(new AnimationDemo5CustomTypeEvaluator(), point1, point2);
        // 设置动画的一些参数
        animator.setStartDelay(100);
        animator.setDuration(2000);
        animator.setRepeatCount(0);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        // 指定 TimeInterpolator 即 Interpolator，不指定的话默认值是 LinearInterpolator 匀速变化
        animator.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));

        // 监听值的变化
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取变化后的值
                Point currentValue = (Point) animation.getAnimatedValue();

                // 将变化后的值赋值给需要动画化的属性
                mTextView2.setX(currentValue.x);
                mTextView2.setY(currentValue.y);
            }
        });

        // 启动动画
        animator.start();
    }
}
