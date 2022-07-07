/**
 * 本例用于演示 kotlin 的语句（if..else, while, do..while, for, repeat, when, continue, break, return, 遍历 iterator 对象, try/catch/finally, kotlin.runCatching）
 */

package com.webabcd.androiddemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.*
import java.io.IOException
import java.lang.Exception
import java.util.*

class Demo3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo3)

        // 没有三元运算符 ? : 了
        // if..else, while, do..while, continue, break, return 都和 java 差不多

        sample1(); // 通过 if..else 直接给变量赋值
        sample2(); // for 语句, repeat 语句
        sample3(); // continue, break, return 跳出指定的循环
        sample4(); // 遍历 iterator 对象
        sample5(); // when 语句
        sample6(); // 异常处理 try/catch/finally, kotlin.runCatching
    }

    fun sample1() {

        // 可以通过 if..else 语句直接给变量赋值
        var a: Int = if (System.currentTimeMillis() % 2 == 0L) {
            123;
        } else {
            456;
        }
        appendMessage("$a");
    }

    fun sample2() {
        // in..until 大于等于左边的，小于右边的（step 是步长，如果步长是 1 则可以省略）
        var a = "";
        for (item in 0 until 5 step 1) {
            a += item;
        }
        appendMessage("$a"); // 01234

        // in..downTo 小于等于左边的，大于等于左边的（step 是步长，如果步长是 1 则可以省略）
        var b = "";
        for (item in 5 downTo 0 step 1) {
            b += item;
        }
        appendMessage("$b"); // 543210

        // .. 是区间操作符，大于等于左边的，小于等于右边的（step 是步长，如果步长是 1 则可以省略）
        var c = "";
        for (item in 0 .. 5 step 1) {
            c += item;
        }
        appendMessage("$c"); // 012345

        // step 步长，如果步长是 1 则可以省略
        var d = "";
        for (item in 0 until 6 step 2) {
            d += item;
        }
        appendMessage("$d"); // 024

        // 遍历数组的每个元素
        var e = "";
        var array = arrayOf(1, 2, 3, 4, 5)
        for (item in array) {
            e += item;
        }
        appendMessage("$e"); // 12345

        // 遍历数组的每个索引
        var f = "";
        for (index in array.indices) {
            f += index;
        }
        appendMessage("$f"); // 01234

        // 遍历数组的每个索引和元素
        var g = "";
        for ((index, value) in array.withIndex()) {
            g += index;
            g += value;
        }
        appendMessage("$g"); // 0112233445

        // repeat() - 重复执行指定的次数
        // repeat() { } 有一个参数，其代表当前的重复次数，从 0 开始
        repeat(3) {
            appendMessage("x$it")   // x0 x1 x2
        }
        repeat(3) { p ->
            appendMessage("x$p")    // x0 x1 x2
        }
    }

    fun sample3() {
        // kotlin 中的 continue, break, return 可以退出指定的循环

        // 为循环打标签，本例中为这个 for 打了一个名为 myfor 的标签
        myfor@ for (i in 0 .. 5) {
            appendMessage("$i")
            for (j in 10 .. 15) {
                appendMessage("$j")
                for (k in 20 .. 25) {
                    appendMessage("$k")

                    // 一般的 break 是退出最近的循环，kotlin 可以根据标签名退出指定的循环
                    // 本例为退出标签名为 myfor 的循环
                    break@myfor
                }
            }
        }
    }

    fun sample4() {
        var array = arrayOf(1, "2", false, 4, 5)

        // 获取 iterator 对象
        var iterator: Iterator<Any> = array.iterator()

        var a = "";
        // 遍历 iterator 对象
        while (iterator.hasNext()){
            a += iterator.next();
        }
        appendMessage("$a");
    }

    // when 类似 switch，但是 when 没有 break，因为其一旦匹配到就自动 break
    fun sample5() {
        var a = Random().nextInt(10)
        appendMessage("a=$a")

        // 判断 when 指定的变量
        when(a) {
            0, 1, 2, 3, 4 -> {
                appendMessage("01234")
            }
            5 -> {
                appendMessage("5")
            }
            else -> {
                appendMessage("6789")
            }
        }

        // 判断 when 指定的表达式
        when (a < 5) {
            true -> {
                appendMessage("a < 5")
            }
            false ->{
                appendMessage("a >= 5")
            }
        }

        // 可以通过 when 直接给变量赋值
        var b = when (a) {
            0 -> {
                "0"
            }
            1 ->{
                "0"
            }
            else ->
            {
                "23456789"
            }
        }
        appendMessage("$b")

        // 可以不给 when 提供参数，这样的 when 其实就是 if..else
        when {
            a == 0 -> appendMessage("0")
            a == 1 -> appendMessage("1")
            else -> appendMessage("23456789")
        }

        // 在 when 中使用 in 或 !in
        when (1) {
            in arrayOf(1, 2, 3, 4, 5) -> {
                appendMessage("in arrayOf(1, 2, 3, 4, 5)")
            }
            in 0 .. 10 -> appendMessage("in 0 .. 10") // 不会走到这里，因为匹配到第一条了，然后自动 break 了
            !in 5 .. 10 -> appendMessage("!in 5 .. 10") // 不会走到这里，因为匹配到第一条了，然后自动 break 了
        }

        // 在 when 中使用 is 或 !is
        when(1) {
            is Int -> appendMessage("is Int")
            !is Int -> appendMessage("!is Int")
            else -> appendMessage("else")
        }
    }

    fun sample6() {
        // 异常处理 try/catch/finally
        try {
            throw IOException("I am a IOException")
        } catch (e: IOException) {
            appendMessage("catch $e") // catch java.io.IOException: I am a IOException
        } catch (e: Exception) {
            appendMessage("catch $e")
        } finally {
            appendMessage("finally") // finally
        }

        // 异常处理 kotlin.runCatching
        val a = kotlin.runCatching {
            throw Exception("I am an Exception")
        }.onSuccess {
            appendMessage("onSuccess")
        }.onFailure {
            appendMessage("onFailure: $it") // onFailure: java.lang.Exception: I am an Exception
        }
        // 获取返回值，有异常则返回 null
        appendMessage("${a.getOrNull()}") // null

        val b = kotlin.runCatching {
            "xyz" // 最后一个表达式的值就是返回值。当然，你也可以通过 return 显式的返回一个值，比如 return@runCatching "xyz"
        }.onSuccess {
            appendMessage("onSuccess") // onSuccess
        }.onFailure {
            appendMessage("onFailure: $it")
        }
        // 获取返回值，有异常则返回 null
        appendMessage("${b.getOrNull()}") // xyz
    }

    fun appendMessage(message: String) {
        textView1.append(message);
        textView1.append("\n");
    }
}