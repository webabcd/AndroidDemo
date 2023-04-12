/**
 * MediaPlayer（视频中插广告）
 */

package com.webabcd.androiddemo.view.media

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.webabcd.androiddemo.databinding.ActivityViewMediaMediaplayerdemo3Binding
import java.util.*


class MediaPlayerDemo3 : AppCompatActivity(), SurfaceHolder.Callback,
    OnBufferingUpdateListener,
    OnCompletionListener,
    OnPreparedListener,
    OnErrorListener,
    OnInfoListener,
    OnSeekCompleteListener,
    OnVideoSizeChangedListener {

    private val _logTag = "MediaPlayerDemo3"
    private lateinit var mBinding: ActivityViewMediaMediaplayerdemo3Binding

    private lateinit var _surfaceHolder: SurfaceHolder
    private var _mediaPlayer: MediaPlayer? = null
    private var _adPlayer: AdPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityViewMediaMediaplayerdemo3Binding.inflate(layoutInflater)
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

        // 初始化广告播放器
        initAdPlayer()

        // 在播放正片的过程中插入广告
        mBinding.button1.setOnClickListener {
            // 暂停并隐藏正片
            _mediaPlayer!!.setDisplay(null)
            _mediaPlayer!!.pause()
            mBinding.button1.visibility = View.GONE

            // 显示并播放广告
            _adPlayer!!.play(_surfaceHolder)
        }
    }

    // 初始化广告播放器
    private fun initAdPlayer() {
        // 实例化广告播放器
        _adPlayer = AdPlayer(this)
        _adPlayer!!.setOnAdListener {
            // 广告播放完成后的回调
            if (it == "completion") {
                // 停止并隐藏广告
                _adPlayer!!.stop()

                // 显示并继续正片
                _mediaPlayer!!.setDisplay(_surfaceHolder)
                _mediaPlayer!!.start()
                mBinding.button1.visibility = View.VISIBLE

                initAdPlayer()
            }
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        printLog("surfaceChanged")
    }
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        printLog("surfaceDestroyed")
    }
    override fun surfaceCreated(p0: SurfaceHolder) {
        printLog("surfaceCreated")
        playVideo()
    }

    // 播放正片
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

    override fun onDestroy() {
        stopVideo()
        super.onDestroy()
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
        Log.d(_logTag, "video $message")
    }
}


// 广告播放器
class AdPlayer(context: Context):
    OnBufferingUpdateListener,
    OnCompletionListener,
    OnPreparedListener,
    OnErrorListener,
    OnInfoListener,
    OnSeekCompleteListener,
    OnVideoSizeChangedListener {

    private var _context: Context

    private val _logTag = "AdPlayer"
    private var _mediaPlayer: MediaPlayer

    init {
        _context = context

        _mediaPlayer = MediaPlayer()
        _mediaPlayer.setOnBufferingUpdateListener(this)
        _mediaPlayer.setOnPreparedListener(this)
        _mediaPlayer.setOnCompletionListener(this)
        _mediaPlayer.setOnErrorListener(this)
        _mediaPlayer.setOnInfoListener(this)
        _mediaPlayer.setOnSeekCompleteListener(this)
        _mediaPlayer.setOnVideoSizeChangedListener(this)

        try {
            val fd: AssetFileDescriptor = _context.assets.openFd("ad.mp4")
            _mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length);
            _mediaPlayer.prepareAsync()
        } catch (ex: Exception) {
            printLog("mediaPlayer error: $ex")
        }
    }

    // 在指定的 SurfaceView 上显示并播放广告
    fun play(sh: SurfaceHolder?) {
        _mediaPlayer.setDisplay(sh)
        _mediaPlayer.start()
    }

    // 停止广告并清理资源（之后如果再需要播放广告的话，则要重新实例化）
    fun stop() {
        setOnAdListener(null)
        _mediaPlayer.setDisplay(null)
        _mediaPlayer.stop()
        _mediaPlayer.release()
    }

    override fun onPrepared(p0: MediaPlayer?) {
        printLog("onPrepared")
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
        performAdStatus("completion")
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, percent: Int) {
        // printLog(String.format(Locale.US, "onBufferingUpdate, percent:%d", percent));
    }

    // 广告播放器的状态的回调
    var mOnAdStatusListener: ((String) -> Unit)? = null
    fun setOnAdListener(listener: ((String) -> Unit)?) {
        mOnAdStatusListener = listener
    }
    private fun performAdStatus(status: String) {
        if (mOnAdStatusListener != null) {
            mOnAdStatusListener!!(status)
        }
    }

    private fun printLog(message: String) {
        Log.d(_logTag, "ad $message")
    }
}
