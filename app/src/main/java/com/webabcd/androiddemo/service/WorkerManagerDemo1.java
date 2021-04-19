/**
 * WorkerManager - 后台任务管理
 *     OneTimeWorkRequest - 一次性任务请求
 *     PeriodicWorkRequest - 周期性任务请求
 *
 * 注：具体的后台任务逻辑请参见 /service/Worker1.java
 */

package com.webabcd.androiddemo.service;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.M)
public class WorkerManagerDemo1 extends AppCompatActivity {

    private final String LOG_TAG = "WorkerManagerDemo1";

    private UUID mId;

    private Button mButton1;
    private Button mButton2;
    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_workermanagerdemo1);

        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mTextView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTask();
            }
        });
    }

    // 新增任务
    private void addTask() {
        // 构造 Constraints 对象，用于指定任务触发的约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false) // 是否需要处于充电状态
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // 是否需要处于网络连接状态
                .setRequiresBatteryNotLow(false) // 是否需要处于非低电量状态
                .setRequiresDeviceIdle(false) // 是否需要处于系统空闲状态（api level 23 或以上）
                .build();

        // 构造需要传递给 Worker 的数据
        Data inputData = new Data.Builder()
                .putString("input_param1", "input_value1")
                .putString("input_param2", "input_value2")
                .build();

        // OneTimeWorkRequest - 一次性任务请求（继承自 WorkRequest）
        // 需要指定自定义 Worker 类
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Worker1.class)
                // 设置任务的触发条件
                .setConstraints(constraints)
                // 指定需要传递给 Worker 的数据
                .setInputData(inputData)
                // 如果符合触发条件，则在 10 秒后执行任务（我这里测试，这个值最小是 5 秒）
                .setInitialDelay(10, TimeUnit.SECONDS)
                // Worker 发生异常或在 doWork() 中返回 Result.retry() 时的自定义重试策略
                // 下面的代码代表重试间隔为：重试次数 * 15 秒（我这里测试，这个值最小是 10 秒）
                .setBackoffCriteria(BackoffPolicy.LINEAR,15, TimeUnit.SECONDS)
                // 指定任务的标签（分组用，多个任务可以指定相同的标签），后续可以通过标签获取任务状态或取消任务
                .addTag("myTag")
                .build();

        // PeriodicWorkRequest - 周期性任务请求（继承自 WorkRequest）
        // 需要指定自定义 Worker 类
        // 执行间隔最小为 15 分钟
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(Worker1.class, 15, TimeUnit.MINUTES).build();

        // 将任务请求加入 WorkManager 队列
        // 也可以传入一个任务请求集合，他们会并行执行
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);


        // 如果有一系列任务需要按顺序执行，则可以通过 beginWith().then().then()...enqueue() 实现
        // 以下代码用于实现：先执行 A，执行完成后再执行 B，执行完成后再执行 C
        // WorkManager.getInstance(this).beginWith(WorkRequestA).then(WorkRequestB).then(WorkRequestC).enqueue();

        // 如果将任务请求集合传入 beginWith() 或 then() 则他们会并行执行
        // 以下代码用于实现：A 和 B 并行执行，当他们都执行完成后再执行 C
        // List<OneTimeWorkRequest> taskList = new ArrayList<>();
        // taskList.add(WorkRequestA);
        // taskList.add(WorkRequestB);
        // WorkManager.getInstance(this).beginWith(taskList).then(WorkRequestC).enqueue();

        // 关于任务顺序的更复杂的需求，可能会需要用到 combine() 方法
        // 以下代码用于实现：
        //   1、先执行 A，执行完成后再执行 B
        //   2、先执行 C，执行完成后再执行 D
        //   3、AB 和 CD 可以并行执行
        //   4、AB 和 CD 都执行完成后再执行 E
        /*
        WorkContinuation workContinuation1 =  WorkManager.getInstance(this).beginWith(WorkRequestA).then(WorkRequestB);
        WorkContinuation workContinuation2 =  WorkManager.getInstance(this).beginWith(WorkRequestC).then(WorkRequestD);
        List<WorkContinuation> taskList = new ArrayList<>();
        taskList.add(workContinuation1);
        taskList.add(workContinuation2);
        WorkContinuation.combine(taskList).then(WorkRequestE).enqueue();
        */


        // 通过 WorkRequest 的 getId() 可以获取到这个任务的标识，后续可以通过此标识获取该任务状态或取消该任务
        mId = oneTimeWorkRequest.getId();
        Log.d(LOG_TAG, String.format("add OneTimeWorkRequest, id:%s, threadId:%d", mId, Thread.currentThread().getId()));

        // 获取任务状态
        loadTaskStatus();
    }

    // 删除当前的任务
    private void removeTask() {
        // 根据 id 删除任务
        WorkManager.getInstance(this).cancelWorkById(mId);
        // 根据 tag 删除任务
        // WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        // 取消全部任务
        // WorkManager.getInstance(this).cancelAllWork();
    }

    // 获取任务状态
    private void loadTaskStatus() {
        try {
            // 根据 id 获取任务信息
            // List<WorkInfo> workInfoList = WorkManager.getInstance(this).getWorkInfoById(mId).get();
            // 根据 tag 获取任务信息
            // List<WorkInfo> workInfoList = WorkManager.getInstance(this).getWorkInfosByTag(tag).get();

            // 监控指定 tag 的任务的状态变化
            // WorkManager.getInstance(this).getWorkInfosByTagLiveData(tag).observe();
            // 监控指定 id 的任务的状态变化
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(mId).observe(WorkerManagerDemo1.this, new Observer<WorkInfo>()
            {
                @Override
                public void onChanged(WorkInfo workInfo)
                {
                    // 获取通过 Work 的 doWork() 的 Result.success() 或 Result.failure 设置的数据
                    Data outputData = workInfo.getOutputData();

                    // 任务状态（可能的值有：ENQUEUED, RUNNING, SUCCEEDED, FAILED, BLOCKED, CANCELLED）
                    WorkInfo.State state = workInfo.getState();

                    writeMessage(String.format(Locale.US, "Worker id:%s\noutput_param1:%s\noutput_param2:%s\nstate:%s\nthreadId:%d",
                            workInfo.getId(),
                            outputData.getString("output_param1"), outputData.getString("output_param2"),
                            state.name(),
                            Thread.currentThread().getId()));
                }
            });

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }

    private void writeMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView1.setText(message);
            }
        });
    }
}