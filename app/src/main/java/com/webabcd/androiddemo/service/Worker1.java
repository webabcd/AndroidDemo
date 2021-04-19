/**
 * Worker - 具体的后台任务逻辑
 * 需要在项目的 build.gradle 中配置好 implementation "androidx.work:work-runtime:x.x.x"
 *
 * 注：后台任务管理请参见 /service/WorkerManagerDemo1.java
 */

package com.webabcd.androiddemo.service;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class Worker1 extends Worker {

    private final String LOG_TAG = "Worker1";

    public Worker1(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    // 执行任务
    @Override
    public Result doWork() {

        // 获取通过 WorkRequest 的 setInputData() 设置的数据
        Data inputData = getInputData();

        // 构造任务执行完成后返回给 WorkerManager 的数据
        Data outputData = new Data.Builder()
                .putString("output_param1", "output_value1")
                .putString("output_param2", "output_value2")
                .build();

        Log.d(LOG_TAG, String.format("Worker id:%s, input_param1:%s，input_param2:%s，threadId:%d",
                this.getId(), inputData.getString("input_param1"), inputData.getString("input_param2"), Thread.currentThread().getId()));

        // 告知 WorkerManager 此任务成功
        return Result.success(outputData);

        // 告知 WorkerManager 此任务失败
        // return Result.failure(outputData);

        // 告知 WorkerManager 此任务需要重试
        // return Result.retry();
    }
}