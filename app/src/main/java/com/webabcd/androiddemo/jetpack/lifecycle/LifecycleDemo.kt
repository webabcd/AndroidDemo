/**
 * lifecycle - 生命周期
 *
 * lifecycle 的主要作用是，让其他组件可以监听 Activity/Fragment 的生命周期，其好处如下
 * 1、以前 Activity/Fragment 需要在不同的生命周期对一些组件做相应的操作，现在改为由组件自己处理，从而降低了耦合性
 * 2、避免一些组件持有 Activity/Fragment 后不释放导致的内存泄漏问题
 *
 * Lifecycle - 生命周期抽象类
 *   addObserver() - 增加一个指定的观察者
 *   removeObserver() - 删除指定的观察者
 *   getCurrentState() - 获取生命周期当前的状态（INITIALIZED, CREATED, STARTED, RESUMED, DESTROYED）
 *
 * LifecycleOwner - 生命周期的原始所有者，这是一个接口，实现这个接口就可以支持 Lifecycle
 *   getLifecycle() - 只有这一个方法，其会返回 Lifecycle 对象
 *   Activity, Fragment 都实现了 LifecycleOwner 接口
 *   Activity, Fragment 的 getLifecycle() 方法返回的是 LifecycleRegistry 对象（其继承自 Lifecycle）
 *   如果需要使用支持 Lifecycle 的 Service 请使用 LifecycleService（继承自 Service 并实现了 LifecycleOwner 接口）
 *
 * LifecycleRegistry - Activity, Fragment 等的 getLifecycle() 返回的对象，其继承自 Lifecycle
 *   初始化 LifecycleRegistry 时需要传入 LifecycleOwner 对象，LifecycleRegistry 会弱引用 LifecycleOwner（这样就可以避免持有 Activity/Fragment 后不释放导致的内存泄漏问题）
 *
 * LifecycleObserver - 生命周期观察者
 *   用于监听指定的 LifecycleOwner 的生命周期（ON_ANY, ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY）
 */

package com.webabcd.androiddemo.jetpack.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.android.synthetic.main.activity_jetpack_lifecycle_lifecycledemo.*
import com.webabcd.androiddemo.R

class LifecycleDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_lifecycle_lifecycledemo)

        // 实例化一个自定义的生命周期观察者（也就是说你准备用这个组件来监听 Activity 的生命周期）
        val myLifecycleObserver = MyLifecycleObserver()
        // 为 Lifecycle 增加一个指定的观察者
        this.lifecycle.addObserver(myLifecycleObserver) // 这个 this.lifecycle 就是 Activity 的 getLifecycle() 方法，其返回的是 LifecycleRegistry 对象（继承自 Lifecycle）
        button1.setOnClickListener {
            // 获取 Lifecycle 当前的状态
            textView1.text = "${this.lifecycle.currentState}"
        }

    }
}

// 继承 LifecycleObserver 实现一个自定义的生命周期观察者
class MyLifecycleObserver : LifecycleObserver {

    // 触发任何事件前都会先触发这个事件
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {
        Log.d("lifecycle", "ON_ANY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreat() {
        Log.d("lifecycle", "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d("lifecycle", "ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d("lifecycle", "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d("lifecycle", "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d("lifecycle", "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        Log.d("lifecycle", "ON_DESTROY")
    }
}