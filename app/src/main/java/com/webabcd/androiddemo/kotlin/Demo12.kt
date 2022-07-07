/**
 * 本例用于演示 let, also, with, run, runCatching, apply 的用法
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*
import java.io.IOException

class Demo12 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo12)

        sample1() // let
        sample2() // also
        sample3() // with
        sample4() // run, runCatching
        sample5() // apply
    }

    fun sample1() {
        val a = 1
        // let 的意思就是用 it 代替 let 左侧的对象
        a.let {
            appendMessage("${it + 1}") // 2
        }
        // 当然，你可以用别的名称代替 it
        a.let { p ->
            appendMessage("${p + 1}") // 2
        }

        val b: Int? = 1
        // let 的使用场景一般会结合 null 判断，即 ?.let
        b?.let {
            appendMessage("${it + 1}") // 2
        }

        // let 的最后一个表达式的值就是返回值
        var c = b?.let {
            appendMessage("${it + 1}") // 2
            "xyz" // 这是返回值。当然，你也可以通过 return 显式的返回一个值，比如 return@let "xyz"
        }
        appendMessage("$c") // xyz
    }

    fun sample2() {
        val a: Int? = 1
        // also 和 let 是基本一样的，区别就是 also 的返回值就是 also 左侧的对象（这样就可以使用链式语法了）
        a?.also {
            appendMessage("${it + 1}") // 2
        }?.also {
            appendMessage("${it + 1}") // 2
        }?.also {
            appendMessage("${it + 1}") // 2
        }
    }

    fun sample3() {
        val a = "webabcd"
        // with 的意思就是通过 this 代替你 with 的对象，如果你需要调用 this 的方法或属性，那么可以省略 this
        with(a) {
            appendMessage("${this.length}, $length") // 7, 7
        }

        // with 的最后一个表达式的值就是返回值
        val b = with(a) {
            appendMessage("${this.length}, $length") // 7, 7
            "xyz" // 这是返回值。当然，你也可以通过 return 显式的返回一个值，比如 return@with "xyz"
        }
        appendMessage("$b") // xyz
    }

    fun sample4() {
        val a: String? = "webabcd"
        // run 是 let 和 with 的结合体，最后一个表达式的值就是返回值
        val b = a?.run {
            appendMessage("$length") // 7
            "xyz" // 这是返回值。当然，你也可以通过 return 显式的返回一个值，比如 return@run  "xyz"
        }
        appendMessage("$b") // xyz

        // runCatching 就是 run 和 try/catch/finally 的结合体
        val c = a?.runCatching {
            throw Exception("I am an Exception")
        }?.onSuccess {
            appendMessage("onSuccess")
        }?.onFailure {
            appendMessage("onFailure: $it") // onFailure: java.lang.Exception: I am an Exception
        }
        // 获取返回值，有异常则返回 null
        appendMessage("${c?.getOrNull()}") // null

        val d = a!!.runCatching {
            "xyz" // 最后一个表达式的值就是返回值。当然，你也可以通过 return 显式的返回一个值，比如 return@runCatching "xyz"
        }.onSuccess {
            appendMessage("onSuccess") // onSuccess
        }.onFailure {
            appendMessage("onFailure: $it")
        }
        // 获取返回值，有异常则返回 null
        appendMessage("${d.getOrNull()}") // xyz
    }

    fun sample5() {
        val a: String? = "webabcd"
        // apply 是 also 和 with 的结合体，返回值就是 apply 左侧的对象（这样就可以使用链式语法了）
        a?.apply {
            appendMessage("$length") // 7
        }?.apply {
            appendMessage("$length") // 7
        }?.apply {
            appendMessage("$length") // 7
        }
    }



    fun appendMessage(message: String) {
        textView1.append(message)
        textView1.append("\n")
    }
}