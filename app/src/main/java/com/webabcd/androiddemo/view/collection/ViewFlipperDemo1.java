/**
 * ViewFlipper - 页面翻页控件
 *
 * 本例介绍了如何让 ViewFlipper 自动翻页，以及如何通过手势操作翻页 ViewFlipper
 */

package com.webabcd.androiddemo.view.collection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

import com.webabcd.androiddemo.R;

public class ViewFlipperDemo1 extends AppCompatActivity {

    // 用于演示 ViewFlipper 的自动翻页
    private ViewFlipper mViewFlipper1;
    // 用于演示 ViewFlipper 的手势操作翻页
    private ViewFlipper mViewFlipper2;

    // 用于保存手指触摸屏幕时的 x 坐标
    private float mStartTouchX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection_viewflipperdemo1);

        mViewFlipper1 = findViewById(R.id.viewFlipper1);
        mViewFlipper2 = findViewById(R.id.viewFlipper2);

        sample();
    }

    private void sample() {
        /**
         * addView() - 为 ViewFlipper 添加一个子 view
         * setFlipInterval() - 翻页动画的间隔时间，单位：毫秒
         * setInAnimation() - 进入的动画
         * setOutAnimation() - 离开的动画
         * startFlipping() - 启动动画（默认是不会启动动画的）
         * stopFlipping() - 停止动画
         */
        mViewFlipper1.setFlipInterval(3000);
        mViewFlipper1.setInAnimation(this, R.anim.viewflipper_right_in);
        mViewFlipper1.setOutAnimation(this, R.anim.viewflipper_right_out);
        mViewFlipper1.startFlipping();
    }


    // 通过手势控制 ViewFlipper 的翻页
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 手指触摸时
            case MotionEvent.ACTION_DOWN: {
                // 保存手指触摸屏幕时的 x 坐标
                mStartTouchX = event.getX();
                break;
            }
            // 手指触摸后移动时
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            // 当手离开时
            case MotionEvent.ACTION_UP: {
                /**
                 * setInAnimation() - 进入的动画
                 * setOutAnimation() - 离开的动画
                 * showNext() - 翻页到 ViewFlipper 中的下一个 view
                 * showPrevious() - 翻页到 ViewFlipper 的上一个 view
                 */

                // 手指从右向左滑动（这里判断手指的横向移动距离要大于 100 才生效）
                if (mStartTouchX - event.getX() > 100) {
                    mViewFlipper2.setInAnimation(this, R.anim.viewflipper_right_in);
                    mViewFlipper2.setOutAnimation(this, R.anim.viewflipper_right_out);
                    mViewFlipper2.showNext();
                }
                // 手指从左向右滑动（这里判断手指的横向移动距离要大于 100 才生效）
                else if (event.getX() - mStartTouchX > 100) {
                    mViewFlipper2.setInAnimation(this, R.anim.viewflipper_left_in);
                    mViewFlipper2.setOutAnimation(this, R.anim.viewflipper_left_out);
                    mViewFlipper2.showPrevious();
                }

                break;
            }
        }

        return true;
    }
}
