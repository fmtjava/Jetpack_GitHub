package com.fmt.github

import android.app.Application
import com.fmt.github.config.Configs
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.bugly.crashreport.CrashReport

class App : Application() {

    companion object {
        lateinit var mApplication: Application
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        CrashReport.initCrashReport(this, Configs.BUGLY_APP_ID, false)

        LiveEventBus.get()
            .config()
            .supportBroadcast(this)
            .lifecycleObserverAlwaysActive(true)
            .autoClear(false)
    }
}