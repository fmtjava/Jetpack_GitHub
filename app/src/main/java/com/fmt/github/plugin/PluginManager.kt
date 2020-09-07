package com.fmt.github.plugin

import android.app.Activity
import com.fmt.github.config.Configs
import com.fmt.github.repos.activity.ReposDetailActivity.Companion.OWNER
import com.fmt.github.repos.activity.ReposDetailActivity.Companion.REPO
import com.fmt.github.repos.activity.ReposDetailActivity.Companion.WEB_URL
import com.fmt.github.repos.activity.go2ReposDetailActivity
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Android与Flutter通信管理类
 */
class PluginManager private constructor(val activity: Activity) : MethodChannel.MethodCallHandler {

    companion object {
        fun registerWith(messenger: BinaryMessenger, activity: Activity) {
            val methodChannel = MethodChannel(messenger, "MethodChannelPlugin")
            val pluginManager = PluginManager(activity)
            methodChannel.setMethodCallHandler(pluginManager)
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "go2ReposDetail" -> {
                go2ReposDetail(call.arguments<Map<String, String>>())
                //result.success() 如果需要回传数据给Flutter，可使用此方法
            }
            else -> result.notImplemented()
        }
    }

    private fun go2ReposDetail(arguments: Map<String, String?>) {
        go2ReposDetailActivity(
            activity, "${Configs.GITHUB_BASE_URL}${arguments[WEB_URL]}",
            arguments[REPO] ?: error(""), arguments[OWNER] ?: error("")
        )
    }

}

