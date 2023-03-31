/**
 * 本例用于演示 Lambda 表达式，高阶函数
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.webabcd.androiddemo.databinding.ActivityKotlinDemo10Binding

class Demo10 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinDemo10Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinDemo10Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        sample1()
        sample2()
        sample3()
    }

    fun sample1() {
        // 传统方式监听按钮的点击事件
        mBinding.button1.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                // 这里的 this@Demo10 相当于 java 中的 Demo10.this
                Toast.makeText(this@Demo10, "button1 clicked", Toast.LENGTH_SHORT).show()
            }
        })

        // lambda 方式监听按钮的点击事件
        // 因为 OnClickListener 接口只有一个方法，所以可以转换成如下的简单方式（如果接口有多个方法，则不能用这种简单方式）
        mBinding.button2.setOnClickListener {
            // 这里的 this 指向的就是 activity
            Toast.makeText(this, "button2 clicked", Toast.LENGTH_SHORT).show()
        }
    }


    fun sample2() {
        fun1() // fun1
        appendMessage(fun2()) // fun2
        appendMessage(fun3()) // fun3
        appendMessage("${fun4(1, 2)}") // 3
        appendMessage("${fun5(1, 2)}") // 3
        appendMessage("${fun6(1, 2)}") // 2
    }
    // 无传入参数，无返回值的 lambda
    val fun1 = { appendMessage("fun1") }
    // 无传入参数，有返回值的 lambda（自动推断返回值的类型）
    val fun2 = { "fun2" }
    // 无传入参数，有返回值的 lambda（这里的 () -> String 代表无传入参数，返回值的类型是 String）
    val fun3: () -> String = { "fun3" }
    // 有传入参数，有返回值的 lambda（自动推断返回值的类型）
    // 这里的 -> 的左边是参数声明，右边是表达式
    val fun4 = { a: Int, b: Int -> a + b }
    // 有传入参数，有返回值的 lambda
    // 这里 (Int, Int) -> Int 的意思是有 2 个 Int 类型的传入参数，返回值是 Int 类型的（如果没有返回值，则这里写 -> Unit 即可）
    // 这里 { a, b -> a + b } 的意思是 -> 的左边是参数声明，右边是表达式
    val fun5: (Int, Int) -> Int = { a, b -> a + b }
    // 这里有个约定，如果你后面的表达式用不到某个参数，就用 _ 表示这个参数
    val fun6: (Int, Int) -> Int = { _ , b -> b }


    // 演示高阶函数（High-Order Function），所谓高阶函数就是将函数用作另一个函数的传入参数或返回值
    fun sample3() {
        // lambda 表达式作为函数的传入参数的示例
        // 下面 2 种写法均可，建议用第 2 种写法（注：这种写法要求函数的最后一个参数是函数，此时就可以把这个最后的参数通过 lambda 表达式的方式放到括号外面）
        appendMessage("${funA(1,2, { a, b ->  a + b })}") // 3
        appendMessage("${funA(1,2) { a, b ->  a + b }}") // 3
        // 当然，也可以函数作为参数（通过 :: 把指定的函数当做一个参数传递到另一个函数中）
        fun myFun(x: Int, y: Int): Int = x + y
        appendMessage("${funA(1, 2, ::myFun)}") // 3
        // 如果作为参数的函数定义在其他类中的话，则写成类似 className::functionName 即可
        appendMessage("${funA(1, 2, Int::plus)}") // 3
        // 也可以匿名函数作为参数
        appendMessage("${funA(1, 2, fun(x: Int, y: Int): Int { return x + y })}") // 3

        // lambda 表达式作为函数的传入参数的示例
        // 以下面的例子为例，当 lambda 表达式只有一个参数时，可以用简便写法，通过 it 替代 x -> x
        appendMessage("${funB(3) { x -> x > 5 }}") // 0
        appendMessage("${funB(10) { it > 5 }}") // 10

        // lambda 表达式作为函数的返回值的示例
        appendMessage("${funC(10)(1, 2)}") // 13

        // lambda 表达式实现扩展方法的示例
        appendMessage("${funD(1, 2)}") // 3
        appendMessage("${1.funD(2)}") // 3
        appendMessage("${funE(1, 2)}") // 3
        appendMessage("${1.funE(2)}") // 3

        // lambda 表达式作为扩展方法的参数的示例
        appendMessage("${"abc".sumASCII { it.code }}") // 会输出 294（就是 97 + 98 + 99）

        // 这里再说明一下 lambda 表达式的返回值的问题
        // 有个约定，最后一个表达式的值就是返回值
        appendMessage("${"abc".sumASCII { 100 }}") // 300
        // 也可以通过 return 显式的返回一个值
        appendMessage("${"abc".sumASCII { return@sumASCII 100 }}") // 300

        // lambda 表达式实现扩展方法并作为函数的参数，且扩展方法无参数
        val a = funF {
            // 本例中这个 this 代表的就是字符串 hello:
            // 如果你需要调用 this 的方法或属性，那么可以省略 this
            appendMessage("$this, ${this.length}, $length") // hello:, 6, 6
            this + "webabcd"
        }
        appendMessage("$a") // hello:webabcd

        // 闭包的示例
        val b = funG(5)
        appendMessage("${b()}, ${b()}, ${b()}") // 5, 10, 15
    }

    // lambda 表达式作为函数的传入参数的示例
    // 这里有个建议的命名规范（operation - 多个传入参数；selector - 一个传入参数且返回值不是 bool 类型；predicate - 一个传入参数且返回值为 bool 类型）
    // lambda 表达式中的参数可以有参数名称，也可以省略参数名称
    fun funA(a: Int, b: Int, operation: (x: Int, y: Int) -> Int) : Int {
        return operation(a, b)
    }

    // lambda 表达式作为函数的传入参数的示例
    // 这里有个建议的命名规范（operation - 多个传入参数；selector - 一个传入参数且返回值不是 bool 类型；predicate - 一个传入参数且返回值为 bool 类型）
    fun funB(a: Int, predicate: (Int) -> Boolean) : Int {
        return if (predicate(a)) {
            a
        } else {
            0
        }
    }

    // lambda 表达式作为函数的返回值的示例
    fun funC(a: Int): (Int, Int) -> Int {
        return fun(x, y) : Int {
            return a + x + y
        }
    }

    // lambda 表达式实现扩展方法
    // 可以类似这么调用 1.funD(2) 或类似这么调用 funD(1, 2)
    val funD: Int.(Int) -> Int = { this + it } // 为 Int 类型扩展出 funD(Int) 方法
    val funE: Int.(Int) -> Int = Int::plus // 为 Int 类型扩展出 funE(Int) 方法

    // lambda 表达式作为扩展方法的参数
    // 这里有个建议的命名规范（operation - 多个传入参数；selector - 一个传入参数且返回值不是 bool 类型；predicate - 一个传入参数且返回值为 bool 类型）
    fun CharSequence.sumASCII(selector: (Char) -> Int): Int {
        var sum: Int = 0
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }

    // lambda 表达式实现扩展方法并作为函数的参数，且扩展方法无参数
    // 可以类似这么调用 funF { this + "webabcd" }，其中的 this 代表的就是字符串 hello:
    fun funF(a: String.() -> String): String { // 在函数内为 String 扩展出了 a() 方法
        // 下面这句等同于 return a("hello: ")
        return "hello:".a()
    }

    // 闭包
    fun funG(number: Int): () -> Int {
        var total = 0
        // 这个函数会将其外部的 total 变量捕获过来，也就是说 myFun() 是不会丢失 total 的
        val myFun: () -> Int = {
            total += number
            total
        }
        // 函数返回的函数就是闭包（闭包引用的闭包外的变量的生命周期会拉长到与闭包一致）
        return myFun
    }



    fun appendMessage(message: String) {
        mBinding.textView1.append(message);
        mBinding.textView1.append("\n");
    }
}