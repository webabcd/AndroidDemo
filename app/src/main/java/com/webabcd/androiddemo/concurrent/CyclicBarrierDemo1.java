/**
 * CyclicBarrier - 屏障（当所有参与者都到达屏障后，屏障解除）
 *     CyclicBarrier(int parties, Runnable barrierAction) - 实例化 CyclicBarrier，指定参与者的数量，以及屏障解除后需要执行的 Runnable barrierAction
 *     int await(), int await(long timeout, TimeUnit unit) - 此参与者到达屏障后，阻塞并等待所有参与者都到达屏障。返回值为此参与者到达屏障时的排名索引
 *     getParties() - 获取参与者的数量
 *     reset() - 重置屏障
 *     isBroken() - 屏障是否是 broken 状态（比如有的参与者线程被 interrupted 了或者屏障被 reset 了，那么屏障的状态就会被置为 broken 状态）
 */

package com.webabcd.androiddemo.concurrent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierDemo1 extends AppCompatActivity {

    private TextView _textView1;

    private CyclicBarrier _cyclicBarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_cyclicbarrierdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        final Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeMessage(String.format("%s ready", Thread.currentThread().getName()));
                    SystemClock.sleep(random.nextInt(3000));

                    try {
                        // 此参与者已经到达屏障了，阻塞并等待所有参与者都到达屏障
                        // 返回值为当前参与者到达屏障时的排名索引
                        int arrivalIndex = _cyclicBarrier.await(5000, TimeUnit.MILLISECONDS);
                        writeMessage(String.format("%s completed（arrivalIndex:%d）", Thread.currentThread().getName(), arrivalIndex));
                    } catch (InterruptedException e) {
                        // 在阻塞过程中，如果此线程被 interrupted 了则抛出此异常
                    } catch (BrokenBarrierException e) {
                        // 在阻塞过程中，如果其他线程被 interrupted 了或者 CyclicBarrier 被 reset 了则抛出此异常
                    } catch (TimeoutException e) {
                        // 在阻塞过程中，如果超时了则抛出此异常
                    }
                }
            });
            thread.setName("thread" + i);
            thread.setDaemon(true);
            thread.start();
        }

        // 指定一共有 5 个参与者，屏障解除后在第一个到达屏障的参与者所在线程执行指定的 Runnable
        _cyclicBarrier  = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                writeMessage(String.format("所有参与者都到达屏障了，在第一个到达屏障的参与者所在线程执此 Runnable（%s）", Thread.currentThread().getName()));
            }
        });
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView1.append(String.format("%s\n", message));
            }
        });
    }
}
