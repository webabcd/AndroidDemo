/**
 * Lock（演示 Condition 的 await() signal() signalAll() 的使用）
 *
 * Lock 是一个接口，ReentrantLock 实现了这个接口
 *     newCondition() - 创建一个新的 Condition 对象
 *
 * Condition
 *     await() - 阻塞当前 Condition 的线程
 *     signal() - 当前 Condition 的某一个被 await() 的线程被唤醒（具体是哪个线程会被唤醒是由系统决定的，无法预知）
 *     signalAll() - 当前 Condition 的所有被 await() 的线程都会被唤醒
 *
 *
 *  注：关于 Object 的 wait() notify() notifyAll() 的说明，请参见“async.ThreadDemo2”
 *
 *
 *  本示例通过生产者/消费者模型来演示 await() signal() signalAll() 的使用
 *  关于本例中的生产者/消费者模型，说明如下：
 *  1、Pool 是仓库，最多保存 10 件商品
 *  2、Producer 是生产者（一共有 5 个），如果仓库中的商品小于 10 件的话，则找一个生产者生产 1 件商品放到仓库
 *  3、Consumer 是消费者（一共有 5 个），如果仓库中的商品大于 0 件的话，则找一个消费者从仓库拿走 1 件商品
 */

package com.webabcd.androiddemo.concurrent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo3 extends AppCompatActivity {

    private final String LOG_TAG = "LockDemo3";

    private Lock _lock = new ReentrantLock();
    private Condition _producerCondition = _lock.newCondition();
    private Condition _consumerCondition = _lock.newCondition();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrent_lockdemo3);

        sample();
    }

    private void sample() {
        Pool pool = new Pool();

        for (int i = 0; i < 5; i++) {
            Producer producer = new Producer(pool);
            producer.setName("producer" + i);
            producer.start();
        }

        for (int i = 0; i < 5; i++) {
            Consumer consumer = new Consumer(pool);
            consumer.setName("consumer" + i);
            consumer.start();
        }
    }

    // 仓库
    class Pool {
        private List<Integer> list = new LinkedList<Integer>();
        private int max = 10;
        private int serialNo = 0;

        public int add() {
            _lock.lock();
            try {
                String threadName = Thread.currentThread().getName();
                while (list.size() >= max) {
                    try {
                        // 阻塞当前 _producerCondition 的线程
                        _producerCondition.await();
                    } catch (Exception e) {

                    }
                }

                serialNo++;
                list.add(new Integer(serialNo));
                Log.d(LOG_TAG, String.format("%s, %d, %d", threadName, list.size(), serialNo));

                // 唤醒一个 _consumerCondition 的线程
                _consumerCondition.signal();

                return serialNo;
            } finally {
                _lock.unlock();
            }
        }

        public int remove() {
            _lock.lock();
            try {
                String threadName = Thread.currentThread().getName();
                while (list.size() == 0) {
                    try {
                        // 阻塞当前 _consumerCondition 的线程
                        _consumerCondition.await();
                    } catch (Exception e) {

                    }
                }

                int serialNo = list.remove(0);
                Log.d(LOG_TAG, String.format("%s, %d, %d", threadName, list.size(), serialNo));

                // 唤醒一个 _producerCondition 的线程
                _producerCondition.signal();

                return serialNo;
            } finally {
                _lock.unlock();
            }
        }
    }

    // 生产者
    class Producer extends Thread {
        private Pool pool;

        public Producer(Pool pool) {
            this.pool = pool;
        }

        public void run() {
            while (true) {
                int serialNo = pool.add();
            }
        }

    }

    // 消费者
    class Consumer extends Thread {
        private Pool pool;

        public Consumer(Pool pool) {
            this.pool = pool;
        }

        public void run() {
            while (true) {
                int serialNo = pool.remove();
            }
        }
    }
}
