package com.fmt.github.tasks

import android.content.Context
import com.didichuxing.doraemonkit.DoraemonKit
import com.fmt.github.mApplication
import com.rousetime.android_startup.AndroidStartup

class InitDoKitTask : AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = true

    override fun waitOnMainThread(): Boolean = true

    override fun create(context: Context): Unit = DoraemonKit.install(mApplication, "")
}