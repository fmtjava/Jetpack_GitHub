package com.fmt.github.tasks

import com.fmt.github.config.Configs
import com.fmt.launch.starter.task.MainTask
import com.fmt.launch.starter.task.Task
import com.tencent.bugly.crashreport.CrashReport

class InitBuGlyTask : MainTask() {//Bugly官方建议，初始化放在主线程

    override fun run() {
        CrashReport.initCrashReport(mContext, Configs.BUGLY_APP_ID, false)
    }
}