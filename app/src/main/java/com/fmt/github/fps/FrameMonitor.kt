package com.fmt.github.fps

import android.view.Choreographer
import java.util.concurrent.TimeUnit

/**
 * 对界面绘制的每一帧进行监听
 */
internal class FrameMonitor : Choreographer.FrameCallback {

    private val choreographer = Choreographer.getInstance()
    private var startFrameTime = 0L//上一帧的绘制时间
    private var frameCount = 0//每1s内绘制的总帧数
    private val callBackList = arrayListOf<FpsMonitor.FpsCallback>()//Fps回调监听集合

    //每一帧的绘制回调
    override fun doFrame(frameTimeNanos: Long) {
        //获取当前帧的耗时
        val currentTime = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)
        if (startFrameTime > 0) {
            //累计每1s绘制多少帧
            frameCount++

            val diffTime = currentTime - startFrameTime
            if (diffTime > 1000) {
                val fps = frameCount * 1000 / diffTime.toDouble()
                callBackList.forEach {
                    it.onFrame(fps)
                }
                frameCount = 0
                startFrameTime = currentTime
            }
        } else {
            startFrameTime = currentTime
        }
        start()
    }

    //开启监听
    fun start() {
        choreographer.postFrameCallback(this)
    }

    //结束监听
    fun stop() {
        choreographer.removeFrameCallback(this)
        startFrameTime = 0L
        frameCount = 0
        callBackList.clear()
    }

    //回调监听
    fun addFpsCallback(callback: FpsMonitor.FpsCallback) {
        if (!callBackList.contains(callback))
            callBackList.add(callback)
    }
}