/**
 * Context 的与优化相关的知识点
 */

package com.webabcd.androiddemo.optimize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.webabcd.androiddemo.R;

import java.lang.ref.WeakReference;

public class Demo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_demo2);

        // getApplicationContext() 获取到的 context 是全局的
        // 这个 context 可以随便给别人持有，因为反正是全局的

        // Activity 是继承自 Context 的
        // 在 activity 中使用 this，或者在 view 中使用 getContext() 返回的就是当前 activity 的 context，当前 activity 销毁了这个 context 就销毁了
        // 这个 context 不要随便给别人持有，因为如果其他持有人不放手的话，这个 context 就销毁不了了
        // 如果要把 context 给别人持有，又希望当前 activity 销毁了，这个 context 也销毁，则可以通过 WeakReference 来实现，参见下面的例子
        sample();
    }

    public static WeakReference<Context> Context;
    private void sample() {
        Demo2.Context = new WeakReference(this);

        Thread thread = new Thread(new MyRunnable());
        thread.setName("thread_optimize_demo2");
        thread.setDaemon(true);
        thread.start();
    }
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                // 如果 activity 活着，那么 context 就有强引用，不会被清理
                // 退出 activity 后，这个 activity 就没有强引用了，此时 context 只有弱引用，那么在 GC 后，这个 context 就会被清理掉
                Log.i("optimize_Demo2", Demo2.Context.get() == null ? "context 被销毁了" : "context 还活着呢");

                if (Demo2.Context.get() != null) {
                    SystemClock.sleep(1000);
                } else {
                    break;
                }
            }
        }
    }
}