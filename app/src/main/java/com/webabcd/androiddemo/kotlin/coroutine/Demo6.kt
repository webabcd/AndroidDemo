/**
 * flow - 异步流
 * 流程上简单来说就是，collect 的时候会去异步执行 flow，然后接收 flow 发送的数据
 *
 * 本例用于演示通过 flow 发送和接收数据，flow 的超时处理，取消处理，异常处理，指定 flow 阶段的运行协程使其不同于 collect 阶段的运行协程，让 collect 阶段运行到其他协程从而不阻塞当前协程
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo6.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class Demo6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo6)

        // 通过 flow 发送和接收数据
        button1.setOnClickListener {
            sample1()
        }

        // flow 的超时处理
        button2.setOnClickListener {
            sample2()
        }

        // flow 的取消处理
        button3.setOnClickListener {
            sample3()
        }

        // flow 的异常处理
        button4.setOnClickListener {
            sample4()
        }

        // 指定 flow 阶段的运行协程，使其不同于 collect 阶段的运行协程
        button5.setOnClickListener {
            sample5()
        }

        // 让 collect 阶段运行到其他协程，从而不阻塞当前协程
        button6.setOnClickListener {
            sample6()
        }
    }

    fun sample1() {
        // flow<T> {} - 实例化一个异步流，T 是 emit() 返回的数据类型，如果能通过 emit() 推导出数据类型的话，则可以省略 T
        val flow = flow<String> {
            appendMessage("start flow")
            for (i in 1..2) {
                delay(500)
                // emit() - 发出数据
                emit("emit: $i")
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            // collect {} - 接收 flow 的 emit() 的数据，并且阻塞直到接收完所有数据
            // 注：
            // 1、实例化 flow 后他并不会自动启动，而是等你调用 collect {} 的时候 flow {} 才会启动
            // 2、在无缓冲区的情况下，发送数据会阻塞，直到发送的数据被接收为止；接收数据会阻塞，直到接收到数据为止
            flow.collect { value -> appendMessage(value) }
            flow.collect { value -> appendMessage(value) }
        }
        // start flow（DefaultDispatcher-worker-1）
        // emit: 1（DefaultDispatcher-worker-1）
        // emit: 2（DefaultDispatcher-worker-1）
        // start flow（DefaultDispatcher-worker-1）
        // emit: 1（DefaultDispatcher-worker-1）
        // emit: 2（DefaultDispatcher-worker-1）
    }

    fun sample2() {
        val flow = flow {
            for (i in 1..100) {
                delay(500)
                emit("emit: $i")
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            // flow 的超时处理，关于 withTimeout() 和 withTimeoutOrNull() 请参见 Demo2.kt 中的相关说明
            withTimeoutOrNull(1200) {
                flow.collect { value -> appendMessage(value) }
            }
            appendMessage("done")
        }
        // emit: 1（DefaultDispatcher-worker-1）
        // emit: 2（DefaultDispatcher-worker-1）
        // done（DefaultDispatcher-worker-1）
    }

    fun sample3() {
        val flow = flow {
            for (i in 1..100) {
                try {
                    emit(i)
                } catch (e: CancellationException) {
                    // 运行到 suspend 函数时，如果发现取消了，则会抛出异常，你的 flow 就退出了
                    // 注意，所有处理程序都会忽略 CancellationException 异常（也就是说抛出此异常并不会导致崩溃），因为它就是用于退出 flow 用的
                    // 本例仅演示用，实际开发中你可以不必捕获 CancellationException 异常
                    throw e
                }
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            flow.collect { value ->
                if (value == 3) {
                    // 取消 flow（这个和 Job 的取消是很像的，请参见 Demo2.kt 中的相关说明）
                    cancel()
                }
                appendMessage("$value")
            }
            appendMessage("done")
        }
        // 1（DefaultDispatcher-worker-1）
        // 2（DefaultDispatcher-worker-1）
        // 3（DefaultDispatcher-worker-1）
    }

    fun sample4() {
        CoroutineScope(Dispatchers.Default).launch {
            // 在 flow {} 外做 try/catch（无论是 collect{} 之前的，还是 collect{} 之中的异常都是能 catch 到的）
            try {
                flow<Int> {
                    throw Exception("sample1 ex")
                }.collect { _ ->

                }
            } catch (e: Exception) {
                appendMessage("catch: $e")
            } finally {
                appendMessage("finally sample1")
            }
            // catch: java.lang.Exception: sample1 ex（DefaultDispatcher-worker-1）
            // finally sample1（DefaultDispatcher-worker-1）


            // 在 flow {} 外做 try/catch（无论是 collect{} 之前的，还是 collect{} 之中的异常都是能 catch 到的）
            try {
                flow<Int> {
                    emit(0)
                }.collect { _ ->
                    throw Exception("sample2 ex")
                }
            } catch (e: Exception) {
                appendMessage("catch: $e")
            } finally {
                appendMessage("finally sample2")
            }
            // catch: java.lang.Exception: sample2 ex（DefaultDispatcher-worker-1）
            // finally sample2（DefaultDispatcher-worker-1）


            // 通过 Flow 的 catch {} 方法捕获异常（只能 catch 到 collect{} 之前的异常）
            // Flow 的 onCompletion {} 类似 finally，而且你可以通过 onCompletion {} 中的参数知道是否是因为异常导致了完成
            //   注：onCompletion {} 必须在 catch {} 之前调用
            flow<Int> {
                throw Exception("sample3 ex")
            }.onCompletion { cause ->
                // cause 不为 null 则是因为异常导致的完成；cause 为 null 则是正常完成
                if (cause != null) {
                    appendMessage("finally sample3, cause:$cause")
                }
            }.catch { e ->
                appendMessage("catch: $e")
            }.collect { _ ->

            }
            // finally sample3, cause:java.lang.Exception: sample3 ex（DefaultDispatcher-worker-1）
            // catch: java.lang.Exception: sample3 ex（DefaultDispatcher-worker-1）


            // 通过 Flow 的 catch {} 方法无法捕获 collect{} 之中的异常，下面这段代码会崩溃的
            /*
            flow<Int> {
                emit(1)
            }.catch { e ->
                appendMessage("catch: $e")
            }.collect { _ ->
                throw Exception("exception")
            }
            */
        }
    }

    fun sample5() {
        val flow = flow {
            for (i in 1..2) {
                delay(500)
                appendMessage("flow: $i, ${currentCoroutineContext()[CoroutineName]?.name}")
                emit("$i")
            }
        }

        CoroutineScope(Dispatchers.Default + CoroutineName("c2")).launch {
            // flowOn() - 用于指定 flow 阶段的运行协程，如果不指定的话，默认 flow 阶段的运行协程和 collect 阶段的运行协程是一样的
            flow.flowOn(Dispatchers.Default + CoroutineName("c1"))
                .collect { value ->
                    appendMessage("collect $value, ${currentCoroutineContext()[CoroutineName]?.name}")
                }
        }
        // flow: 1, c1（DefaultDispatcher-worker-1）
        // collect 1, c2（DefaultDispatcher-worker-1）
        // flow: 2, c1（DefaultDispatcher-worker-1）
        // collect 2, c2（DefaultDispatcher-worker-1）
    }

    fun sample6() {
        val flow = flow {
            for (i in 1..2) {
                delay(500)
                emit(i)
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            // 这是常规写法，调用 collect 的时候就会执行 flow，然后阻塞直到 flow 执行完
            flow.collect { appendMessage("collect: $it") }
            appendMessage("done")
            // collect: 1（DefaultDispatcher-worker-1）
            // collect: 2（DefaultDispatcher-worker-1）
            // done（DefaultDispatcher-worker-1）


            // 如果想要调用 collect 的时候不阻塞的话，可以像下面这样做
            // launchIn() - 在指定的 CoroutineScope 中启动 collect（这样就不会阻塞当前的协程了）
            // onEach {} - 其会在数据发给 collect {} 之前调用
            //   因为 collect 在 launchIn() 中做了，所以可以在 launchIn() 之前通过 onEach {} 收集数据
            flow
                .onEach { appendMessage("onEach: $it") }
                .launchIn(this)
            appendMessage("done")
            // done（DefaultDispatcher-worker-1）
            // onEach: 1（DefaultDispatcher-worker-1）
            // onEach: 2（DefaultDispatcher-worker-1）
        }
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