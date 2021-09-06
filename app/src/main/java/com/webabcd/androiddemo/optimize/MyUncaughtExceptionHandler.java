/**
 * 用于捕获未处理异常的类（此类在 MyApplication.java 中初始化）
 */

package com.webabcd.androiddemo.optimize;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

// 通过实现 UncaughtExceptionHandler 接口来实现自定义的未处理异常处理器
public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private static MyUncaughtExceptionHandler instance = null;
    public synchronized static MyUncaughtExceptionHandler getInstance() {
        if (instance == null) {
            instance = new MyUncaughtExceptionHandler();
        }
        return instance;
    }

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private MyUncaughtExceptionHandler() {

    }

    public void init(Context context) {
        mContext = context;

        // 系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 指定 UncaughtException 处理器为我们的自定义处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    // 捕获未处理异常
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        // 打印未处理异常的相关信息
        Log.e("UncaughtException", getDeviceInfo(mContext) + getTraceInfo(ex));

        // 使用系统默认的 UncaughtException 处理器处理这个未处理异常（调用这个就可以崩溃了）
        mDefaultHandler.uncaughtException(thread, ex);
    }

    // 获取设备信息
    public static String getDeviceInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                sb.append(String.format(Locale.ENGLISH, "versionName:%s, versionCode:%d\n", pi.versionName, pi.versionCode));
            }
        } catch (Exception e) {
            sb.append("getPackageInfo error:" + e.toString());
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                sb.append(String.format(Locale.ENGLISH, "%s:%s\n", field.getName(), field.get(null).toString()));
            } catch (Exception e) {
                sb.append("getDeclaredFields error:" + e.toString());
            }
        }

        return sb.toString();
    }

    // 获取异常信息
    public static String getTraceInfo(Throwable e) {
        StringBuilder sb = new StringBuilder();
        Throwable ex = e.getCause() == null ? e : e.getCause();
        StackTraceElement[] stacks = ex.getStackTrace();
        for (int i = 0; i < stacks.length; i++) {
            sb.append(String.format(Locale.ENGLISH, "class:%s, method:%s, line:%s, exception:%s\n",
                    stacks[i].getClassName(), stacks[i].getMethodName(), stacks[i].getLineNumber(), ex.toString()));
        }

        return sb.toString();
    }
}