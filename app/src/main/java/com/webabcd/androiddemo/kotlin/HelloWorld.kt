package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.databinding.ActivityJetpackLifecycleDatabindingdemoBinding
import com.webabcd.androiddemo.databinding.ActivityKotlinHelloworldBinding

class HelloWorld : AppCompatActivity() {

    /**
     * ActivityKotlinHelloworldBinding 是 gradle 自动生成的类，其对应的 xml 是 activity_kotlin_helloworld.xml
     * 如果想使用这种 xml 和 kotlin 绑定的方式的话，需要在 build.gradle 中做如下配置
     * buildFeatures {
     *   viewBinding = true
     * }
     */
    private lateinit var mBinding: ActivityKotlinHelloworldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置 activity 的布局，并获取绑定对象（方式一）
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_kotlin_helloworld)

        // 设置 activity 的布局，并获取绑定对象（方式二）
        mBinding = ActivityKotlinHelloworldBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.button1.setOnClickListener {

            // 注：kotlin 语句结尾可以加分号，也可以不加分号（官方建议不加分号）
            mBinding.textView1.append("hello world\n")

            // 用于演示 java 调用 kotlin
            mBinding.textView1.append(HelloWorld_Java().javaToKotlin() + "\n")

            // 用于演示 kotlin 调用 java
            mBinding.textView1.append(HelloWorld_Kotlin().kotlinToJava() + "\n")
        }
    }
}
