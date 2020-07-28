/**
 * Callable, Future, FutureTask 的使用（用于异步执行任务，然后同步获取结果）
 *     Callable<V> - 接口，与 Runnable 类似，不同之处在于 Callable<V> 接口的 V call() 方法是有返回值的
 *     Future - 与 Callable 和 ThreadPool 结合使用，实现异步执行任务，然后同步获取结果
 *         V get() - 阻塞，直至异步任务返回结果
 *         V get(long timeout, TimeUnit unit) - 阻塞，直至异步任务返回结果，如果到了指定时间仍未返回结果则抛出 TimeoutException 异常
 *     FutureTask - 与 Callable 和 ThreadPool 或 Thread 结合使用，实现异步执行任务，然后同步获取结果（FutureTask 实现了 Future 接口和 Runnable 接口）
 *         V get() - 阻塞，直至异步任务返回结果
 *         V get(long timeout, TimeUnit unit) - 阻塞，直至异步任务返回结果，如果到了指定时间仍未返回结果则抛出 TimeoutException 异常
 *
 *
 * 注： 因为 FutureTask 实现了 Runnable 接口，所以可以通过 Thread 来执行
 */

package com.webabcd.androiddemo.async;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_futuretaskdemo1);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);

        // Callable + Future + ThreadPool 的使用（同时简要说明各种类型的异常）
        sample1();

        // Callable + FutureTask + ThreadPool 的使用
        sample2();

        // Callable + FutureTask + Thread 的使用
        sample3();
    }

    // Callable + Future + ThreadPool 的使用（同时简要说明各种类型的异常）
    private void sample1() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newCachedThreadPool();
                // 实例化自定义 Callable 对象
                MyCallable myCallable = new MyCallable();
                // 通过 ExecutorService 的 Future<T> submit(Callable<T> task) 方法传入一个 Callable 对象，返回一个 Future 对象s
                Future<String> task = executor.submit(myCallable);
                executor.shutdown();

                try {
                    // 阻塞，并等待异步任务返回结果，如果到了指定的超时时间仍未返回结果则触发 TimeoutException 异常
                    String result = task.get(3000, TimeUnit.MILLISECONDS);
                    writeMessage("result " + result);
                } catch (InterruptedException e) {
                    // 参见 FutureTaskDemo2.java 中的说明
                } catch (ExecutionException e) {
                    // 参见 FutureTaskDemo2.java 中的说明
                } catch (TimeoutException e) {
                    // 参见 FutureTaskDemo2.java 中的说明
                } catch (CancellationException e) {
                    // 参见 FutureTaskDemo2.java 中的说明
                }
            }
        });
    }

    // Callable + FutureTask + ThreadPool 的使用
    private void sample2() {
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newCachedThreadPool();
                // 实例化自定义 Callable 对象
                MyCallable myCallable = new MyCallable();
                // 通过指定的 Callable 对象实例化 FutureTask 对象
                FutureTask<String> task = new FutureTask<String>(myCallable);
                // 因为 FutureTask 实现了 Runnable 接口，所以可以通过 execute() 方法添加到线程池
                executor.execute(task);
                executor.shutdown();

                try {
                    // 阻塞，并等待异步任务返回结果
                    String result = task.get();
                    writeMessage("result " + result);
                } catch (Exception e) {

                }
            }
        });
    }

    // Callable + FutureTask + Thread 的使用
    private void sample3() {
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化自定义 Callable 对象
                MyCallable myCallable = new MyCallable();
                // 通过指定的 Callable 对象实例化 FutureTask 对象
                FutureTask<String> task = new FutureTask<String>(myCallable);
                // 因为 FutureTask 实现了 Runnable 接口，所以可以通过 Thread 来执行
                Thread thread = new Thread(task);
                thread.start();

                try {
                    // 阻塞，并等待异步任务返回结果
                    String result = task.get();
                    writeMessage("result " + result);
                } catch (Exception e) {

                }
            }
        });
    }

    // 实现 Callable<V> 接口，其中指定的泛型为返回值类型
    class MyCallable implements Callable<String> {
        // 此方法如果抛出 Exception 异常，那么在 Future 对象的 get() 方法中则会 catch 到 ExecutionException 异常
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "thread: " + Thread.currentThread().getId();
        }
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
