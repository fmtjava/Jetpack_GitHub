package com.fmt.github

import android.app.Application
import android.content.ContextWrapper
import com.fmt.github.tasks.InitBuGlyTask
import com.fmt.github.tasks.InitKoInTask
import com.fmt.github.tasks.InitLiveEventBusTask
import com.fmt.github.tasks.InitSmartRefreshLayoutTask
import com.fmt.launch.starter.TaskDispatcher

lateinit var mApplication: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        //启动器进行异步初始化
        TaskDispatcher.init(this)
        TaskDispatcher.createInstance()
            .addTask(InitBuGlyTask())
            .addTask(InitKoInTask())
            .addTask(InitLiveEventBusTask())
            .addTask(InitSmartRefreshLayoutTask())
            .start()
    }
}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)
