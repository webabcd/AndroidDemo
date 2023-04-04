/**
 * MediaPlayer（在 SurfaceView 上播放）
 *
 * 注：无法对 SurfaceView 截图，如果需要对视频截图的话可以使用 TextureView，参见 MediaPlayerDemo2.kt
 */

package com.webabcd.androiddemo.view.media

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.webabcd.androiddemo.databinding.ActivityViewMediaMediaplayerdemo1Binding
import java.util.*
import kotlin.math.ceil
import kotlin.math.max


class MediaPlayerDemo1 : AppCompatActivity(), SurfaceHolder.Callback,
    OnBufferingUpdateListener,
    OnCompletionListener,
    OnPreparedListener,
    OnErrorListener,
    OnInfoListener,
    OnSeekCompleteListener,
    OnVideoSizeChangedListener {

    private val _logTag = "MediaPlayerDemo1"
    private lateinit var mBinding: ActivityViewMediaMediaplayerdemo1Binding

    private lateinit var _surfaceHolder: SurfaceHolder
    private var _mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityViewMediaMediaplayerdemo1Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // 隐藏状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller!!.hide(WindowInsets.Type.statusBars())
        }

        _surfaceHolder = mBinding.surfaceView.holder
        _surfaceHolder.addCallback(this)

        mBinding.button1.setOnClickListener {
            playVideo()
        }

        mBinding.button2.setOnClickListener {
            stopVideo()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }
    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }
    override fun surfaceCreated(p0: SurfaceHolder) {
        printLog("surface created")
    }

    private fun playVideo() {
        stopVideo()
        try {
            _mediaPlayer = MediaPlayer()
            _mediaPlayer!!.setDisplay(_surfaceHolder)
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

    override fun onPrepared(p0: MediaPlayer?) {
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
