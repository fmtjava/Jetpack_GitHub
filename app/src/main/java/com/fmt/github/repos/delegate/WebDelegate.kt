package com.fmt.github.repos.delegate

import android.app.Activity
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class WebDelegate(
    private val webContainer: WebContainer,
    private val mActivity: Activity,
    private val mViewGroup: ViewGroup,
    private val mWebUrl: String
) : LifecycleObserver {

    companion object {
        fun create(
            webContainer: WebContainer,
            activity: Activity,
            viewGroup: ViewGroup,
            webUrl: String
        ): WebDelegate =
            WebDelegate(webContainer, activity, viewGroup, webUrl)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        webContainer.onCreate(mActivity, mViewGroup, mWebUrl)
        Log.e("fmt", "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        webContainer.onPause()
        Log.e("fmt", "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        webContainer.onResume()
        Log.e("fmt", "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        webContainer.onDestroy()
        Log.e("fmt", "onDestroy")
    }

    fun back() = webContainer.back()

    fun handleKeyEvent(keyCode: Int, event: KeyEvent) = webContainer.handleKeyEvent(keyCode, event)

}