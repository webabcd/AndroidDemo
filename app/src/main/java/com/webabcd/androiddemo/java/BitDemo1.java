/**
 * 位操作
 */

package com.webabcd.androiddemo.java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

public class BitDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "BitDemo1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_bitdemo1);

        sample1();
    }

    // 或运算 |
    // 有一个为真则为真，比如 0101 | 0011 结果是 0111
    private void sample1() {
        // 合体
        Log.d(LOG_TAG, "1 | 2: " + (1 | 2)); // 结果是 3（1 和 2 合体为 3）
        Log.d(LOG_TAG, "1 | 4: " + (1 | 4)); // 结果是 5（1 和 4 合体为 5）
        Log.d(LOG_TAG, "2 | 4: " + (2 | 4)); // 结果是 6（2 和 4 合体为 6）

        // 分体
        Log.d(LOG_TAG, "6 & ~1: " + (6 & ~1)); // 结果是 6（从 6 中分出 1，但是 6 里面没有 1，所以结果还是 6）
        Log.d(LOG_TAG, "6 & ~2: " + (6 & ~2)); // 结果是 4（从 6 中分出 2，6 包含 2 和 4，所以结果为 4）
        Log.d(LOG_TAG, "6 & ~3: " + (6 & ~3)); // 结果是 4（从 6 中分出 3，6 包含 2 和 4，3 包含 1 和 2，所以会分出 2，所以结果为 4）
        Log.d(LOG_TAG, "6 & ~4: " + (6 & ~4)); // 结果是 2（从 6 中分出 4，6 包含 2 和 4，所以结果为 2）
        Log.d(LOG_TAG, "6 & ~5: " + (6 & ~5)); // 结果是 2（从 6 中分出 5，6 包含 2 和 4，5 包含 1 和 4，所以会分出 4，所以结果为 2）
        Log.d(LOG_TAG, "6 & ~6: " + (6 & ~6)); // 结果是 0（从 6 中分出自身，结果为 0）

        // 判断是否包含
        Log.d(LOG_TAG, "6 | 1: " + (6 | 1)); // 结果是 7（6 中不包含 1 所以结果不为自身）
        Log.d(LOG_TAG, "6 | 2: " + (6 | 2)); // 结果是 6（6 中包含 2 所以结果为自身）
        Log.d(LOG_TAG, "6 | 3: " + (6 | 3)); // 结果是 7（6 中不包含 3 所以结果不为自身）
        Log.d(LOG_TAG, "6 | 4: " + (6 | 4)); // 结果是 6（6 中包含 4 所以结果为自身）
        Log.d(LOG_TAG, "6 | 5: " + (6 | 5)); // 结果是 7（6 中不包含 5 所以结果不为自身）
        Log.d(LOG_TAG, "6 | 6: " + (6 | 6)); // 结果是 6（6 中包含自身，所以结果为自身）


    }
}