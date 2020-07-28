/**
 * volatile 与原子性, 可见性, 有序性
 *
 * 1、原子性：volatile 无法保证原子性
 * 2、可见性：volatile 可以保证可见性
 * 3、有序性：volatile 可以保证有序性
 */

package com.webabcd.androiddemo.concurrent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.webabcd.androiddemo.R;

public class VolatileDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_volatiledemo1);
    }



    // 可见性说明
    private boolean _status = false;
    public void setStatus() {
        _status = true;
    }
    public void execTask() {
        if (_status) {
            /**
             * 先自行了解现代计算机的内存模型和 Java 内存模型
             * 在多线程场景下，线程 A 执行了 setStatus() 后，然后线程 B 执行了 execTask()，那么能保证会执行到这里吗？答案是不能保证
             * 因为线程 A 执行了 setStatus() 后，然后线程 B 在执行 execTask() 时，如果线程 A 修改后的 _status 值还没有同步到主内存，则线程 B 获取到的 _status 值仍为 false
             * 这就是说共享变量 _status 不能保证可见性
             * 如果本例用 volatile 修饰 _status 的话，则可以保证共享变量 _status 的可见性，也就是说线程 A 执行了 setStatus() 后，然后线程 B 在执行 execTask() 时，拿到的 _status 值肯定为 true
             */
        }
    }



    // 有序性说明
    private void sample() {
        /**
         * 在不影响程序逻辑的前提下，为了优化程序性能，编译器和处理器可能会对指令顺序重新排序
         * 比如下面的例子，这里的 5 个语句的实际的执行顺序是不一定的
         * 如果本例用 volatile 修饰变量 c 的话，那么语句 1 和语句 2 的执行顺序不定，语句 4 和语句 5 的执行顺序不定，但是语句 3 一定是第 3 个执行的
         * 也就是说 volatile 可以保证有序性
         */

        int a = 1; // 语句 1
        int b = 2; // 语句 2
        int c = 3; // 语句 3
        int d = 4; // 语句 4
        int e = 5; // 语句 5
    }
}
