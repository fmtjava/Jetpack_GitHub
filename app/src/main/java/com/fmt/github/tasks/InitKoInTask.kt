package com.fmt.github.tasks

import android.content.Context
import com.fmt.github.di.appModule
import com.rousetime.android_startup.AndroidStartup
import org.koin.core.context.startKoin

class InitKoInTask : AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = false

    override fun waitOnMainThread(): Boolean = false

    override fun create(context: Context): Unit? {
        startKoin {
            modules(appModule)
        }
        return null
    }
}