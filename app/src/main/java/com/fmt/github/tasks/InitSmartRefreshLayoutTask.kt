package com.fmt.github.tasks

import android.content.Context
import androidx.core.content.ContextCompat
import com.fmt.github.R
import com.rousetime.android_startup.AndroidStartup
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class InitSmartRefreshLayoutTask : AndroidStartup<Unit>() {

    override fun callCreateOnMainThread(): Boolean = false

    override fun waitOnMainThread(): Boolean = false

    override fun create(context: Context): Unit =
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { c, layout ->
            layout.setEnableHeaderTranslationContent(false)
            MaterialHeader(c).setColorSchemeColors(
                ContextCompat.getColor(
                    c,
                    R.color.colorPrimary
                )
            )
        }
}