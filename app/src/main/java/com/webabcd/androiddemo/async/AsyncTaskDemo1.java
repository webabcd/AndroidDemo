/**
 * AsyncTask 的使用
 *
 * AsyncTask - 异步任务（用于简化“UI 线程启动一个后台线程，后台线程向 UI 线程通知进度并汇报结果”的场景）
 *     execute() - 执行
 *     cancel() - 取消
 *     getStatus() - 状态
 *         PENDING - AsyncTask 在调用 execute() 之前是 PENDING 状态
 *         RUNNING - AsyncTask 在调用 execute() 之后是 RUNNING 状态
 *         FINISHED - AsyncTask 在执行完成之后是 FINISHED 状态（注：在回调 onPostExecute() 或 onCancelled() 中仍然是 RUNNING 状态）
 *
 *     onPreExecute() - 任务执行前（UI 线程）
 *     doInBackground() - 执行任务（后台线程），接收“输入数据”，通知“进度通知”，返回“执行结果”
 *     onProgressUpdate() - 接收“进度通知”（UI 线程）
 *     onPostExecute() - 接收“执行结果”（UI 线程），如果调用了 AsyncTask 的 cancel() 则不会执行到这里
 *     onCancelled(boolean mayInterruptIfRunning) - 任务被取消（调用 AsyncTask 的 cancel() 后，等待后台线程执行完后才会回调）
 *         mayInterruptIfRunning 为 false 的方式：将 AsyncTask 的 isCancelled 状态置为 true，等任务执行完毕后回调 onCancelled() 方法（后台线程执行完后才会回调）
 *         mayInterruptIfRunning 为 true 的方式：将 AsyncTask 的 isCancelled 状态置为 true，调用线程的 interrupt() 方法，等任务执行完毕后回调 onCancelled() 方法（后台线程执行完后才会回调）
 *
 *
 * 注：
 * 1、只能在 UI 线程上实例化 AsyncTask 并调用其 execute() 方法
 * 2、该 cancel 的时候一定要 cancel（比如执行 AsyncTask 的 Activity 关闭的时候，记住一定要 cancel 掉 AsyncTask）
 * 3、取消线程时，如果线程中有 io 阻塞的话，先要把这个 io 干掉
 */

package com.webabcd.androiddemo.async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class AsyncTaskDemo1 extends AppCompatActivity {

    private Button _buttonExecute;
    private Button _buttonCancel1;
    private Button _buttonCancel2;
    private TextView _textView1;
    private TextView _textView2;
    private ProgressBar _progressBar1;
    private ProgressBar _progressBar2;

    private MyTask _myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_asynctaskdemo1);

        _buttonExecute = (Button) findViewById(R.id.buttonExecute);
        _buttonCancel1 = (Button) findViewById(R.id.buttonCancel1);
        _buttonCancel2 = (Button) findViewById(R.id.buttonCancel2);
        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        _progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        sample();
    }

    private void sample() {
        _buttonExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 实例化 AsyncTask（必须在 UI 线程）
                _myTask = new MyTask();

                // 执行 AsyncTask（必须在 UI 线程），同一个 AsyncTask 对象只能调用一次 execute()
                _myTask.execute("myTask arg1", "myTask arg2"); // 这个参数为“输入数据”，其数据类型是在 AsyncTask 的泛型参数中定义的
            }
        });

        _buttonCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消任务（mayInterruptIfRunning 为 false 的方式），将 AsyncTask 的 isCancelled 状态置为 true，等任务执行完毕后回调 onCancelled() 方法（后台线程执行完后才会回调）
                _myTask.cancel(false);
            }
        });

        _buttonCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消任务（mayInterruptIfRunning 为 true 的方式），将 AsyncTask 的 isCancelled 状态置为 true，调用线程的 interrupt() 方法，等任务执行完毕后回调 onCancelled() 方法（后台线程执行完后才会回调）
                _myTask.cancel(true);
            }
        });
    }

    /**
     * 继承 AsyncTask 并实现相关逻辑
     * AsyncTask 有 3 个泛型参数，分别是“输入数据”，“进度通知”，“执行结果”，按需要指定其数据类型即可，如果不需要则用 java.lang.Void
     */
    private class MyTask extends AsyncTask<String, Integer, String> {

        // onPreExecute() - 任务执行前（UI 线程）
        @Override
        protected void onPreExecute() {
            _textView1.setText("异步任务准备执行");
        }

        // doInBackground() - 执行任务（后台线程），接收“输入数据”，通知“进度通知”，返回“执行结果”
        @Override
        protected String doInBackground(String... params) {
            // 接收“输入数据”
            writeMessage(String.format("异步任务开始执行：%s, %s", params[0], params[1]));

            try {
                int i = 0;
                int j = 0;
                while (i < 100 || j < 100) {
                    Thread.sleep(10);

                    // 判断是否调用了 AsyncTask 的 cancel() 方法，如果被 cancel 了则需要像这样手动写退出线程的逻辑
                    if (isCancelled()) {
                        break;
                    }

                    i++;
                    if (i == 100) {
                        j += 10;
                        if (j == 100) {
                            break;
                        } else {
                            i = 0;
                        }
                    }

                    // 通知“进度通知”
                    publishProgress(i, j);
                }
            } catch (InterruptedException e) {
                writeMessage(e.toString());
            }

            // 返回“执行结果”
            return "ok";
        }

        // onProgressUpdate() - 接收“进度通知”（UI 线程）
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            _progressBar1.setProgress(progresses[0]);
            _progressBar2.setProgress(progresses[1]);
            _textView1.setText(String.format("子任务进度：%d%%，总任务进度：%d%%", progresses[0], progresses[1]));
        }

        // onPostExecute() - 接收“执行结果”（UI 线程），如果调用了 AsyncTask 的 cancel() 则不会执行到这里
        @Override
        protected void onPostExecute(String result) {
            _textView1.setText("异步任务执行完毕: " + result);
            _progressBar1.setProgress(100);
            _progressBar2.setProgress(100);
        }

        // onCancelled() - 任务被取消（调用 AsyncTask 的 cancel() 后，等待后台线程执行完后才会回调）
        @Override
        protected void onCancelled() {
            _textView1.setText("异步任务已经取消");
            _progressBar1.setProgress(0);
            _progressBar2.setProgress(0);
        }
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _textView2.append(String.format("%s\n", message));
            }
        });
    }
}
