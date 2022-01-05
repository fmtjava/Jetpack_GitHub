package com.fmt.github

import android.app.Application
import android.content.ContextWrapper
import android.os.Debug
import com.fmt.github.tasks.*
import com.rousetime.android_startup.StartupManager

lateinit var mApplication: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        //启动器进行异步初始化
        StartupManager.Builder()
            .addStartup(InitBuGlyTask())
            .addStartup(InitKoInTask())
            .apply {
                if (BuildConfig.DEBUG) {
                    addStartup(InitDoKitTask())
                }
            }
            .addStartup(InitFlutterBoostTask())
            .addStartup(InitImageLoaderTask())
            .addStartup(InitSmartRefreshLayoutTask())
            .build(this)
            .start()
            .await()
    }
}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)
