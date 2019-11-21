package com.fmt.github

import android.app.Application
import android.content.ContextWrapper
import androidx.core.content.ContextCompat
import com.fmt.github.config.Configs
import com.fmt.github.di.appModule
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import org.koin.core.context.startKoin

lateinit var mApplication: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        CrashReport.initCrashReport(this, Configs.BUGLY_APP_ID, false)

        LiveEventBus.get()
            .config()
            .supportBroadcast(this)
            .lifecycleObserverAlwaysActive(true)
            .autoClear(false)

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(false)
            MaterialHeader(context).setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))
        }

        startKoin {
            modules(appModule)
        }
    }
}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)