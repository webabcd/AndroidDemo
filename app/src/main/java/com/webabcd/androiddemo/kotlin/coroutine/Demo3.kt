/**
 * coroutine - 协程
 * 本利用于演示协程的顺序执行，并行执行，async 的立即执行与懒启动，以及 async/await 的其他说明
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import com.webabcd.androiddemo.kotlin.HelloWorld_Java
import com.webabcd.androiddemo.kotlin.HelloWorld_Kotlin
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo2.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.*
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button1
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button2
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button3
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button4
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button5
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.button6
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.textView1
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.measureTimeMillis

class Demo3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo3)

        // 顺序执行
        button1.setOnClickListener {
            sample1()
        }

        // 并行执行（launch 方式）
        button2.setOnClickListener {
            sample2()
        }

        // 并行执行（async 方式）
        button3.setOnClickListener {
            sample3()
        }

        // 调用 async { } 时立即执行
        button4.setOnClickListener {
            sample4()
        }

        // 调用 async { } 时懒启动，等调用 await() 时再执行
        button5.setOnClickListener {
            sample5()
        }

        // async 返回的是 Deferred<T> 对象，其继承自 Job
        button6.setOnClickListener {
            sample6()
        }
    }

    suspend fun fun1() {
        delay(1000)
        appendMessage("fun1")
    }
    suspend fun fun2() {
        delay(1000)
        appendMessage("fun2")
    }

    fun sample1() {
        CoroutineScope(Dispatchers.Default).launch {
            // 顺序执行，执行完 fun1() 再执行 fun2()
            fun1()
            fun2()
        }
        appendMessage("xxx")
        // 03:28:08.138: xxx（main）
        // 03:28:09.130: fun1（DefaultDispatcher-worker-2）
        // 03:28:10.135: fun2（DefaultDispatcher-worker-2）
    }

    fun sample2() {
        CoroutineScope(Dispatchers.Default).launch {
            // 并发执行，fun1() 和 fun2() 并行执行（launch 方式）
            this.launch {
                fun1()
            }
            this.launch {
                fun2()
            }
        }
        appendMessage("xxx")
        // 03:28:34.341: xxx（main）
        // 03:28:35.378: fun1（DefaultDispatcher-worker-2）
        // 03:28:35.379: fun2（DefaultDispatcher-worker-2）
    }

    fun sample3() {
        CoroutineScope(Dispatchers.Default).launch {
            // 并发执行，fun1() 和 fun2() 并行执行（async 方式）
            this.async {
                fun1()
            }
            this.async {
                fun2()
            }
        }
        appendMessage("xxx")
        // 03:28:59.085: xxx（main）
        // 03:29:00.094: fun1（DefaultDispatcher-worker-1）
        // 03:29:00.098: fun2（DefaultDispatcher-worker-1）
    }

    fun sample4() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * 默认情况，调用 async { } 时他就会立即执行
             */
            val task1 = this.async {
                fun1()
            }
            val task2 = this.async {
                fun2()
            }
            // task1 和 task2 是并行执行的
            task1.await()
            task2.await()
            appendMessage("done")
        }
        appendMessage("xxx")
        // 03:29:59.239: xxx（main）
        // 03:30:00.249: fun2（DefaultDispatcher-worker-2）
        // 03:30:00.249: fun1（DefaultDispatcher-worker-1）
        // 03:30:00.254: done（DefaultDispatcher-worker-1）
    }

    fun sample5() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * start = CoroutineStart.LAZY 懒启动模式，调用 await() 的时候才会执行
             */
            val task1 = this.async(start = CoroutineStart.LAZY) {
                fun1()
            }
            val task2 = this.async(start = CoroutineStart.LAZY) {
                fun2()
            }
            // 因为 task1 是懒启动模式，所以这里调用 await() 的时候他才会执行
            task1.await()
            // 因为 task2 是懒启动模式，所以这里调用 await() 的时候他才会执行
            task2.await()
            appendMessage("done")
        }
        appendMessage("xxx")
        // 03:30:31.413: xxx（main）
        // 03:30:32.430: fun1（DefaultDispatcher-worker-2）
        // 03:30:33.449: fun2（DefaultDispatcher-worker-1）
        // 03:30:33.461: done（DefaultDispatcher-worker-1）
    }

    fun sample6() {
        /**
         * launch 返回的是 Job 对象，关于 Job 的说明请参见 Demo2.kt
         * async 返回的是 Deferred<T> 对象
         *   注：
         *   1、Deferred<T> 继承自 Job，所以关于 Deferred<T> 的等待与取消和超时处理等与 Job 是一样的，请参见 Demo2.kt
         *   2、Deferred<T> 可以通过 await() 在当前线程阻塞，直到他执行完
         *   3、Deferred<T> 中的 T 指的是返回值的类型
         *
         * 另外：launch 启动的协程是没有返回值的，async 启动的协程可以有返回值
         */
        CoroutineScope(Dispatchers.Default).launch {
            val task1 = this.async {
                delay(1000)
                "return value" // 返回值
            }
            val result = task1.await()
            appendMessage(result)
        }
        // return value（DefaultDispatcher-worker-2）
    }



    fun appendMessage(message: String) {
        val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
        val time = dateFormat.format(Date());
        val threadName = Thread.currentThread().name

        CoroutineScope(Dispatchers.Main).launch{
            val log = "$time: $message（$threadName）"
            textView1.append(log);
            textView1.append("\n");

            println(log)
        }
    }
}