package com.fmt.github.repos.delegate

import android.app.Activity
import android.view.KeyEvent
import android.view.ViewGroup

class WebDelegate(
    private val webContainer: WebContainer,
    private val mActivity: Activity,
    private val mViewGroup: ViewGroup,
    private val mWebUrl: String
) {

    companion object {
        fun create(
            webContainer: WebContainer,
            activity: Activity,
            viewGroup: ViewGroup,
            webUrl: String
        ): WebDelegate =
            WebDelegate(webContainer, activity, viewGroup, webUrl)
    }

    fun onCreate() {
        webContainer.onCreate(mActivity, mViewGroup, mWebUrl)
    }

    fun onPause() {
        webContainer.onPause()
    }

    fun onResume() {
        webContainer.onResume()
    }

    fun onDestroy() {
        webContainer.onDestroy()
    }

    fun back() = webContainer.back()

    fun handleKeyEvent(keyCode: Int, event: KeyEvent) = webContainer.handleKeyEvent(keyCode, event)

}