package com.fmt.github.tasks

import android.content.Context
import com.fmt.github.config.Configs
import com.rousetime.android_startup.AndroidStartup
import com.tencent.bugly.crashreport.CrashReport

class InitBuGlyTask : AndroidStartup<Unit>() {//Bugly官方建议，初始化放在主线程

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = false

    override fun create(context: Context): Unit =
        CrashReport.initCrashReport(context, Configs.BUGLY_APP_ID, false)
}