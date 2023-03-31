/**
 * 本例用于演示枚举，密封类，数据类
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.databinding.ActivityKotlinDemo9Binding

class Demo9 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinDemo9Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinDemo9Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sample1() // 枚举
        sample2() // 密封类
        sample3() // 数据类
    }

    fun sample1() {
        // .name 是枚举常量的名称
        // .ordinal 是枚举常量在枚举中的序号
        appendMessage("${Demo9_Enum1.OK}, ${Demo9_Enum1.ERROR}, " + // OK, ERROR
                "${Demo9_Enum1.OK.name}, ${Demo9_Enum1.ERROR.name}, " + // OK, ERROR
                "${Demo9_Enum1.OK.ordinal}, ${Demo9_Enum1.ERROR.ordinal}") // 0, 1

        // Demo9_Enum2.OK.status - 用于演示如何获取枚举常量的对应的构造函数中的参数值
        // Demo9_Enum2.OK.getMessage() - 用于演示如何调用枚举中的方法
        appendMessage("${Demo9_Enum2.OK}, ${Demo9_Enum2.OK.ordinal}, ${Demo9_Enum2.OK.status}, ${Demo9_Enum2.OK.getMessage()}") // OK, 0, 100, message:100

        // 遍历枚举常量
        appendMessage(enumValues<Demo9_Enum2>().joinToString { it.name }) // OK, ERROR
        // 字符串转枚举常量
        appendMessage("${enumValueOf<Demo9_Enum2>("ERROR").status}") // 200
        // 字符串转枚举常量
        appendMessage("${Demo9_Enum2.valueOf("ERROR").status}") // 200
        // 获取枚举中指定序号的枚举常量
        appendMessage("${Demo9_Enum2.values()[1].status}") // 200
    }

    fun sample2() {
        // 密封类不能实例化
        // 密封类的用处和枚举差不多，区别就是枚举常量是单例的，而密封类的子类可以有多个实例

        var a = Demo9_SealedClass.OK()
        var b = Demo9_SealedClass.OK()

        // 通过类似 a is Demo9_SealedClass.OK 的方式实现枚举的作用
        appendMessage("${a is Demo9_SealedClass.OK}, ${b is Demo9_SealedClass.OK}, ${a == b}") // true, true, false
    }

    fun sample3() {
        var a = Demo9_DataClass("webabcd", 40)
        a.salary = 5000
        // 数据对象支持 toString(), componentN()
        appendMessage("$a, ${a.component1()}, ${a.component2()}") // Demo9_DataClass(name=webabcd,age=40), webabcd, 40

        // 数据对象支持 equals()
        appendMessage("${a == Demo9_DataClass("webabcd", 40)}") // true

        // 数据对象支持 copy()，其用于复制数据对象，且可以同时修改只读变量
        var b = a.copy(age = 100)
        appendMessage("$b")  // Demo9_DataClass(name=wanglei,age=100)

        // 数据对象支持解构
        val (name, age) = b
        appendMessage("name:$name, age:$age") // name:wanglei, age:100

        // 系统自带的 Pair 就是一个数据类
        var p = Pair(1, "abc")
        appendMessage("first:${p.first}, second:${p.second}") // first:1, second:abc

        // 系统自带的 Triple 就是一个数据类
        var t = Triple(2, "xyz", true)
        appendMessage("first:${t.first}, second:${t.second}, third:${t.third}") // first:2, second:xyz, third:true
    }



    fun appendMessage(message: String) {
        mBinding.textView1.append(message);
        mBinding.textView1.append("\n");
    }
}