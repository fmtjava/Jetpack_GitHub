package com.fmt.github.repos.delegate

import android.app.Activity
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.fmt.github.R
import com.just.agentweb.AgentWeb

class WebDelegate(
    private val mActivity: Activity,
    private val mViewGroup: ViewGroup,
    private val mWebUrl: String
) {

    private lateinit var mAgentWeb: AgentWeb

    companion object {
        fun create(activity: Activity, viewGroup: ViewGroup, webUrl: String): WebDelegate =
            WebDelegate(activity, viewGroup, webUrl)
    }

    fun onCreate() {
        mAgentWeb = AgentWeb.with(mActivity)
            .setAgentWebParent(mViewGroup, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(ContextCompat.getColor(mActivity, R.color.indicator_color))
            .createAgentWeb()
            .ready()
            .go(mWebUrl)
    }

    fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
    }

    fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
    }

    fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
    }

    fun back() = mAgentWeb.back()

    fun handleKeyEvent(keyCode: Int, event: KeyEvent) = mAgentWeb.handleKeyEvent(keyCode, event)

}