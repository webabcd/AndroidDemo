/**
 * 属性动画（Property Animation）中的 ViewPropertyAnimator
 * ViewPropertyAnimator 是通过 ValueAnimator 实现的
 *
 *
 * View - 视图
 *     animate() - 返回 ViewPropertyAnimator 对象
 *
 * ViewPropertyAnimator - 用于简化动画的实现（支持链式语法）
 *     setStartDelay() - 设置动画启动的延迟时间，单位：毫秒
 *     setDuration() - 设置动画的持续时间，单位：毫秒
 *     setInterpolator() - 指定此动画的 Interpolator（关于 Interpolator 参见 ui/AnimationDemo2 和 ui/AnimationDemo3）
 *     alpha() - alpha 动画到指定的值
 *     alphaBy() - alpha 动画偏移指定的值（这就是结尾有 By 和没有 By 的区别）
 *     rotation(), rotationBy(), rotationX(), rotationXBy(), rotationY(), rotationYBy() - 顾名思义
 *     scaleX(), scaleXBy(), scaleY(), scaleYBy() - 顾名思义
 *     translationX(), translationXBy(), translationY(), translationYBy(), translationZ(), translationZBy() - 顾名思义
 *     x(), xBy(), y(), yBy(), z(), zBy() - 顾名思义
 *     start() - 开始动画（一般不需要手动调用 start() 方法，因为 ViewPropertyAnimator 会自动启动动画）
 *     cancel() - 停止动画
 *     withStartAction() - 动画开始的回调
 *     withEndAction() - 动画结束的回调
 *     addListener() - 动画事件
 *         onAnimationStart() - 动画开始
 *         onAnimationEnd() - 动画结束
 *         onAnimationCancel() - 动画取消
 *         onAnimationRepeat() - 动画重复
 *     addUpdateListener() - 动画更新事件
 *         onAnimationUpdate() - 动画更新了（这里可以拿到 ValueAnimator 对象）
 *
 *
 * 注：对于动画来说，优先考虑用 ViewPropertyAnimator，不行再考虑用 ObjectAnimator， 再不行再考虑用 ValueAnimator， 还不行最后考虑自己写
 */

package com.webabcd.androiddemo.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class AnimationDemo7 extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_animationdemo7);

        mTextView1 = findViewById(R.id.textView1);
        mTextView2 = findViewById(R.id.textView2);
        mTextView3 = findViewById(R.id.textView3);
        mTextView4 = findViewById(R.id.textView4);

        sample1();
    }

    private void sample1() {
        // 把参数都设置完后，动画会自动启动（按照所有参数并行运行动画）
        mTextView1.animate()
                .setDuration(2000)
                .translationX(Helper.dp2px(this, 200))
                .rotation(360)
                .setInterpolator(new BounceInterpolator());


        // 重复设置相同的参数，则以最后一次的设置为准
        mTextView2.animate()
                .setStartDelay(100)
                .setDuration(10)
                .translationX(10)
                .rotation(10);
        mTextView2.animate()
                .setDuration(2000)
                .translationX(Helper.dp2px(this, 200))
                .rotation(360)
                .setInterpolator(new BounceInterpolator());


        // 需要按顺序运行动画的话，则在播放结束的回调中再次使用 ViewPropertyAnimator 即可
        mTextView3.animate()
                .setDuration(2000)
                .translationX(Helper.dp2px(this, 200))
                .rotation(360)
                .setInterpolator(new BounceInterpolator())
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        // 动画开始
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // 动画结束后启动指定的其他动画
                        mTextView3.animate()
                                .setDuration(2000)
                                .translationXBy(Helper.dp2px(AnimationDemo7.this, -100));
                    }
                });


        // 拿到 ViewPropertyAnimator 所用的 ValueAnimator 对象，可以做一些额外的处理
        mTextView4.animate()
                .setDuration(2000)
                .translationX(Helper.dp2px(this, 200))
                .setListener(new Animator.AnimatorListener() {
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
                })
                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 通过设置 ValueAnimator 对象使动画循环运行
                        animation.setRepeatCount(-1);
                        animation.setRepeatMode(ValueAnimator.REVERSE);

                        // 可以从 ValueAnimator 对象获取动画的当前运行的时长，fraction 等数据
                        mTextView4.setText(String.format("%d, %.2f", animation.getCurrentPlayTime(), animation.getAnimatedFraction()));
                    }
                });
    }
}
