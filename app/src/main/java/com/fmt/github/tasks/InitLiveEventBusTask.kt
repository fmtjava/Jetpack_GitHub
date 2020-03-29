package com.fmt.github.tasks

import com.fmt.launch.starter.task.Task
import com.jeremyliao.liveeventbus.LiveEventBus

class InitLiveEventBusTask : Task() {

    override fun run() {
        LiveEventBus.get()
            .config()
            .supportBroadcast(mContext)
            .lifecycleObserverAlwaysActive(true)
            .autoClear(false)
    }
}