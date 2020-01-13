/**
 * Gesture 添加手势（向手势库中添加自定义手势）
 *
 * 本例演示
 * 在绘制区绘制手势（支持多笔），然后可以在指定的 ImageView 控件中显示此手势的缩略图，并把此手势添加进手势库（以便后续识别）
 */

package com.webabcd.androiddemo.input;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.webabcd.androiddemo.R;

public class GestureDemo2 extends AppCompatActivity {

    private final String LOG_TAG = "GestureDemo2";

    private GestureOverlayView mGestureOverlayView;
    private ImageView mImageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_gesturedemo2);

        mGestureOverlayView = findViewById(R.id.gestureOverlayView);
        mImageView1 = findViewById(R.id.imageView1);

        sample();
    }

    private void sample() {
        // 手势识别完成（识别完成后将此手势添加进手势库）
        mGestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, final Gesture gesture) {
                // 手势识别完成
                Log.d(LOG_TAG, "onGesturePerformed");

                // toBitmap() - 将绘制的手势保存为一个位图对象
                Bitmap bitmap = gesture.toBitmap(100, 100, 10, 0xff0000ff);
                mImageView1.setImageBitmap(bitmap);

                /**
                 * GestureLibraries.fromFile() - 通过指定的路径或文件实例化手势库
                 * GestureLibraries.fromRawResource() - 通过指定的资源实例化手势库
                 *     addGesture() - 添加手势（需要指定手势名称和手势对象）
                 *     removeEntry() - 通过指定的手势名称删除手势
                 *     getGestureEntries() - 获取手势库中全部手势的名称列表
                 *     getGestures() - 获取手势库中指定名称的手势列表
                 *     save() - 将“添加/删除”后的结果保存进手势库
                 */
                GestureLibrary gestureLibrary = GestureLibraries.fromFile(getFilesDir() + "/myGestures/");
                gestureLibrary.addGesture("gesture1", gesture);
                gestureLibrary.save();
            }
        });

        // 手势绘制监听
        mGestureOverlayView.addOnGesturingListener(new GestureOverlayView.OnGesturingListener() {
            // 手势绘制开始
            // 如果只画一条直线是不会回调这里的，因为一条直线不算是手势（绘制时至少要达到一个弧线或一个折线之类的才算是手势，才会执行到这里）
            @Override
            public void onGesturingStarted(GestureOverlayView overlay) {
                Log.d(LOG_TAG, "onGesturingStarted");
            }

            // 手势绘制结束
            @Override
            public void onGesturingEnded(GestureOverlayView overlay) {
                Log.d(LOG_TAG, "onGesturingEnded");
            }
        });

        // 路径绘制监听
        mGestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            // 路径绘制开始时（就是手指按下时）
            @Override
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
                Log.d(LOG_TAG, "onGestureStarted");
            }

            // 路径绘制继续
            @Override
            public void onGesture(GestureOverlayView overlay, MotionEvent event) {
                Log.d(LOG_TAG, "onGesture");
            }

            // 路径绘制结束
            @Override
            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
                Log.d(LOG_TAG, "onGestureEnded");
            }

            @Override
            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
                Log.d(LOG_TAG, "onGestureCancelled");
            }
        });
    }
}
