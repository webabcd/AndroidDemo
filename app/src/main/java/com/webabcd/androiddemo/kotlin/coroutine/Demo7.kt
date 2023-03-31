/**
 * flow - 异步流
 *
 * 本例用于演示
 * 1、优化发送/接收数据（buffer, conflate, collectLatest）
 * 2、数组转换为 flow 以及 flow 的数据处理相关的操作符（drop, take, filter, map, transform, onEach, first, last, single, reduce 等）
 * 3、将两个 flow 组合为一个 flow（zip, combine）
 * 4、flow 内嵌套 flow（flatMapConcat, flatMapMerge）
 */

package com.webabcd.androiddemo.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.webabcd.androiddemo.databinding.ActivityKotlinCoroutineDemo7Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Demo7 : AppCompatActivity() {

    private lateinit var mBinding: ActivityKotlinCoroutineDemo7Binding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinCoroutineDemo7Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 优化发送/接收数据（buffer, conflate, collectLatest）
        mBinding.button1.setOnClickListener {
            sample1()
        }

        // 数组转换为 flow 以及 flow 的数据处理相关的操作符（drop, take, filter, map, transform, onEach, first, last, single, reduce 等）
        mBinding.button2.setOnClickListener {
            sample2()
        }

        // 将两个 flow 组合为一个 flow（zip, combine）
        mBinding.button3.setOnClickListener {
            sample3()
        }

        // flow 内嵌套 flow（flatMapConcat, flatMapMerge）
        mBinding.button4.setOnClickListener {
            sample4()
        }
    }

    fun sample1() {
        val flow = flow {
            for (i in 1..3) {
                delay(200)
                emit(i)
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            // 常规写法，发送数据会阻塞，直到发送的数据被接收为止；接收数据会阻塞，直到接收到数据为止
            flow
                .collect { value ->
                    delay(500)
                    appendMessage("$value")
                }
            // 每 700 毫秒会收到一条数据
            // 03:07:39.761: 1（DefaultDispatcher-worker-1）
            // 03:07:40.481: 2（DefaultDispatcher-worker-1）
            // 03:07:41.188: 3（DefaultDispatcher-worker-1）


            // 通过 buffer() 设置一个缓冲区，可以指定缓冲区保存数据的最大条数
            flow
                .buffer(64)
                .collect { value ->
                    delay(500)
                    appendMessage("$value")
                }
            // 每 500 毫秒会收到一条数据
            // 03:07:41.937: 1（DefaultDispatcher-worker-2）
            // 03:07:42.445: 2（DefaultDispatcher-worker-2）
            // 03:07:42.958: 3（DefaultDispatcher-worker-2）


            // 通过 conflate() 可以实现，在你来不及接收数据的时候丢弃数据
            flow
                .conflate()
                .collect { value ->
                    delay(500)
                    appendMessage("$value")
                }
            // emit 第 2 条数据时，collect 正在忙其他事情来不及接收，所以就丢弃第 2 条数据了
            // 03:07:43.725: 1（DefaultDispatcher-worker-2）
            // 03:07:44.230: 3（DefaultDispatcher-worker-2）


            // 通过 collectLatest {} 可以实现，在发来的数据你来不及接收的时候，立刻停止你正在忙的工作，马上去从接收数据开始重新走一遍逻辑（相当于重启接收了）
            flow
                .collectLatest { value ->
                    appendMessage("collect: $value")
                    delay(300)
                    appendMessage("done: $value")
                }
            // 在发来的数据你来不及接收的时候，立刻停止你正在忙的工作，马上去从接收数据开始重新走一遍逻辑（相当于重启接收了）
            // 03:07:44.450: collect: 1（DefaultDispatcher-worker-2）
            // 03:07:44.662: collect: 2（DefaultDispatcher-worker-1）
            // 03:07:44.866: collect: 3（DefaultDispatcher-worker-1）
            // 03:07:45.168: done: 3（DefaultDispatcher-worker-1）
        }
    }

    fun myMap(input: Int): String {
        return "map $input"
    }
    fun myFilter(input: Int): Boolean {
        return input > 28
    }
    fun sample2() {
        // asFlow() - 数组转 flow
        val a = (1..100).asFlow()
        // flowOf() - 数组转 flow
        val b = flowOf(1..100)

        CoroutineScope(Dispatchers.Default).launch {
            (1..100)
                .asFlow()
                .drop(10)       // 舍弃前 n 条数据
                .take(20)       // 只获取前 n 条数据
                .filter { value ->    // 只获取符合条件的数据
                    myFilter(value)
                }.map { value ->      // 按照自定义逻辑转换数据
                    myMap(value)
                }.onEach { value ->   // 在数据发给 collect {} 之前调用
                    appendMessage("onEach: $value")
                }.collect { value ->
                    appendMessage("collect: $value")
                }
            // onEach: map 29（DefaultDispatcher-worker-1）
            // collect: map 29（DefaultDispatcher-worker-1）
            // onEach: map 30（DefaultDispatcher-worker-1）
            // collect: map 30（DefaultDispatcher-worker-1）


            (1..2)
                .asFlow()
                .transform { value ->   // 按照自定义逻辑处理数据，你可以转换数据并决定是否发送数据
                    // 发送你换后的数据，不调用 emit() 的话收集器就收不到数据了
                    emit( "transform: $value")
                }.collect { value ->
                    appendMessage(value)
                }
            // transform: 1（DefaultDispatcher-worker-1）
            // transform: 2（DefaultDispatcher-worker-1）


            var result =
                (1..5)
                    .asFlow()           // flow 支持 first(), last(), single() 之类的方法，都比较简单，就不演示了
                    .reduce { a, b ->   // 统计数据，本例演示了如何通过 reduce 实现数据累加的功能
                        appendMessage("reduce: $a, $b")
                        a + b
                    }
            appendMessage("result: $result")
            // reduce: 1, 2（DefaultDispatcher-worker-1）
            // reduce: 3, 3（DefaultDispatcher-worker-1）
            // reduce: 6, 4（DefaultDispatcher-worker-1）
            // reduce: 10, 5（DefaultDispatcher-worker-1）
            // result: 15（DefaultDispatcher-worker-1）
        }
    }

    fun sample3() {
        CoroutineScope(Dispatchers.Default).launch {
            // 这里的 onEach 用于模拟长时任务
            val flow1 = flowOf(1, 2).onEach { delay(100) }
            // 这里的 onEach 用于模拟长时任务
            val flow2 = flowOf("a", "b", "c").onEach { delay(500) }

            // zip() - 用于把两个 flow 组合成一个 flow
            flow1.zip(flow2) { a, b -> // 两个 flow 会互相等待，等两个 flow 都收到了数据时，就会走到这里，有一个 flow 退出了组合体就会退出
                "$a -> $b"
            }.collect { appendMessage(it) }
            // 03:24:48.873: 1 -> a（DefaultDispatcher-worker-2）
            // 03:24:49.351: 2 -> b（DefaultDispatcher-worker-2）


            // combine() - 用于把两个 flow 组合成一个 flow
            flow1.combine(flow2) { a, b -> // 两个 flow 分别接收数据，接收慢的 flow 收到数据时会走到这里（接收快的那个此处会保存最新接收的值），两个 flow 都退出了组合体就会退出
                "$a -> $b"
            }.collect { appendMessage(it) }
            // 03:24:49.915: 2 -> a（DefaultDispatcher-worker-1）
            // 03:24:50.421: 2 -> b（DefaultDispatcher-worker-1）
            // 03:24:50.922: 2 -> c（DefaultDispatcher-worker-1）

        }
    }

    fun myFlow(i: Int): Flow<String> = flow {
        delay(300)
        emit("emit: $i")
    }
    fun sample4() {
        CoroutineScope(Dispatchers.Default).launch {
            (1..3)
                .asFlow()
                .onEach { delay(500) } // 这里的 onEach 用于模拟长时任务
                .flatMapConcat { // 在 flow 内嵌套另一个 flow，主 flow 和 嵌套 flow 是顺序执行的
                    myFlow(it)
                }
                .collect { value ->
                    appendMessage("$value")
                }
            // 03:30:18.268: emit: 1（DefaultDispatcher-worker-1）
            // 03:30:19.078: emit: 2（DefaultDispatcher-worker-1）
            // 03:30:19.886: emit: 3（DefaultDispatcher-worker-1）


            (1..3)
                .asFlow()
                .onEach { delay(500) } // 这里的 onEach 用于模拟长时任务
                .flatMapMerge(64) { // 在 flow 内嵌套另一个 flow，主 flow 和 嵌套 flow 是并行执行的，可以指定最大并发数
                    myFlow(it)
                }
                .collect { value ->
                    appendMessage("$value")
                }
            // 03:30:20.784: emit: 1（DefaultDispatcher-worker-2）
            // 03:30:21.278: emit: 2（DefaultDispatcher-worker-1）
            // 03:30:21.778: emit: 3（DefaultDispatcher-worker-2）
        }
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