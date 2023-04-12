/**
 * MediaPlayer（在 TextureView 上播放，可截图）
 *
 * 用 TextureView 的话效率相对较低，如果没有截图等特殊要求的话，建议用 SurfaceView，参见 MediaPlayerDemo1.kt
 */

package com.webabcd.androiddemo.view.media

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.webabcd.androiddemo.databinding.ActivityViewMediaMediaplayerdemo2Binding
import java.util.*


class MediaPlayerDemo2 : AppCompatActivity(), TextureView.SurfaceTextureListener,
    OnBufferingUpdateListener,
    OnCompletionListener,
    OnPreparedListener,
    OnErrorListener,
    OnInfoListener,
    OnSeekCompleteListener,
    OnVideoSizeChangedListener {

    private val _logTag = "MediaPlayerDemo2"
    private lateinit var mBinding: ActivityViewMediaMediaplayerdemo2Binding

    private var _surfaceTexture: SurfaceTexture? = null
    private var _mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityViewMediaMediaplayerdemo2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // 隐藏状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller!!.hide(WindowInsets.Type.statusBars())
        }

        mBinding.textureView.surfaceTextureListener = this

        mBinding.button1.setOnClickListener {
            playVideo()
        }

        mBinding.button2.setOnClickListener {
            stopVideo()
        }

        mBinding.button3.setOnClickListener {
            // 截图
            showFrameImage()
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {

        printLog(String.format(Locale.US, "onSurfaceTextureAvailable, width:%d, height:%d", width, height))

        if (_surfaceTexture == null) {
            _surfaceTexture = surface;
        }
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, width: Int, height: Int) {
        printLog(String.format(Locale.US, "onSurfaceTextureSizeChanged, width:%d, height:%d", width, height))
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        printLog("onSurfaceTextureDestroyed")
        return false
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
        // printLog("onSurfaceTextureUpdated")
    }


    private fun playVideo() {
        stopVideo()
        try {
            _mediaPlayer = MediaPlayer()
            _mediaPlayer!!.setSurface(Surface(_surfaceTexture))
            _mediaPlayer!!.setOnBufferingUpdateListener(this)
            _mediaPlayer!!.setOnPreparedListener(this)
            _mediaPlayer!!.setOnCompletionListener(this)
            _mediaPlayer!!.setOnErrorListener(this)
            _mediaPlayer!!.setOnInfoListener(this)
            _mediaPlayer!!.setOnSeekCompleteListener(this)
            _mediaPlayer!!.setOnVideoSizeChangedListener(this)

            _mediaPlayer!!.setDataSource("https://d2zihajmogu5jn.cloudfront.net/sintel/master.m3u8")
            _mediaPlayer!!.prepareAsync()
        } catch (ex: Exception) {
            printLog("mediaPlayer error: $ex")
        }
    }

    private fun stopVideo() {
        if (_mediaPlayer != null) {
            _mediaPlayer!!.stop()
            _mediaPlayer!!.release()
            _mediaPlayer = null
        }
    }

    override fun onDestroy() {
        stopVideo()
        super.onDestroy()
    }

    // 对 TextureView 截图
    private fun showFrameImage() {
        mBinding.imageView.visibility = View.VISIBLE
        val bitmap:Bitmap? = mBinding.textureView.bitmap // 对 TextureView 截图
        if (bitmap != null) {
            mBinding.imageView.setImageBitmap(bitmap)
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        printLog("onPrepared")
        _mediaPlayer!!.start()
    }

    override fun onInfo(p0: MediaPlayer?, what: Int, extra: Int): Boolean {
        printLog(String.format(Locale.US, "onInfo, what:%d, extra:%d", what, extra))
        return false
    }

    override fun onError(p0: MediaPlayer?, what: Int, extra: Int): Boolean {
        printLog(String.format(Locale.US, "onError, what:%d, extra:%d", what, extra))
        return false
    }

    override fun onVideoSizeChanged(p0: MediaPlayer?, width: Int, height: Int) {
        printLog(String.format(Locale.US, "onVideoSizeChanged, width:%d, height:%d", width, height))
    }

    override fun onSeekComplete(p0: MediaPlayer?) {
        printLog("onSeekComplete")
    }

    override fun onCompletion(p0: MediaPlayer?) {
        printLog("onCompletion")
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, percent: Int) {
        // printLog(String.format(Locale.US, "onBufferingUpdate, percent:%d", percent));
    }



    private fun printLog(message: String) {
        Log.d(_logTag, message)
        mBinding.txtLog.post(Runnable {
            mBinding.txtLog.append(message)
            mBinding.txtLog.append("\n")
            val offset: Int = mBinding.txtLog.lineCount * mBinding.txtLog.lineHeight
            if (offset > mBinding.txtLog.height) {
                mBinding.txtLog.scrollTo(0, offset - mBinding.txtLog.height)
            }
        })
    }
}