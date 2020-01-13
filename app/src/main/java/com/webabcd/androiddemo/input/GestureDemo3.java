/**
 * Gesture 识别手势（遍历手势库中的手势，通过逐一比对来识别当前手势）
 *
 * 本例演示
 * 在绘制区绘制手势（支持多笔），然后与手势库中的手势进行比对，并返回匹配度较高的手势列表
 */

package com.webabcd.androiddemo.input;

import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;

public class GestureDemo3 extends AppCompatActivity {

    private final String LOG_TAG = "GestureDemo3";

    private GestureOverlayView mGestureOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_gesturedemo3);

        mGestureOverlayView = findViewById(R.id.gestureOverlayView);

        sample();
    }

    private void sample() {
        /**
         * GestureLibraries.fromFile() - 通过指定的路径或文件实例化手势库
         * GestureLibraries.fromRawResource() - 通过指定的资源实例化手势库
         *     boolean load() - 加载手势库（返回值代表加载成功还是失败）
         */
        final GestureLibrary gestureLibrary = GestureLibraries.fromFile(getFilesDir() + "/myGestures/");
        if (gestureLibrary.load()) {
            Toast.makeText(this, "手势库加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "手势库加载失败", Toast.LENGTH_SHORT).show();
        }

        // 手势识别完成
        mGestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, final Gesture gesture) {
                // 手势识别完成
                Log.d(LOG_TAG, "onGesturePerformed");

                /**
                 * GestureLibrary - 手势库
                 *     recognize() - 遍历手势库中的手势，返回匹配的全部手势
                 * Prediction - 匹配结果
                 *     name - 手势名称
                 *     score - 匹配度（0 - 10 之间）
                 */
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                ArrayList<String> result = new ArrayList<String>();
                for (Prediction prediction : predictions) {
                    if (prediction.score > 1.0) {
                        result.add(String.format("与手势“%s”的匹配度为：%f", prediction.name, prediction.score));
                    }
                }

                if (result.size() > 0) {
                    // 显示所有匹配度较高的手势
                    ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(GestureDemo3.this, android.R.layout.simple_dropdown_item_1line, result.toArray());
                    new AlertDialog.Builder(GestureDemo3.this).setAdapter(adapter,null).setPositiveButton("确定",null).show();
                } else {
                    // 找不到匹配的手势
                    Toast.makeText(GestureDemo3.this,"找不到匹配的手势",Toast.LENGTH_SHORT).show();
                }
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
