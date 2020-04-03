package com.fmt.github.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeConverter {

    fun tramsTimeAgo(time: String): String =
            transTimeStamp(time).let {
                DateUtils.getRelativeTimeSpanString(it).toString()
            }

    private fun transTimeStamp(time: String): Long =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                    .let {
                        it.timeZone = TimeZone.getTimeZone("GMT+1")
                        it.parse(time).time
                    }
}