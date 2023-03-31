/**
 * LiveData
 * 本例用于演示如何通过 LiveData 实现数据变化的通知与接收，以及 LiveData 如何结合 ViewModel 使用
 *
 * LiveData
 *   当数据发生变化时，LiveData 会通知 Observer
 *   LiveData 会根据 Observer 绑定的 LifecycleOwner 的生命周期情况，来决定是否将数据的变化通知给 Observer（比如 activity 不在前台就不通知了）
 *   activity 不在前台就不通知，当 activity 返回前台时会先通知一次之前没能通知的最近一次的通知，然后继续正常的通知
 * ViewModel
 *   注重结合生命周期管理数据，发生 configuration change（比如横竖屏切换等）时不会销毁数据（关于 configuration change 请参见 /ui/ConfigurationChangeDemo1.java）
 *   当不需要 ViewModel 时，比如 activity 将要销毁时，此时绑定到此 activity 的 ViewModel 会自动调用自己的 onCleared() 方法，然后销毁自己
 * 官方建议 LiveData 和 ViewModel 配合使用，在 ViewModel 中使用 LiveData，这样当 ViewModel 销毁的时候，LiveData 就跟着销毁了
 *
 *
 * LiveData - 抽象类，其 postValue() 和 setValue() 是 protected 的
 * MutableLiveData - 继承自 LiveData，其 postValue() 和 setValue() 是 public 的
 *   setValue() - 更新 LiveData 的值，只能在主线程调用，新的值会通知给观察者
 *   postValue() - 更新 LiveData 的值，允许在非主线程调用，其内部会切换到主线程并调用 setValue()，新的值会通知给观察者
 *     注：在主线程接收数据之前，如果多次调用 postValue() 则主线程只会收到最后一次更新的数据的通知
 *   getValue() - 获取 LiveData 的值
 *   observe() - 在指定的 LifecycleOwner 中添加观察者
 *   removeObserver()/removeObservers() - 移除观察者
 *   observeForever() - 添加观察者，但是不绑定任何 LifecycleOwner（也就是说数据变化总是会通知给观察者，即使当前 activity 不在前台）
 *   hasObservers() - 是否有观察者
 *   hasActiveObservers() - 是否有活动观察者（即绑定的 LifecycleOwner 在前台，则观察者就是活动的）
 */

package com.webabcd.androiddemo.jetpack.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webabcd.androiddemo.R
import com.webabcd.androiddemo.databinding.ActivityJetpackLifecycleDatabindingdemoBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class LiveDataDemo : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel

    private lateinit var mBinding: ActivityJetpackLifecycleDatabindingdemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack_lifecycle_livedatademo)

        // 创建一个指定的 ViewModel 对象，并绑定到指定的 activity
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        // 为 ViewModel 中的 LiveData 指定观察者，并将这个观察者绑定到当前的 activity
        viewModel.myName.observe(this) {
            // 调用 LiveData 的 postValue() 或 setValue() 后，观察者就会收到通知
            mBinding.textView1.text = it

            // 正常来说，这里每秒会收到一个通知，但是如果 activity 走到后台了，这里就收不到了
            // 当 activity 重新变回前台时，则会先收到之前没收到的最近一次的通知，之后会继续每秒都收到通知
            Log.d("lifecycle", it)
        }

        // 每秒更新一次 LiveData 的值
        CoroutineScope(Dispatchers.Default).launch {
            repeat(1000) {
                delay(1000)
                val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
                val time = dateFormat.format(Date())
                viewModel.setMyName("$time")
            }
        }

        // 跳转到其他 activity 然后再返回来，观察一下这个过程中的效果
        mBinding.button1.setOnClickListener {
            startActivity(Intent(this@LiveDataDemo, LifecycleDemo::class.java))
        }
    }
}

// 自定义 ViewModel
class MyViewModel: ViewModel() {

    private val _myName = MutableLiveData<String>()

    val myName: LiveData<String>
        get() = _myName

    init {
        _myName.value = "webabcd"
    }

    fun setMyName(name: String) {
        _myName.postValue(name)

        // 只能在主线程调用 setValue()
        // _myName.value = name
    }

    // 当不需要 ViewModel 时，比如 activity 将要销毁时，此时绑定到此 activity 的 ViewModel 会自动调用自己的 onCleared() 方法，然后销毁自己
    // 如果需要的话，你可以在这里清理相关资源
    override fun onCleared() {
        super.onCleared()
    }
}