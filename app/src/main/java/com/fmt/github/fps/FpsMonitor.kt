package com.fmt.github.fps

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.fmt.github.AppContext
import com.fmt.github.R
import com.fmt.github.ext.infoToast
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.fmt.github.utils.ActivityManager
import java.text.DecimalFormat

object FpsMonitor {

    private val fpsViewer = FpsViewer()

    fun toggle() {
        fpsViewer.toggle()
    }

    private class FpsViewer {
        //初始化悬浮窗布局
        private var fpsView =
            LayoutInflater.from(AppContext).inflate(R.layout.layout_fps, null, false) as TextView

        //判断悬浮窗是否开启
        private var isPlaying = false
        private val frameMonitor = FrameMonitor()
        private val decimal = DecimalFormat("#.0 fps")
        private val windowManager =
            AppContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        //初始化悬浮窗参数
        private val windowLayoutParams = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.END or Gravity.CENTER
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_TOAST
            }
        }

        val fpsCallback = object : FpsCallback {
            override fun onFrame(fps: Double) {
                //将Fps实时展示到悬浮窗中
                fpsView.text = decimal.format(fps)
            }
        }

        init {
            //根据App是否处于前台判断是否显示悬浮窗
            ActivityManager.instance.addFrontBackCallback(object :
                ActivityManager.FrontBackCallback {
                override fun onChanged(front: Boolean) {
                    if (front) {
                        start()
                    } else {
                        stop()
                    }
                }
            })
        }

        //开启悬浮窗
        private fun start() {
            if (!hasOverlayPermission()) {
                startOverlaySettingActivity()
                infoToast(R.string.open_overlay_tip)
                return
            }
            frameMonitor.addFpsCallback(fpsCallback)
            frameMonitor.start()
            if (!isPlaying) {
                isPlaying = true
                windowManager.addView(fpsView, windowLayoutParams)
            }
        }

        //关闭悬浮窗
        private fun stop() {
            frameMonitor.stop()
            if (isPlaying) {
                isPlaying = false
                windowManager.removeView(fpsView)
            }
        }

        fun toggle() {
            isPlaying.yes {
                stop()
            }.otherwise {
                start()
            }
        }
    }

    //是否有悬浮窗权限
    fun hasOverlayPermission() =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(AppContext)

    //引导用户开启悬浮窗
    private fun startOverlaySettingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppContext.startActivity(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${AppContext.packageName}")
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    interface FpsCallback {
        fun onFrame(fps: Double)
    }
}