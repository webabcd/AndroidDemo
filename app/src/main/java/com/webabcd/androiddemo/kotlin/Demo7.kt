/**
 * 本例用于演示类的定义和使用（实例化对象，调用属性，调用方法，自定义 getter 和 setter，下标运算符 [] 的使用，扩展方法，扩展属性，静态属性，静态方法，嵌套类，内部类，局部类，匿名内部类，内联类，类型别名）
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.databinding.ActivityKotlinDemo7Binding

// 类型别名，必须在顶层声明
typealias MyType = String

class Demo7 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinDemo7Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinDemo7Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sample1() // 实例化对象，调用属性，调用方法，自定义 getter 和 setter，下标运算符 [] 的使用，扩展方法，扩展属性
        sample2() // 静态属性，静态方法
        sample3() // 嵌套类，内部类，局部类
        sample4() // 匿名内部类
        sample5() // 内联类，类型别名
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

        // 下标运算符 [] 的使用
        a[0] = "123"
        appendMessage(a[0])

        // 扩展方法和扩展属性
        appendMessage(a.myExtensionMethod()) // my extension method
        appendMessage(a.myExtensionProperty) // my extension property
        appendMessage(Demo7_Class.myCompanionExtensionMethod()) // my companion extension method
        appendMessage(Demo7_Class.myCompanionExtensionProperty) // my companion extension property
    }

    fun sample2() {
        // 静态属性（通过伴生对象实现，这种方式 Demo7_Class 可以有静态属性和实例属性）
        appendMessage(Demo7_Class.name)
        // 静态方法（通过伴生对象实现，这种方式 Demo7_Class 可以有静态方法和实例方法）
        appendMessage(Demo7_Class.hello("webabcd"))

        // 静态属性（通过 object 实现，这种方式 MyStatic 只可以有静态属性，不可以有实例属性）
        appendMessage(MyStatic.name)
        // 静态方法（通过 object 实现，这种方式 MyStatic 只可以有静态方法，不可以有实例方法）
        appendMessage(MyStatic.hello("webabcd"))
    }

    fun sample3() {
        // 嵌套类的使用
        var a: NestedClass = NestedClass("wanglei")
        appendMessage(a.hello())

        // 内部类的使用
        var b: InnerClass = InnerClass("wanglei")
        appendMessage(b.hello())

        // 定义一个局部类，即方法中的类（可以访问外部类的成员）
        class PartClass(name: String) {
            var name = ""
            init {
                this.name = name
            }
            fun hello(): String {
                // 可以访问外部类的成员
                this@Demo7.appendMessage("我是局部类")
                return "hello: $name"
            }
        }

        // 局部类的使用
        var c: PartClass = PartClass("wanglei")
        appendMessage(c.hello())
    }

    fun sample4() {
        // 通过 object 可以实现匿名内部类
        val a = object {
            val name = "webabcd"
            override fun toString() = "name: $name"
        }
        appendMessage("${a.name}, ${a.toString()}") // webabcd, name: webabcd

        var b: MyView = MyView()
        // 这里的 object 就是一个匿名内部类，其实现了 OnClickListener 接口
        b.setOnClickListener(object: OnClickListener {
            override fun onClick(message: String) {
                appendMessage(message)
            }
        })
        b.testListener()
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

    fun sample5() {
        // 类型别名
        val a: MyType = "webabcd"
        appendMessage("${a is MyType}, ${a is String}, ${a::class.simpleName}") // true, true, String

        // 内联类
        var b: MyInlineClass = MyInlineClass("webabcd")
        appendMessage("${b.name}") // webabcd
    }
    // 用 value 修饰的类就是内联类，必须要有 @JvmInline
    // 内联类要求在主构造函数中声明一个属性，内联类其实就是这个属性的包装
    // 这个存在的意义就是，对于基本类型的内联类来说，其会由 heap 上分配改为 stack 上分配，从而提高性能
    @JvmInline
    value class MyInlineClass(val name: String)

    // 定义一个 object 用于实现静态属性和静态方法
    object MyStatic {
        var name = "wanglei"
        fun hello(name: String): String {
            return "hello: $name"
        }
    }

    // 定义一个嵌套类，即类中的类（不可以访问外部类的成员）
    class NestedClass(name: String) {
        var name = ""
        init {
            this.name = name
        }
        fun hello(): String {
            return "hello: $name"
        }
    }

    // 定义一个内部类，内部类其实就是用 inner 修饰的嵌套类
    // 内部类可以访问外部类的成员
    inner class InnerClass(name: String) {
        var name = ""
        init {
            this.name = name
        }
        fun hello(): String {
            // 可以访问外部类的成员
            this@Demo7.appendMessage("我是内部类")
            return "hello: $name"
        }
    }



    fun appendMessage(message: String) {
        mBinding.textView1.append(message);
        mBinding.textView1.append("\n");
    }
}