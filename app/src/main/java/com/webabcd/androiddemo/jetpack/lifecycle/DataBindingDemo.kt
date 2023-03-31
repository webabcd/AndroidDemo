/**
 * DataBinding 就是 android 官方的 MVVM 模式开发框架
 *
 * 如需启用 DataBinding 请在 build.gradle 的 android {} 中做如下配置
 * dataBinding {
 *   enabled = true;
 * }
 * 如需使用 DataBinding 相关注解，则需要在 gradle 中配置 apply plugin: 'kotlin-kapt'
 * 注：DataBinding 需要 gradle 根据你的配置生成一些类，如果遇到问题就 Rebuild Project 重新生成一下
 */

package com.webabcd.androiddemo.jetpack.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.webabcd.androiddemo.MyApplication
import com.webabcd.androiddemo.databinding.ActivityJetpackLifecycleDatabindingdemoBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DataBindingDemo : AppCompatActivity() {

    // ActivityJetpackLifecycleDatabindingdemoBinding 是 gradle 自动生成的类，其对应的 xml 是 activity_jetpack_lifecycle_databindingdemo.xml
    private lateinit var mBinding: ActivityJetpackLifecycleDatabindingdemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置 activity 的布局，并获取绑定对象（方式一）
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack_lifecycle_databindingdemo)

        // 设置 activity 的布局，并获取绑定对象（方式二）
        mBinding = ActivityJetpackLifecycleDatabindingdemoBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val myModel = MyDataBindingModel()
        // 指定绑定对象的 lifecycleOwner（用于实现 lifecycleOwner 不在前台则不更新数据）
        mBinding.lifecycleOwner = this
        // 在 xml 的 data 中声明的变量这里都可以使用
        mBinding.buttonText = "我是一个按钮" // 这是一次绑定的方式，这种方式不支持单向绑定或双向绑定
        mBinding.myModel = myModel
        // 在 xml 中的控件，这里也可以通过其 id 使用
        mBinding.button1.setOnClickListener {}

        // 数据发生变化时的通知
        myModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.name) {
                    Log.d("lifecycle", "BR.name, ${myModel.name}")
                }
            }
        })

        lifecycleScope.launch {
            repeat(1000) {
                delay(1000)
                val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
                val time = dateFormat.format(Date())

                // 本例用于演示单向绑定
                myModel.name = "${time}"
            }
        }

        // 数据发生变化时的通知
        // 本例用于演示双向绑定，即除了数据层变化会通知 UI 更新数据外，UI 中的数据发生了变化也会通知数据层
        myModel.country.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.d("lifecycle", "country: ${myModel.country.get()}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding.unbind()
    }
}

// 自定义类如果想实现单向绑定或双向绑定，需要继承 BaseObservable 类
class MyDataBindingModel : BaseObservable() {

    // 这个 @get:Bindable 注解是必不可少的（如需使用 DataBinding 相关注解，则需要在 gradle 中配置 apply plugin: 'kotlin-kapt'）
    @get:Bindable
    var name: String = "123456789"
        set(value){
            field = value
            // BR 是由 gradle 生成的，这里能用 BR.name 是因为 name 属性添加了 @get:Bindable 注解
            // notifyPropertyChanged() - 更新 UI 上的此对象指定字段
            // notifyChange() - 更新 UI 上的此对象的所有字段
            notifyPropertyChanged(BR.name)
        }

    // 通过如下几个类可以实现指定类型数据的单向绑定或双向绑定（下面这几个类都继承自 BaseObservable 类）
    // ObservableBoolean, ObservableInt, ObservableLong, ObservableFloat, ObservableDouble 等
    // ObservableField<T>
    // ObservableList, ObservableMap
    var country: ObservableField<String> = ObservableField<String>()

    // 本例用于演示事件绑定并传参
    fun showName(name: String) {
        Toast.makeText(MyApplication.getInstance().applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        // 本例用于演示静态方法的绑定
        @JvmStatic
        fun substr8(name: String): String {
            return name.substring(0, 8)
        }

        // 本例用于演示 @BindingAdapter 注解的用法
        // 此方法将接管布局文件中的 app:imageUrl 绑定的数据（注：通过 @{} 绑定的数据才会被接管）
        @JvmStatic
        @BindingAdapter(value = ["imageUrl"]) // 如需使用 DataBinding 相关注解，则需要在 gradle 中配置 apply plugin: 'kotlin-kapt'
        fun loadImage(imageView: ImageView, loadUrl:String) {
            Glide.with(imageView).load(loadUrl).into(imageView)
        }
    }
}
