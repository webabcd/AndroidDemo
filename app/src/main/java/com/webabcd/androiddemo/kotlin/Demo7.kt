/**
 * 本例用于演示类的定义和使用（实例化对象，调用属性，调用方法，静态属性，静态方法，嵌套类，内部类，局部类，匿名内部类）
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*

class Demo7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo7)

        sample1() // 实例化对象，调用属性，调用方法
        sample2() // 静态属性，静态方法
        sample3() // 嵌套类，内部类，局部类
        sample4() // 匿名内部类
    }

    fun sample1() {
        // 实例化对象（不需要 new）
        var a: Demo7_Class = Demo7_Class("webabcd", "china")
        var b: Demo7_Class = Demo7_Class("webabcd", 40, "china")

        // 调用方法
        appendMessage(a.getInfo())
        appendMessage(b.getInfo())

        // 调用属性
        b.country = "中国"
        b.weight = 33
        appendMessage(b.getInfo())

        // 用于演示属性的自定义 getter 和 setter
        b.height = 2;
        appendMessage(b.getInfo())
        b.height = 20;
        appendMessage(b.getInfo())
    }

    fun sample2() {
        // 静态属性（通过伴生对象实现）
        appendMessage(Demo7_Class.name)
        // 静态方法（通过伴生对象实现）
        appendMessage(Demo7_Class.hello("webabcd"))

        // 静态属性（通过 object 实现）
        appendMessage(MyStatic.name)
        // 静态方法（通过 object 实现）
        appendMessage(MyStatic.hello("webabcd"))
    }

    fun sample3() {
        // 嵌套类的使用
        var a: NestedClass = NestedClass("wanglei")
        appendMessage(a.hello())

        // 内部类的使用
        var b: InnerClass = InnerClass("wanglei")
        appendMessage(b.hello())

        // 定义一个局部类（可以访问外部类）
        class PartClass(name: String) {
            var name = ""
            init {
                this.name = name
            }
            fun hello(): String {

                return "hello: $name"
            }
        }

        // 局部类的使用
        var c: PartClass = PartClass("wanglei")
        appendMessage(c.hello())
    }

    fun sample4() {
        var a: MyView = MyView()
        // 这里的 object: OnClickListener 就是匿名内部类
        a.setOnClickListener(object: OnClickListener {
            override fun onClick(message: String) {
                appendMessage(message)
            }
        })
        a.testListener()
    }
    // 用于演示匿名内部类
    class MyView {
        private lateinit var listener : OnClickListener
        fun setOnClickListener(listener: OnClickListener) {
            this.listener = listener
        }
        fun testListener(){
            if (this.listener != null)
                listener.onClick("xxx")
        }
    }
    interface OnClickListener {
        fun onClick(message: String)
    }

    // 定义一个 object 用于实现静态属性和静态方法
    object MyStatic {
        var name = "wanglei"
        fun hello(name: String): String {
            return "hello: $name"
        }
    }

    // 定义一个嵌套类（不可以访问外部类）
    class NestedClass(name: String) {
        var name = ""
        init {
            this.name = name
        }
        fun hello(): String {
            return "hello: $name"
        }
    }

    // 定义一个内部类（不可以访问外部类）
    inner class InnerClass(name: String) {
        var name = ""
        init {
            this.name = name
        }
        fun hello(): String {
            return "hello: $name"
        }
    }



    fun appendMessage(message: String) {
        textView1.append(message)
        textView1.append("\n")
    }
}