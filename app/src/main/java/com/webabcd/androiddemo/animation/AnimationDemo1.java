/**
 * 视图动画（View Animation）基础
 * 视图动画只是改变了 View 的视觉效果，而并没有改变 View 的属性（比如 left, top, right, bottom 之类的都是不变的）
 * View Animation（视图动画）即 Tween Animation（补间动画）
 * 本例中定义动画的 xml 请参见 res/anim/set_animationdemo1.xml
 *
 *
 * AnimationSet - 动画集合，用于定义一组动画。继承自 Animation
 *     addAnimation(Animation a) - 添加动画
 * TranslateAnimation, ScaleAnimation, AlphaAnimation, RotateAnimation - 位移动画，缩放动画，不透明度动画，旋转动画。均继承自 Animation
 *     在构造函数中指定动画的参数，不同动画的不同参数的解释看 res/anim/set_animationdemo1.xml 中的说明吧
 * Animation - 动画（在 xml 定义的各种视图动画均需要放在 res/anim 目录下）
 *     setDuration(), setStartOffset(), setFillAfter(), setFillBefore(), setRepeatCount(), setRepeatMode() - 具体解释看 res/anim/set_animationdemo1.xml 中的说明吧
 *     setAnimationListener() - 设置动画监听器
 *         onAnimationStart - 动画开始
 *         onAnimationEnd - 动画结束
 *         onAnimationRepeat - 动画重复
 * AnimationUtils.loadAnimation(Context context, int id) - 获取 xml 中定义的 Animation 对象
 *
 *
 * View - 视图
 *     startAnimation(Animation animation) - 开始动画
 *     clearAnimation() - 取消动画
 */

package com.webabcd.androiddemo.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class AnimationDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private TextView _textView2;
    private Button _button1;
    private Button _button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo1);

        _textView1 = findViewById(R.id.textView1);
        _textView2 = findViewById(R.id.textView2);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        // AnimationUtils.loadAnimation() - 从 xml 中加载 Animation
        final Animation animation = AnimationUtils.loadAnimation(AnimationDemo1.this, R.anim.set_animationdemo1);

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始动画
                _textView1.startAnimation(animation);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消动画
                _textView1.clearAnimation();
            }
        });

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始
                _textView2.append("onAnimationStart\n");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束
                _textView2.append("onAnimationEnd\n");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复
                _textView2.append("onAnimationRepeat\n");
            }
        });
    }
}