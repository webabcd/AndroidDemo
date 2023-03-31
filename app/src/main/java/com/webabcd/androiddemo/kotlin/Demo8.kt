/**
 * 本例用于演示类继承，接口，抽象类，by 委托
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.databinding.ActivityJetpackLifecycleDatabindingdemoBinding
import com.webabcd.androiddemo.databinding.ActivityKotlinDemo8Binding
import kotlin.reflect.KProperty

class Demo8 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinDemo8Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinDemo8Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sample1() // 类继承
        sample2() // 接口
        sample3() // 抽象类
        sample4() // by 委托
    }

    fun sample1() {
        var a = Demo8_Class1(40);
        appendMessage(a.hello()) // hello:wanglei, age:40, country:china

        var b = Demo8_Class2(40, "中国");
        appendMessage(b.hello()) // hi:wanglei, age:80, country:中国
    }

    fun sample2() {
        var a = Demo8_Class3(40)
        appendMessage(a.hello()) // hello:wanglei, age:40 hi:wanglei, age:40
    }

    fun sample3() {
        var a = Demo8_Class4("wanglei")
        appendMessage(a.hello()) // hello:wanglei, age:100, country:china
    }

    fun sample4() {
        // 接口的实现委托给指定的参数（即通过 by 委托实现接口代理）
        var a = MyClass1("webabcd")
        var b = MyClass2(a)
        appendMessage("${a.hello()}, ${b.hello()}") // hello: webabcd, hello: webabcd

        // 属性的实现委托给指定的类（即通过 by 委托实现属性代理）
        var c = MyClass3()
        c.myName = "webabcd"
        appendMessage("${c.myName}") // property name:myName, value:webabcd

        // 注：还有一种 by 委托可以用来实现属性的懒初始化，即 by lazy（请参见：Demo1.kt）
    }
    interface MyInterface {
        fun hello(): String
    }
    class MyClass1(val name: String): MyInterface {
        override fun hello(): String {
            return "hello: $name"
        }
    }
    // MyClass2 类委托 xxx 参数实现 MyInterface 接口
    // 也就是说 MyClass2 类实现了 MyInterface 接口，你在调用 MyClass2 的相关实现的时候，其实调用的是 xxx 的实现
    class MyClass2(xxx: MyInterface): MyInterface by xxx {
        // 如果你在这里实现 MyInterface 接口的某些方法的话，则这里的实现会覆盖 xxx 的实现
    }
    // myName 属性的实现委托给 MyDelegate 类
    class MyClass3 {
        var myName: String by MyDelegate()
    }
    class MyDelegate {
        private var _s: String = ""
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "property name:${property.name}, value:$_s"
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            _s = value
        }
    }



    fun appendMessage(message: String) {
        val binding = ActivityJetpackLifecycleDatabindingdemoBinding.inflate(layoutInflater)
        binding.textView1.append(message);
        binding.textView1.append("\n");
    }
}