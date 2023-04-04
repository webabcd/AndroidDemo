/**
 * 截图
 */

package com.webabcd.androiddemo.view.media

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.webabcd.androiddemo.databinding.ActivityViewMediaScreenshotdemo1Binding

class ScreenshotDemo1 : AppCompatActivity() {

    private lateinit var mBinding: ActivityViewMediaScreenshotdemo1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityViewMediaScreenshotdemo1Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.button1.setOnClickListener{
            sample1()
        }

        mBinding.button2.setOnClickListener{
            sample2()
        }
    }

    // 截图（整个屏幕）
    fun sample1() {
        // 获取需要截图的屏幕
        val root = this.window.decorView
        // 按照需要截图的屏幕的尺寸初始化 bitmap
        val bitmap = Bitmap.createBitmap(root.width, root.height, Bitmap.Config.ARGB_8888)
        // 根据 bitmap 实例化 canvas
        val canvas = Canvas(bitmap)
        // 在 canvas 上绘制需要截图的屏幕，图片数据会保存到 canvas 的 bitmap 中
        root.draw(canvas)

        mBinding.imageView1.setImageBitmap(bitmap)
    }

    // 截图（指定的 View）
    fun sample2() {
        // 获取需要截图的 view
        val view = mBinding.button2
        // 按照需要截图的 view 的尺寸初始化 bitmap
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        // 根据 bitmap 实例化 canvas
        val canvas = Canvas(bitmap)
        // 在 canvas 上绘制需要截图的 view，图片数据会保存到 canvas 的 bitmap 中
        view.draw(canvas)

        mBinding.imageView1.setImageBitmap(bitmap)
    }
}