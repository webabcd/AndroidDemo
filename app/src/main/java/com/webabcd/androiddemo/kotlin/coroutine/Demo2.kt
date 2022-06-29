/**
 * coroutine - 协程
 * 本利用于演示 Job 的等待与取消，超时处理，取消协程
 *
 * 注：async 返回的是 Deferred<T> 对象，其继承自 Job，所以关于 Deferred<T> 的等待与取消和超时处理等与 Job 是一样的
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo2.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class Demo2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo2)

        // 等待 Job 执行完
        button1.setOnClickListener {
            sample1()
        }

        // 取消 Job
        button2.setOnClickListener {
            sample2()
        }

        // 取消 Job 需要强调的知识点（声明为 suspend 的函数才可以检查取消）
        button3.setOnClickListener {
            sample3()
        }

        // 取消无 suspend 函数的 Job
        button4.setOnClickListener {
            sample4()
        }

        // 超时处理
        button5.setOnClickListener {
            sample5()
        }

        // 取消协程
        button6.setOnClickListener {
            sample6()
        }
    }

    fun sample1() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * Job - 通过 launch 返回的对象
             *   join() - 阻塞，直到 Job 执行完，包括 Job 中的所有子 Job（除了 GlobalScope）
             *   cancel() - 取消 Job，如果一个 Job 里有子 Job，那么这些子 Job 也都会被取消（除了 GlobalScope）
             *   cancelAndJoin() - 先 cancel() 然后 join()
             * 注：Job 的上述方法必须在协程中或 suspend 函数中调用
             */
            val job = launch {
                delay(1000)
                appendMessage("b")
            }
            appendMessage("a")
            job.join()
            appendMessage("c")
        }
        // a（DefaultDispatcher-worker-2）
        // b（DefaultDispatcher-worker-2）
        // c（DefaultDispatcher-worker-2）
    }

    fun sample2() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * Job - 通过 launch 返回的对象
             *   join() - 阻塞，直到 Job 执行完，包括 Job 中的所有子 Job（除了 GlobalScope）
             *   cancel() - 取消 Job，如果一个 Job 里有子 Job，那么这些子 Job 也都会被取消（除了 GlobalScope）
             *   cancelAndJoin() - 先 cancel() 然后 join()
             * 注：Job 的上述方法必须在协程中或 suspend 函数中调用
             *
             * 另外：可以通过 joinAll(job1, job2, job3...) 同时 join 多个 Job
             */
            val job = launch {
                repeat(1000) { i ->
                    appendMessage("heartbeat $i ...")
                    delay(500)
                }
            }
            delay(1300)
            job.cancel()
            job.join()
            // job.cancelAndJoin()
            appendMessage("done")
        }
        // heartbeat 0 ...（DefaultDispatcher-worker-1）
        // heartbeat 1 ...（DefaultDispatcher-worker-1）
        // heartbeat 2 ...（DefaultDispatcher-worker-1）
        // done（DefaultDispatcher-worker-1）
    }

    fun sample3() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * 调用 Job 对象的 cancel() 方法，会触发 CancellationException 异常
             * 这里有个前提，就是你运行的代码必须是可以检查取消的（声明为 suspend 的函数是可以检查取消的）
             * 注：kotlinx.coroutines.* 中定义的方法都是 suspend 的
             */
            val job = launch {
                repeat(1000) { i ->
                    try {
                        appendMessage("heartbeat $i ...")
                        delay(500)
                    } catch (e: CancellationException) {
                        // 运行到 suspend 函数时，如果发现取消了，则会抛出异常，你的 Job 就退出了
                        // 所有处理程序都会忽略 CancellationException 异常（也就是说抛出此异常并不会导致崩溃），因为它就是用于退出 Job 用的
                        throw e
                    }
                }
            }
            delay(1300)
            job.cancelAndJoin()
            appendMessage("done")
        }
        // heartbeat 0 ...（DefaultDispatcher-worker-1）
        // heartbeat 1 ...（DefaultDispatcher-worker-1）
        // heartbeat 2 ...（DefaultDispatcher-worker-1）
        // done（DefaultDispatcher-worker-1）
    }

    fun sample4() {
        /**
         * 如果你需要取消的 Job 中没有 suspend 该怎么办呢？有两种办法：
         * 1、定期调用一个 suspend 函数去检查取消情况
         * 2、定期通过 CoroutineScope 的 isActive 检查 Job 是否是活动状态
         */
        CoroutineScope(Dispatchers.Default).launch {
            val job = launch {
                var time = System.currentTimeMillis()
                var i = 0
                /**
                 * this 是一个 CoroutineScope 对象
                 *   isCompleted - 是否运行完成
                 *   isCancelled - 是否已取消
                 *   isActive - 是否是活动状态（尚未完成且尚未取消）
                 */
                while (this.isActive) {
                    if (System.currentTimeMillis() > time + 500) {
                        appendMessage("heartbeat ${i++} ...")
                        time = System.currentTimeMillis()
                    }
                }
            }
            delay(1300L)
            job.cancelAndJoin()
            appendMessage("done")
        }
        // heartbeat 0 ...（DefaultDispatcher-worker-2）
        // heartbeat 1 ...（DefaultDispatcher-worker-2）
        // done（DefaultDispatcher-worker-2）
    }

    fun sample5() {
        CoroutineScope(Dispatchers.Default).launch {
            /**
             * withTimeout() - 超时判断，如果超时则触发 TimeoutCancellationException 异常
             * withTimeoutOrNull() - 超时判断，如果超时则返回 null（不会触发异常）
             * 注：上述方法必须在协程中或 suspend 函数中调用
             */
            try {
                val result = withTimeout(1300) {
                    repeat(1000) { i ->
                        appendMessage("a $i ...")
                        delay(500)
                    }
                    "a done" // 未超时的返回值（超时则触发异常）
                }
                appendMessage("$result")
            } catch (e: TimeoutCancellationException) {
                appendMessage(e.toString())
            }
            // a 0 ...（DefaultDispatcher-worker-1）
            // a 1 ...（DefaultDispatcher-worker-1）
            // a 2 ...（DefaultDispatcher-worker-1）
            // kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 1300 ms（DefaultDispatcher-worker-1）

            try {
                val result = withTimeout(5000) {
                    repeat(2) { i ->
                        appendMessage("b $i ...")
                        delay(500)
                    }
                    "b done" // 未超时的返回值（超时则触发异常）
                }
                appendMessage("$result")
            } catch (e: TimeoutCancellationException) {
                appendMessage(e.toString())
            }
            // b 0 ...（DefaultDispatcher-worker-1）
            // b 1 ...（DefaultDispatcher-worker-1）
            // b done（DefaultDispatcher-worker-1）

            val result = withTimeoutOrNull(1300) {
                repeat(1000) { i ->
                    appendMessage("c $i ...")
                    delay(500)
                }
                "c done" // 未超时的返回值（超时则返回 null）
            }
            appendMessage("$result")
            // c 0 ...（DefaultDispatcher-worker-1）
            // c 1 ...（DefaultDispatcher-worker-1）
            // c 2 ...（DefaultDispatcher-worker-1）
            // null（DefaultDispatcher-worker-1）

            val result2 = withTimeoutOrNull(5000) {
                repeat(2) { i ->
                    appendMessage("d $i ...")
                    delay(500)
                }
                "d done" // 未超时的返回值（超时则返回 null）
            }
            appendMessage("$result2")
            // d 0 ...（DefaultDispatcher-worker-1）
            // d 1 ...（DefaultDispatcher-worker-1）
            // d done（DefaultDispatcher-worker-1）
        }
    }

    /**
     * CoroutineScope 是可取消的，记得在不需要的时候要取消掉 CoroutineScope
     * 取消 CoroutineScope 后，其内所有子也会被取消（但是如果子是 GlobalScope 则不会被取消）
     * 取消 CoroutineScope 的操作并不要求非要在协程中或 suspend 函数中调用
     * 取消 CoroutineScope 时的检查取消要求和 Job 是类似的，请参见 sample3(), sample4()
     */
    private var mainScope = MainScope()
    fun sample6() {
        mainScope.launch {
            repeat(1000) { i ->
                appendMessage("heartbeat $i ...")
                delay(500)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
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