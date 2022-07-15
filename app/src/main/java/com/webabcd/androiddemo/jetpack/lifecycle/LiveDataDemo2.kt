/**
 * LiveData
 * 本例用于演示如何实现 LiveData 指定的对象的某个属性发生了变化时通知给观察者
 *
 * 关于 LiveData 指定的对象发生了变化时通知给观察者，以及 LiveData 的基础请参见 LiveDataDemo.kt
 */

package com.webabcd.androiddemo.jetpack.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_jetpack_lifecycle_livedatademo2.*
import java.text.SimpleDateFormat
import java.util.*

class LiveDataDemo2 : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_lifecycle_livedatademo2)

        viewModel = ViewModelProvider(this)[MyViewModel2::class.java]
        viewModel.myLiveData.observe(this) {
            // 当 MyLiveData 对象的 name 属性或 age 属性发生了变化时，这里都会收到通知
            textView1.text = "name:${it.name}, age:${it.age}"
        }

        button1.setOnClickListener {
            val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
            val time = dateFormat.format(Date());
            viewModel.myLiveData.name = "$time"
        }
    }
}

// 自定义 LiveData 用于实现指定的对象的某个属性发生了变化时通知给观察者
class MyLiveData: LiveData<MyLiveData>() {
    var name = "webabcd"
        set(value) {
            field = value
            // 这是关键点，通过 postValue(this) 通知
            postValue(this)
        }

    var age = 40
        set(value) {
            field = value
            // 这是关键点，通过 postValue(this) 通知
            postValue(this)
        }
}

class MyViewModel2: ViewModel() {

    private val _myLiveData = MyLiveData()

    val myLiveData: MyLiveData
        get() = _myLiveData
}
