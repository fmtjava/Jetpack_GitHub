package com.fmt.github.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*

class ActivityManager private constructor() {

    //保存App中的Activity
    private val activityRefList = Stack<WeakReference<Activity>>()

    //判断当前Activity是否在前台
    private var front = true

    //当前App在前台Activity的个数
    private var activityFrontCount = 0

    //监听App前后台切换的回到集合
    private val frontBackCallbacks = arrayListOf<FrontBackCallback>()

    companion object {
        val instance: ActivityManager by lazy { ActivityManager() }
    }

    fun init(application: Application) {
        //监听App各个Activity的生命周期
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityRefList.push(WeakReference(activity))
            }

            override fun onActivityStarted(activity: Activity) {
                activityFrontCount++
                //判断App是否从后台切换到前台
                if (!front && activityFrontCount > 0) {
                    front = true
                    onFrontBackChanged(front)
                }
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                activityFrontCount--
                //判断App是否从前台切换到后台
                if (front && activityFrontCount <= 0) {
                    front = false
                    onFrontBackChanged(front)
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                for (activityRef in activityRefList) {
                    if (activityRef.get() == activity) {
                        activityRefList.remove(activityRef)
                        break
                    }
                }
            }
        })
    }

    private fun onFrontBackChanged(front: Boolean) {
        frontBackCallbacks.forEach {
            it.onChanged(front)
        }
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        if (!frontBackCallbacks.contains(callback)) {
            frontBackCallbacks.add(callback)
        }
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.remove(callback);
    }

    //定义App前台/后台切换回调接口
    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }
}