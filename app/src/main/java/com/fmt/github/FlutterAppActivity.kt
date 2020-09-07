package com.fmt.github

import android.os.Bundle
import com.fmt.github.plugin.PluginManager
import io.flutter.embedding.android.FlutterActivity

class FlutterAppActivity : FlutterActivity() {

    var mInitParam: String? = null

    companion object {
        const val INIT_PARAMS = "initParams"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PluginManager.registerWith(flutterEngine!!.dartExecutor, this)
        mInitParam = intent.getStringExtra(INIT_PARAMS)
    }

    //设置显示Flutter对应页面的路由
    override fun getInitialRoute(): String =
        if (mInitParam.isNullOrEmpty()) super.getInitialRoute() else mInitParam!!
}