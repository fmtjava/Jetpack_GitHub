package com.fmt.github.repos.delegate

import android.app.Activity
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class WebDelegate(
    private val webContainer: WebContainer,
    private val mActivity: Activity,
    private val mViewGroup: ViewGroup,
    private val mWebUrl: String
) : DefaultLifecycleObserver {

    companion object {
        fun create(
            webContainer: WebContainer,
            activity: Activity,
            viewGroup: ViewGroup,
            webUrl: String
        ): WebDelegate =
            WebDelegate(webContainer, activity, viewGroup, webUrl)
    }

    override fun onCreate(owner: LifecycleOwner) {
        webContainer.onCreate(mActivity, mViewGroup, mWebUrl)
    }

    override fun onPause(owner: LifecycleOwner) {
        webContainer.onPause()
    }

    override fun onResume(owner: LifecycleOwner) {
        webContainer.onResume()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        webContainer.onDestroy()
    }

    fun back() = webContainer.back()

    fun handleKeyEvent(keyCode: Int, event: KeyEvent) = webContainer.handleKeyEvent(keyCode, event)

}