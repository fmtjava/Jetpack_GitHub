package com.fmt.github.tasks

import com.fmt.github.config.Configs
import com.fmt.launch.starter.task.Task
import com.tencent.bugly.crashreport.CrashReport

class InitBuGlyTask : Task() {

    override fun run() {
        CrashReport.initCrashReport(mContext, Configs.BUGLY_APP_ID, false)
    }
}