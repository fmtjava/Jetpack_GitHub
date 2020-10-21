package com.fmt.github.repos.delegate
import android.app.Activity
import android.view.KeyEvent
import android.view.ViewGroup

/**
 * WebView通用接口，方便后续切换
 */
interface WebContainer {

    fun onCreate(activity: Activity, viewGroup: ViewGroup, webUrl: String)

    fun onPause()

    fun onResume()

    fun onDestroy()

    fun back():Boolean

    fun handleKeyEvent(keyCode: Int, event: KeyEvent):Boolean
}