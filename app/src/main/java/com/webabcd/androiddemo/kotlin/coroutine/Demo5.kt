/**
 * coroutine - 协程
 * 本例用于演示通过 ticker 信道实现类似计时器的效果，协程的异常处理，解决协程的并发问题
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.webabcd.androiddemo.databinding.ActivityKotlinCoroutineDemo5Binding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class Demo5 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinCoroutineDemo5Binding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinCoroutineDemo5Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 通过 ticker 信道实现类似计时器的效果
        mBinding.button1.setOnClickListener {
            sample1()
        }

        // 协程的异常处理
        mBinding.button2.setOnClickListener {
            sample2()
        }

        // 解决协程的并发问题
        mBinding.button3.setOnClickListener {
            sample3()
        }
    }

    fun sample1() {
        CoroutineScope(Dispatchers.Default).launch {
            // 通过 ticker() 创建一个信道，此信道会定时发送一个 Unit 用于实现类似计时器的效果
            val tickerChannel = ticker(delayMillis = 500, initialDelayMillis = 0)
            repeat(100) {
                tickerChannel.receive() // 本例中每 500 毫秒会收到一个 Unit
                appendMessage("tick")
                if (it > 2) {
                    tickerChannel.cancel() // 取消接收
                }
            }
        }
        // 06:08:51.179: tick（DefaultDispatcher-worker-1）
        // 06:08:51.634: tick（DefaultDispatcher-worker-1）
        // 06:08:52.128: tick（DefaultDispatcher-worker-1）
        // 06:08:52.628: tick（DefaultDispatcher-worker-1）
    }

    fun sample2() {
        CoroutineScope(Dispatchers.Default).launch {
            /*
            launch {
                // 在此协程外，你是 catch 不到此异常的，程序会崩溃
                // 需要的话，请在此协程内写 try/catch
                throw Exception()
            }
            */

            // 对于 GlobalScope.launch() 启动的顶级协程来说，你可以通过如下方式 catch 异常
            val myExceptionHandler = CoroutineExceptionHandler { _, exception ->
                appendMessage("myExceptionHandler: $exception")
            }
            GlobalScope.launch(Dispatchers.Default + myExceptionHandler) {
                throw Exception("launch exception")
            }

            // async/await 异常的捕获方法如下
            val deferred = GlobalScope.async {
                throw Exception()
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                appendMessage("async/await exception")
            }
        }
    }

    // 用于模拟并发问题
    suspend fun myFun(action: suspend () -> Unit) = coroutineScope {
        // 启动 200 个协程，因为协程可能会被分配到不同的线程，所以协程也是有并发问题的
        repeat(200) {
            launch {
                // 每个协程执行 1000 次累加
                repeat(1000) {
                    action()
                }
            }
        }
    }
    fun sample3() {
        var counter1 = 0
        val counter2 = AtomicInteger()
        var counter3 = 0
        var counter4 = 0

        val mutex = Mutex()

        CoroutineScope(Dispatchers.Default).launch {
            // 这么做是有并发问题的
            myFun {
                counter1++
            }
            appendMessage("counter1: $counter1")

            // 使用支持原子操作的对象，就不会有并发问题了，而且效率很高
            myFun {
                counter2.incrementAndGet()
            }
            appendMessage("counter2: $counter2")

            // 强制要求只在指定的线程执行，就不会有并发问题了，但是效率比较低
            val myThread = newSingleThreadContext("myThread")
            withContext(Dispatchers.Default) {
                myFun {
                    withContext(myThread) {
                        counter3++
                    }
                }
            }
            appendMessage("counter3: $counter3")

            // 通过互斥锁避免并发问题，常规做法，效率一般
            myFun {
                mutex.withLock {
                    counter4++
                }
            }
            appendMessage("counter4: $counter4")
        }
        // 06:39:35.580: counter1: 195130（DefaultDispatcher-worker-2）   // 有并发问题
        // 06:39:35.683: counter2: 200000（DefaultDispatcher-worker-2）   // 使用支持原子操作的对象，效率很高，本例用了 100 毫秒左右完成
        // 06:39:49.887: counter3: 200000（DefaultDispatcher-worker-1）   // 强制要求只在指定的线程执行，效率比较低，本例用了 1420 毫秒左右完成
        // 06:39:55.249: counter4: 200000（DefaultDispatcher-worker-2）   // 通过互斥锁避免并发问题，常规做法，效率一般，本例用了 540 毫秒左右完成
    }



    fun appendMessage(message: String) {
        val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
        val time = dateFormat.format(Date());
        val threadName = Thread.currentThread().name

        CoroutineScope(Dispatchers.Main).launch{
            val log = "$time: $message（$threadName）"
            mBinding.textView1.append(log);
            mBinding.textView1.append("\n");

            Log.d("coroutine", log)
        }
    }
}