/**
 * 通过 Thread 演示 Object 的 wait() notify() notifyAll() 的使用
 *
 * 在多线程场景下，对 Object 加锁
 * 1、调用 Object 的 wait() 后，会阻塞当前线程
 * 2、调用 Object 的 notify() 后，多线程中的某一个被 wait() 阻塞的线程会被唤醒（具体是哪个线程会被唤醒是由系统决定的，无法预知）
 * 3、调用 Object 的 notifyAll() 后，多线程中的所有被 wait() 阻塞的线程都会被唤醒
 *
 * 本示例通过生产者/消费者模型来演示 wait() notify() notifyAll() 的使用，同时演示 notify() 造成的死锁
 * 关于本例中的生产者/消费者模型，说明如下：
 * 1、Pool 是仓库，最多保存 1 件商品
 * 2、Producer 是生产者，如果仓库中没有商品的话，则每次生产 1 件商品放到仓库
 * 3、Consumer 是消费者，如果仓库中有商品的话，则每次从仓库拿走 1 件商品
 *
 * 运行示例后某一次的运行结果如下
 * p1 serialNo: 1
 * p1 notify
 * p1 wait
 * c1 serialNo: 1
 * c1 notify
 * c1 wait
 * p1 wait end
 * p1 serialNo: 2
 * p1 notify
 * p1 wait
 * c1 wait end
 * c1 serialNo: 2
 * c1 notify
 * c1 wait
 * c2 wait
 * p1 wait end
 * p1 serialNo: 3   // p1 生产了商品 3
 * p1 notify        // p1 通知可唤醒其他某一个线程
 * p1 wait          // p1 等待
 * c1 wait end      // c1 等待结束
 * c1 serialNo: 3   // c1 消费了商品 3
 * c1 notify        // c1 通知可唤醒其他某一个线程
 * c1 wait          // c1 等待
 * c2 wait end      // c2 等待结束
 * c2 wait          // c2 等待（此时 p1 c1 c2 都在等待，死锁了。如需避免此种情况，则将 notify() 改为 notifyAll() 即可）
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.webabcd.androiddemo.R;

import java.util.LinkedList;
import java.util.List;

public class ThreadDemo2 extends AppCompatActivity {

    private final String LOG_TAG = "ThreadDemo2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_threaddemo2);

        sample();
    }

    private void sample() {
        Pool pool = new Pool();

        Producer p1 = new Producer(pool);
        p1.setName("p1");
        p1.start();

        Consumer c1 = new Consumer(pool);
        c1.setName("c1");
        c1.start();

        Consumer c2 = new Consumer(pool);
        c2.setName("c2");
        c2.start();
    }

    // 仓库
    class Pool {
        private List<Integer> list = new LinkedList<Integer>();
        private int max = 1;
        private int serialNo = 0;

        public int add(){
            synchronized (this){
                String threadName = Thread.currentThread().getName();
                while (list.size() >= max){
                    try{
                        Log.d(LOG_TAG, threadName + " wait");
                        this.wait(); // 指定超时时间
                        Log.d(LOG_TAG, threadName + " wait end");
                    }
                    catch (Exception e){
                        Log.d(LOG_TAG, e.toString());
                    }
                }

                serialNo++;
                list.add(new Integer(serialNo));
                Log.d(LOG_TAG, threadName + " serialNo: " + String.valueOf(serialNo));

                Log.d(LOG_TAG, threadName + " notify");
                this.notify();

                return serialNo;
            }
        }

        public int remove(){
            synchronized (this){
                String threadName = Thread.currentThread().getName();
                while (list.size() == 0){
                    try{
                        Log.d(LOG_TAG, threadName + " wait");
                        this.wait();
                        Log.d(LOG_TAG, threadName + " wait end");
                    }
                    catch (Exception e){
                        Log.d(LOG_TAG, e.toString());
                    }
                }
                int serialNo = list.remove(0);
                Log.d(LOG_TAG, threadName + " serialNo: " + String.valueOf(serialNo));

                Log.d(LOG_TAG, threadName + " notify");
                this.notify();

                return serialNo;
            }
        }
    }

    // 生产者
    class Producer extends Thread{
        private Pool pool;
        public Producer(Pool pool){
            this.pool = pool;
        }
        public void run(){
            while (true){
                int serialNo = pool.add();
            }
        }

    }

    // 消费者
    class Consumer extends Thread{
        private Pool pool;
        public Consumer(Pool pool){
            this.pool = pool;
        }
        public void run(){
            while (true){
                int serialNo = pool.remove();
            }
        }
    }
}
