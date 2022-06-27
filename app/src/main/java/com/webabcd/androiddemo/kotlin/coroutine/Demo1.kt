/**
 * coroutine - 协程
 * 本利用于演示协程基础，包括 CoroutineScope, launch, async, await, withContext, suspend
 *
 * 进程是资源分配的最小单位，不同进程之间资源都是独立的
 * 线程是 CPU 调度的基本单位，本身并不拥有系统资源，所有线程会共享进程的资源
 * 协程可以认为是运行在线程上的代码块，协程提供的挂起操作会使协程暂停执行，而不会导致线程阻塞。一个线程内部即使创建大量的协程都不会有任何问题
 *   一个线程内，同一时刻只会有一个协程在运行，其他协程挂起等待，不同协程之间的切换不涉及内核（线程的切换会涉及到内核），所以切换代价更小，更轻量级
 *   一个协程内出现挂起时只是暂停，其所属线程可以运行其他逻辑
 *     比如你在主线程启动一个协程然后挂起，其并不会阻塞主线程
 *   一个协程并不绑定在任何特定的线程上（如果你不强制指定某一特定线程的话），它可以在一个线程中暂停执行，在另一个线程中继续执行
 *     比如你在 Dispatchers.Default 启动一个协程，它开始可能运行在 worker-1 线程，然后协程挂起，然后协程再恢复，此时它可能会运行在 worker-2 线程
 *     如果你是在 Dispatchers.Main 启动一个协程，则它只会运行在主线程中
 *     如果你是在 newSingleThreadContext("myThread") 启动一个协程，则它只会运行在 myThread 线程中
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo1.*
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.button1
import kotlinx.android.synthetic.main.activity_kotlin_helloworld.textView1
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class Demo1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo3)

        // 演示 CoroutineScope.launch
        button1.setOnClickListener {
            sample1()
        }

        // 演示 runBlocking
        button2.setOnClickListener {
            sample2()
        }

        // 演示 MainScope, GlobalScope
        button3.setOnClickListener {
            sample3()
        }

        // 演示 async, await
        button4.setOnClickListener {
            sample4()
        }

        // 演示 suspend
        button5.setOnClickListener {
            sample5()
        }

        // 演示 withContext
        button6.setOnClickListener {
            sample6()
        }
    }

    fun sample1() {
        /**
         * CoroutineScope.launch { } - 在当前线程中启动一个新的协程
         * CoroutineScope(Dispatchers.Default).launch { } - 在子线程中启动一个新的协程（适合 cpu 密集型的工作）
         * CoroutineScope(Dispatchers.IO).launch { } - 在子线程中启动一个新的协程（适合存储或网络等 IO 操作的工作）
         * CoroutineScope(Dispatchers.Main).launch { } - 在主线程（UI 线程）中启动一个新的协程
         * CoroutineScope(Dispatchers.Unconfined).launch { } - 在当前线程中启动一个新的协程，然后在第一个挂起点恢复后再从其他某线程中恢复协程
         * CoroutineScope(newSingleThreadContext("myThread")).launch { } - 新开一个名为 myThread 的线程，并在此线程中启动一个新的协程
         */

        // launch 的返回值是一个 Job 对象，后续再说
        val job = CoroutineScope(Dispatchers.Default).launch {
            // this 是一个 CoroutineScope 对象，通过他可以启动新的协程
            /**
             * launch { } - 在当前线程中启动一个新的协程
             * launch(Dispatchers.Default|Dispatchers.IO|Dispatchers.Main) { } - 在指定线程中启动一个新的协程
             */
            this.launch(Dispatchers.Default) {
                appendMessage("x")   // x（DefaultDispatcher-worker-2）
                delay(1000) // delay 是一个 suspend 函数
                appendMessage("y")   // y（DefaultDispatcher-worker-2）
            }

            appendMessage("b")       // b（DefaultDispatcher-worker-1）
            delay(1000)     // delay 是一个 suspend 函数
            appendMessage("c")       // c（DefaultDispatcher-worker-1）
        }

        /**
         * 注意：
         * 1、一般来说本例的执行顺序是 a b x，但是因为协程足够快，所以执行顺序也可能是 b a x 之类的
         * 2、本例中 x 和 y 不一定运行在同一个线程，b 和 c 不一定运行在同一个线程
         *    因为一个协程并不绑定在任何特定的线程上，它可以在一个线程中暂停执行，在另一个线程中继续执行
         */
        appendMessage("a")           // a（main）
    }

    fun sample2() {
        /**
         * runBlocking { } - 阻塞当前线程，直到内部的所有协程执行完毕
         *   在 { } 中的 this 是一个 CoroutineScope 对象，通过他可以启动新的协程
         */
        runBlocking {
            this.launch(Dispatchers.Default) {
                appendMessage("b")   // b（DefaultDispatcher-worker-2）
                delay(1000) // delay 是一个 suspend 函数
                appendMessage("c")   // c（DefaultDispatcher-worker-2）
            }
            appendMessage("a")       // a（main）
        }
        appendMessage("d")           // d（main）
    }

    fun sample3() {
        /**
         * MainScope().launch - 相当于 CoroutineScope(Dispatchers.Main).launch
         * GlobalScope.launch - 与 CoroutineScope.launch 的区别是 GlobalScope 是作用域为全局的顶级协程
         *   也就是说如果你取消一个协程的话，那么所有其内启动的协程也都会被取消，除了 GlobalScope 方式启动的协程
         *
         * 注：MainScope() 和 GlobalScope 都实现了 CoroutineScope 接口
         */
        val job1 = MainScope().launch {
            delay(500) // b（main）
            appendMessage("b")
        }
        val job2 = GlobalScope.launch(Dispatchers.Default) {
            delay(1000)
            appendMessage("c")  // c（DefaultDispatcher-worker-1）
        }
        appendMessage("a")      // a（main）
    }

    fun sample4() {
        /**
         * async - 其和 launch 的区别是：launch 返回的是 Job 对象，async 返回的是 Deferred<T> 对象（注：Deferred<T> 继承自 Job）
         *   Deferred<T> 可以通过 await() 在当前线程阻塞，直到他执行完
         */
        val task1 = CoroutineScope(Dispatchers.Default).async {
            delay(2000)
            appendMessage("e")             // e（DefaultDispatcher-worker-1）
        }

        runBlocking { // this: CoroutineScope
            // this 是一个 CoroutineScope 对象，通过他可以启动新的协程
            // 无返回值的任务
            val task2 = this.async {
                delay(500)
                appendMessage("a")          // a（main）
            }

            // 有返回值的任务
            val task3 = this.async(Dispatchers.Default) {
                delay(1000)
                appendMessage("b")          // b（DefaultDispatcher-worker-1）
                "c" // 返回值
            }

            // await() - 在当前线程阻塞，直到任务执行完
            task2.await()
            appendMessage(task3.await())    // c（main）
        }

        // 本例中 task1, task2, task3 是并行执行的，主线程会阻塞直到 task2 和 task3 执行完
        appendMessage("d")                  // d（main）
    }

    fun sample5() {
        /**
         * 在协程中调用的函数必须是 suspend 函数，也就是说在 launch 或 async 中调用的函数必须是 suspend 函数
         */
        var job = CoroutineScope(Dispatchers.Default).launch {
            // fun1() 执行完后执行 fun2()，fun2() 执行完后执行 fun3()
            fun1()  // a（DefaultDispatcher-worker-1）
            fun2()  // b（DefaultDispatcher-worker-1）
            fun3()  // c（DefaultDispatcher-worker-1）
        }
    }
    // suspend 函数，可以在协程或其他 suspend 函数中被调用
    suspend fun fun1() {
        delay(1000)
        appendMessage("a")
    }
    // coroutineScope - 在 { } 中的所有逻辑执行完毕后才会返回
    // coroutineScope 挂起时只是暂停，其所属线程可以运行其他逻辑
    suspend fun fun2() = coroutineScope {  // this: CoroutineScope
        // this 是一个 CoroutineScope 对象，通过他可以启动新的协程
        // this.launch { }
        // this.async { }

        delay(1000)
        appendMessage("b")
    }
    // runBlocking - 在 { } 中的所有逻辑执行完毕后才会返回
    // runBlocking 挂起时会阻塞其所属线程
    suspend fun fun3() = runBlocking {  // this: CoroutineScope
        // this 是一个 CoroutineScope 对象，通过他可以启动新的协程
        // this.launch { }
        // this.async { }

        delay(1000)
        appendMessage("c")
    }

    fun sample6() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            appendMessage("b")      // b（DefaultDispatcher-worker-1）

            val task = async {
                delay(1000)
                appendMessage("d")  // d（DefaultDispatcher-worker-1）
            }

            // withContext() - 在指定的线程中执行相关逻辑，执行完毕后再把线程切回去
            //   withContext() 可以有返回值
            //   withContext() 必须在协程中或 suspend 函数中调用
            val v = withContext(Dispatchers.Main) {
                appendMessage("c")  // c（main）
                task.await()
                appendMessage("e")  // e（main）
                "f" // 返回值
            }

            appendMessage(v)        // f（DefaultDispatcher-worker-1）
        }

        appendMessage("a")          // a（main）
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