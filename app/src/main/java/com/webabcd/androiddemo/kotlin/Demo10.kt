/**
 * 本例用于演示 Lambda 表达式
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_demo10.*

class Demo10 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo10)
        
        sample1()
        sample2()
        sample3()
    }

    fun sample1() {
        // 传统方式监听按钮的点击事件
        button1.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                // 这里的 this@Demo10 相当于 java 中的 Demo10.this
                Toast.makeText(this@Demo10, "button1 clicked", Toast.LENGTH_SHORT).show()
            }
        })

        // lambda 方式监听按钮的点击事件
        button2.setOnClickListener {
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


    fun sample3() {
        // lambda 表达式作为函数的传入参数的示例
        // 下面 2 种写法均可，但是建议用第 2 种
        appendMessage("${funA(1,2, { a, b ->  a + b })}") // 3
        appendMessage("${funA(1,2) { a, b ->  a + b }}") // 3

        // lambda 表达式作为函数的传入参数的示例
        // 以下面的例子为例，当 lambda 表达式只有一个参数时，可以用简便写法，通过 it 替代 x -> x
        appendMessage("${funB(3) { x -> x > 5 }}") // 0
        appendMessage("${funB(10) { it > 5 }}") // 10

        // lambda 表达式作为函数的返回值的示例
        appendMessage("${funC(10)(1, 2)}") // 13
    }

    // lambda 表达式作为函数的传入参数的示例
    fun funA(a: Int, b: Int, result: (Int, Int) -> Int) : Int {
        return result(a ,b)
    }

    // lambda 表达式作为函数的传入参数的示例
    fun funB(a: Int, result: (Int) -> Boolean) : Int {
        return if (result(a)) {
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




    fun appendMessage(message: String) {
        textView1.append(message)
        textView1.append("\n")
    }
}