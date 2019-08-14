/**
 * Future, FutureTask 的关闭和异常处理（具体的异常捕获和处理的说明详见下面的示例代码）
 *     boolean cancel(boolean mayInterruptIfRunning) - 取消 Future 异步任务，成功地调用 cancel() 则返回 true（如果异步任务的状态是已取消或者已中断，则返回 false）
 *         mayInterruptIfRunning 为 false 的方式：只会设置异步任务的状态为取消状态
 *         mayInterruptIfRunning 为 true 的方式：调用异步任务线程的 interrupt() 方法，同时设置异步任务的状态为取消状态
 *     isCancelled() - 异步任务是否是取消状态，被取消或者被中断则此值为 true
 *     isDone() - 异步任务是否是完成状态，正常完成或者发生异常或者被取消或者被中断则此值为 true
 *
 *
 * 注： 因为 FutureTask 实现了 Runnable 接口，所以可以通过 Thread 来执行
 */

package com.webabcd.androiddemo.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskDemo2 extends AppCompatActivity {

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;

    private FutureTask<String> mTask;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_futuretaskdemo2);

        _textView1 = (TextView) findViewById(R.id.textView1);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);
        _button3 = (Button) findViewById(R.id.button3);

        sample();
    }

    private void sample() {
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动一个后台线程，用于演示 Future, FutureTask 的关闭和异常处理
                mThread = new Thread(new MyRunnable());
                mThread.setDaemon(true);
                mThread.start();
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTask != null) {
                    // 如果异步任务的状态是已取消或者已中断，则不做任何处理，并返回 false；反之则会调用异步任务线程的 interrupt() 方法，同时设置异步任务的状态为取消状态，并返回 true
                    boolean cancelResult = mTask.cancel(true);
                    writeMessage("是否成功地调用了 Future 的 cancel() 方法：" + cancelResult);
                }
            }
        });

        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThread != null) {
                    // 在 Future 的 get() 阻塞过程中，如果调用了阻塞所在线程的 interrupt() 方法，则会捕获到 InterruptedException 异常
                    mThread.interrupt();
                    writeMessage("调用 Future 的 get() 阻塞所在线程的 interrupt() 方法");
                }
            }
        });
    }
    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            mTask = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    try {
                        // 如果调用 Future 的 cancel() 时指定了 mayInterruptIfRunning 为 true 则这里会抛出 InterruptedException 异常并设置任务状态，反之则只设置任务状态
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        writeMessage("异步任务被 Interrupted 了");
                    }

                    throw new Exception("执行异步任务时发生异常");
                }
            });
            Thread thread = new Thread(mTask);
            thread.setDaemon(true);
            thread.start();

            try {
                String result = mTask.get(10 * 1000, TimeUnit.MILLISECONDS);
                writeMessage("result " + result);
            } catch (InterruptedException e) {
                writeMessage("当前线程（阻塞所在线程，也就是调用异步任务的线程，而不是异步任务线程本身）被 Interrupted 了");
            } catch (ExecutionException e) {
                writeMessage("当 Callable 的 call() 抛出 Exception 异常时，则会捕获到 ExecutionException 异常");
            } catch (TimeoutException e) {
                // 本例默认不会执行到这里，如需要查看效果则将 get() 超时设置小一点即可
                writeMessage("到了指定的超时时间，仍未收到异步任务的返回结果，则会捕获到 TimeoutException 异常");
                // 如异步任务不再需要，请在这里取消这个超时的异步任务
                mTask.cancel(true);
            } catch (CancellationException e) {
                writeMessage("成功地调用了 Future 的 cancel() 方法后，则会捕获到 CancellationException 异常");
            }

            // isCancelled() - 被取消或者被中断则此值为 true
            // isDone() - 正常完成或者发生异常或者被取消或者被中断则此值为 true
            writeMessage(String.format("isDone:%b, isCancelled:%b", mTask.isDone(), mTask.isCancelled()));
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
