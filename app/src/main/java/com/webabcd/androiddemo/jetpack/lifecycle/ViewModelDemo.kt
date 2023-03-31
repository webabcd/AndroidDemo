/**
 * ViewModel
 *   注重结合生命周期管理数据，发生 configuration change（比如横竖屏切换等）时不会销毁数据（关于 configuration change 请参见 /ui/ConfigurationChangeDemo1.java）
 *   当不需要 ViewModel 时，比如 activity 将要销毁时，此时绑定到此 activity 的 ViewModel 会自动调用自己的 onCleared() 方法，然后销毁自己
 */

package com.webabcd.androiddemo.jetpack.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.webabcd.androiddemo.R
import com.webabcd.androiddemo.databinding.ActivityJetpackLifecycleDatabindingdemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelDemo : AppCompatActivity() {

    private lateinit var mBinding: ActivityJetpackLifecycleDatabindingdemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack_lifecycle_viewmodeldemo)

        // 引用 implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0" 后
        // 就可以使用下面这种简单的方式创建创建一个指定的 ViewModel 对象，并绑定到指定的 activity
        // 否则的话需要自己写 ViewModelProvider.Factory，那样会比较麻烦
        val viewModel = ViewModelProvider(this)[CustomViewModel::class.java]
        mBinding.textView1.text = viewModel.name

        mBinding.button1.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("lifecycle", "Activity onDestroy()")
    }
}

// 自定义 ViewModel
class CustomViewModel: ViewModel() {

    var name = "webabcd"

    fun test() {
        /**
         * 引用 implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0" 后
         * ViewModel 会有一个扩展属性 viewModelScope，其可以将协程绑定到 ViewModel 生命周期，即 ViewModel 销毁时，协程也会自动被取消
         */
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
        viewModelScope.launch(Dispatchers.IO){

        }
    }

    // 当不需要 ViewModel 时，比如 activity 将要销毁时，此时绑定到此 activity 的 ViewModel 会自动调用自己的 onCleared() 方法，然后销毁自己
    // 如果需要的话，你可以在这里清理相关资源
    override fun onCleared() {
        super.onCleared()

        Log.d("lifecycle", "ViewModel onCleared()")
    }
}