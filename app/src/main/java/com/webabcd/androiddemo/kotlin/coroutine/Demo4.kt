/**
 * Channel - 信道，用于在不同协程之间传输数据
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webabcd.androiddemo.R
import kotlinx.android.synthetic.main.activity_kotlin_coroutine_demo3.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.util.*

class Demo4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_demo4)

        // 通过信道在不同协程之间发送和接收数据
        button1.setOnClickListener {
            sample1()
        }

        // 生产者/消费者模式
        button2.setOnClickListener {
            sample2()
        }

        // 多个协程使用一个信道发送数据
        button3.setOnClickListener {
            sample3()
        }

        // 多个协程使用一个信道接收数据
        button4.setOnClickListener {
            sample4()
        }

        // 信道缓冲区
        button5.setOnClickListener {
            sample5()
        }

        // 从多个信道同时接收数据
        button6.setOnClickListener {
            sample6()
        }
    }

    fun sample1() {
        CoroutineScope(Dispatchers.Default).launch {
            // 创建一个信道，并指定此信道传输的数据类型
            // 注：Channel 继承了 SendChannel（用于发送数据）和 ReceiveChannel（用于接收数据）
            val channel = Channel<String>()
            this.launch {
                for (i in 1..3) {
                    delay(500)
                    appendMessage("send: $i")
                    // 发送数据（注：在没有缓冲区的情况下，发送数据会阻塞，直到发送的数据被接收为止）
                    channel.send("send: $i")
                }
                // 关闭发送（close() 来自 SendChannel）
                channel.close()
                // 关闭接收（cancel() 来自 ReceiveChannel）
                // channel.cancel()
            }

            // 接收数据（接收数据会阻塞，直到接收到数据为止）
            appendMessage("recv: ${channel.receive()}")
            // 取消所有子协程（取消协程后，协程中的信道也会自动关闭）
            // coroutineContext.cancelChildren()

            // 迭代方式接收数据，信道关闭后则自动退出迭代
            for (msg in channel) {
                appendMessage("recv: $msg")
            }
            appendMessage("done")
        }
        // send: 1（DefaultDispatcher-worker-2）
        // recv: send: 1（DefaultDispatcher-worker-1）
        // send: 2（DefaultDispatcher-worker-1）
        // recv: send: 2（DefaultDispatcher-worker-1）
        // send: 3（DefaultDispatcher-worker-2）
        // recv: send: 3（DefaultDispatcher-worker-1）
        // done（DefaultDispatcher-worker-2）
    }

    // produce { } - 创建一个生产者（用于发送数据）
    fun CoroutineScope.myProduce(): ReceiveChannel<String> = produce {
        for (i in 1..3) {
            delay(500)
            appendMessage("send: $i")
            channel.send("send: $i")
        }
        // 关闭发送
        channel.close()
    }
    fun sample2() {
        CoroutineScope(Dispatchers.Default).launch {
            val myProduce = myProduce()
            // consumeEach { } - 创建一个消费者（用于接收数据）
            myProduce.consumeEach {
                appendMessage("recv: $it")
            }
            appendMessage("done")
        }
        // send: 1（DefaultDispatcher-worker-2）
        // recv: send: 1（DefaultDispatcher-worker-2）
        // send: 2（DefaultDispatcher-worker-2）
        // recv: send: 2（DefaultDispatcher-worker-2）
        // send: 3（DefaultDispatcher-worker-2）
        // recv: send: 3（DefaultDispatcher-worker-1）
        // done（DefaultDispatcher-worker-2）
    }

    suspend fun sendString(channel: SendChannel<String>, s: String) {
        while (true) {
            delay(500)
            appendMessage("send: $s")
            channel.send(s)
        }
    }
    fun sample3() {
        CoroutineScope(Dispatchers.Default).launch {
            val channel = Channel<String>()
            // 多个协程使用一个信道发送数据
            launch { sendString(channel, "aaa") }
            launch { sendString(channel, "bbb") }

            repeat(3) {
                appendMessage("recv: ${channel.receive()}")
            }
            coroutineContext.cancelChildren()
        }
        // send: bbb（DefaultDispatcher-worker-1）
        // send: aaa（DefaultDispatcher-worker-2）
        // recv: aaa（DefaultDispatcher-worker-2）
        // recv: bbb（DefaultDispatcher-worker-1）
        // send: aaa（DefaultDispatcher-worker-1）
        // send: bbb（DefaultDispatcher-worker-2）
        // recv: bbb（DefaultDispatcher-worker-2）
    }

    fun CoroutineScope.launchReceiver(receiverId: Int, channel: ReceiveChannel<String>) = launch {
        for (msg in channel) {
            appendMessage("receiverId:$receiverId, msg:$msg")
        }
    }
    fun sample4() {
        CoroutineScope(Dispatchers.Default).launch {
            val myProduce = produce {
                for (i in 1..100) {
                    delay(500)
                    appendMessage("send: $i")
                    channel.send("send: $i")
                }
            }
            // 多个协程使用一个信道接收数据
            repeat(3) {
                launchReceiver(it, myProduce)
            }
            delay(1800)
            // 关闭接收
            myProduce.cancel()
        }
        // send: 1（DefaultDispatcher-worker-1）
        // receiverId:2, msg:send: 1（DefaultDispatcher-worker-1）
        // send: 2（DefaultDispatcher-worker-2）
        // receiverId:0, msg:send: 2（DefaultDispatcher-worker-2）
        // send: 3（DefaultDispatcher-worker-2）
        // receiverId:1, msg:send: 3（DefaultDispatcher-worker-1）
    }

    fun sample5() {
        CoroutineScope(Dispatchers.Default).launch {
            // 创建信道时可以指定信道缓冲区保存数据的最大条数（不指定的话，默认没有缓冲区）
            // 1、在没有缓冲区的情况下，发送数据会阻塞，直到发送的数据被接收为止
            // 2、在有缓冲区的情况下，缓冲区满后，发送数据会阻塞，直到缓冲区不再满为止
            val channel = Channel<String>(5)
            val job = launch {
                repeat(10) {
                    appendMessage("send: $it")
                    channel.send("send: $it")
                }
            }
            delay(1000)
            job.cancel()
            appendMessage("done")
        }
        // send: 0（DefaultDispatcher-worker-2）
        // send: 1（DefaultDispatcher-worker-2）
        // send: 2（DefaultDispatcher-worker-2）
        // send: 3（DefaultDispatcher-worker-2）
        // send: 4（DefaultDispatcher-worker-2）
        // send: 5（DefaultDispatcher-worker-2）
        // done（DefaultDispatcher-worker-2）
    }

    fun sample6() {
        CoroutineScope(Dispatchers.Default).launch {
            val channel1 = fun1()
            val channel2 = fun2()

            repeat(10) {
                // 因为从信道接收数据是要阻塞的，直到接收到数据为止
                // 所以，当你需要从多个信道接收数据时，一般来说要先从一个信道阻塞并接收，收到之后再从另一个信道阻塞并接收，这样就会导致不及时的问题
                // 这种场景下，可以使用 select { } 同时从多个信道阻塞并接收，有一个信道收到了数据就退出
                select<Unit> {
                    channel1.onReceive { value ->
                        appendMessage(value)
                    }
                    channel2.onReceive { value ->
                        appendMessage(value)
                    }
                }
            }

            // 取消 channel1 协程和 channel2 协程，协程中的信道会自动关闭
            // 当然，也可以通过 channel1.cancel() 和 channel2.cancel() 关闭接收
            coroutineContext.cancelChildren()
        }
        // 05:54:54.448: fun1 0（DefaultDispatcher-worker-1）
        // 05:54:54.626: fun1 1（DefaultDispatcher-worker-1）
        // 05:54:54.693: fun2 0（DefaultDispatcher-worker-1）
        // 05:54:54.826: fun1 2（DefaultDispatcher-worker-1）
        // 05:54:55.029: fun1 3（DefaultDispatcher-worker-1）
        // 05:54:55.194: fun2 1（DefaultDispatcher-worker-1）
        // 05:54:55.235: fun1 4（DefaultDispatcher-worker-1）
        // 05:54:55.438: fun1 5（DefaultDispatcher-worker-1）
        // 05:54:55.640: fun1 6（DefaultDispatcher-worker-1）
        // 05:54:55.696: fun2 2（DefaultDispatcher-worker-1）
    }
    fun CoroutineScope.fun1() = produce<String> {
        repeat (100) {
            delay(200)
            send("fun1 $it")
        }
    }
    fun CoroutineScope.fun2() = produce<String> {
        repeat (100) {
            delay(500)
            send("fun2 $it")
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