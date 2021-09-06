/*
 * 自定义 Application 的示例
 */

package com.webabcd.androiddemo;

import android.app.Application;
import android.util.Log;

import com.webabcd.androiddemo.optimize.MyUncaughtExceptionHandler;

// 使用自定义 Application 的话，需要继承 Application 类，并在 AndroidManifest.xml 中做好类似配置 <application android:name=".MyApplication" />
// 使用时在 activity 中通过 (MyApplication)getApplication() 方法获取
public class MyApplication extends Application {

    private final String LOG_TAG = "MyApplication";

    // 简化自定义 Application 的获取方式，可以通过 MyApplication.getInstance() 获取了
    private static MyApplication mInstance = null;
    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化自定义的未处理异常处理器
        MyUncaughtExceptionHandler myUncaughtExceptionHandler = MyUncaughtExceptionHandler.getInstance();
        myUncaughtExceptionHandler.init(getApplicationContext());

        mInstance = this;
    }

    // 全局变量的示例（当然，要实现全局变量也可以不通过 Application 实现，在一个自定义类中使用静态属性来实现也是挺好的）
    private String mParam = "i am webabcd";
    public String getParam() {
        return mParam;
    }
    public void setParam(String param) {
        mParam = param;
    }

    // 在 application 里构造 thread
    // 如需要全局的 thread 请在 application 中创建，下面的例子用的是匿名内部类，他会隐式持有其外部类（对于本例来说就是 application）
    private Thread mThread = null;
    public void startThread() {
        stopThread();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Log.i(LOG_TAG, "application 中的 thread");

                        if (Thread.interrupted()) {
                            break;
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, "线程被 interrupted 了");
                }
            }
        });
        mThread.setName("thread_application");
        mThread.setDaemon(true);
        mThread.start();
    }
    public void stopThread() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
