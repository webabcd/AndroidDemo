/**
 * 帧动画（Drawable Animation）
 * Drawable Animation（帧动画）即 Frame Animation（逐帧动画）
 *
 *
 * AnimationDrawable - 帧动画，继承自 Drawable（在 xml 定义的各种帧动画均需要放在 res/drawable 目录下）
 *     start(), stop() - 启动动画，停止动画
 *     setOneShot(boolean oneShot) - 帧动画是否只播放一次（false 代表循环播放）
 *     addFrame(Drawable frame, int duration) - 添加帧（需要指定帧图片和显示时长，显示时长的单位是毫秒）
 *     isRunning() - 帧动画是否在运行中
 */

package com.webabcd.androiddemo.animation;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

public class AnimationDemo4 extends AppCompatActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_animationdemo4);

        mImageView1 = findViewById(R.id.imageView1);
        mImageView2 = findViewById(R.id.imageView2);
        mImageView3 = findViewById(R.id.imageView3);

        sample1();
        sample2();
        sample3();
    }

    // 启动一个帧动画（帧动画定义在 xml 中，且在 xml 中绑定到 ImageView）
    private void sample1() {
        // 获取 ImageView 上绑定的帧动画
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageView1.getBackground();
        // 如果帧动画未运行，则启动帧动画
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    // 启动一个帧动画（帧动画定义在 xml 中，然后在 java 中绑定到 ImageView）
    private void sample2() {
        // 获取帧动画
        AnimationDrawable animationDrawable = (AnimationDrawable) this.getDrawable(R.drawable.animationlist_animationdemo4);
        // 绑定帧动画
        mImageView2.setBackground(animationDrawable);
        // 启动帧动画
        animationDrawable.start();
    }

    // 启动一个帧动画（帧动画定义在 java 中，且在 java 中绑定到 ImageView）
    private void sample3() {
        // 定义一个帧动画
        AnimationDrawable animationDrawable = new AnimationDrawable();
        int[] ids = {R.drawable.img_sample_son, R.drawable.img_dialog_original, R.drawable.img_thread_state};
        for (int i = 0; i < 3; i++) {
            Drawable frame = Helper.id2drawable(this, ids[i]);
            animationDrawable.addFrame(frame, (i + 1) * 100);
        }
        animationDrawable.setOneShot(false);

        // 绑定帧动画
        mImageView3.setBackground(animationDrawable);
        // 启动帧动画
        animationDrawable.start();
    }
}
