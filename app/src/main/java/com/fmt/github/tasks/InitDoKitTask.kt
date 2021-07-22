package com.fmt.github.tasks

import com.didichuxing.doraemonkit.DoraemonKit
import com.fmt.github.mApplication
import com.fmt.launch.starter.task.MainTask

class InitDoKitTask : MainTask() {

    override fun run() {
        //因为滴滴平台暂时注册不了，productId后续会替换,暂时使用不了平台工具
        DoraemonKit.install(mApplication,"")
    }
}