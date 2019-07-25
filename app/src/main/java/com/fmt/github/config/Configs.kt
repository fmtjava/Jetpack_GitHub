package com.fmt.github.config

import android.os.Build
import java.util.*

/**
 * Oauth App统一配置
 */
object Configs {

    const val CLIENT_ID = "b050ee3cc3cc692d1d46"
    const val CLIENT_SECRET = "f52c740c30064a8bed60fc0c919d93251c0c1f15"
    const val NOTE = "open_github"
    const val NOTE_URL = "https://github.com/fmtjava"
    val SCOPE = listOf("user", "repo", "notifications", "gist")
    val FINGERPRINT: String by lazy { Build.FINGERPRINT + UUID.randomUUID().toString() }

    const val BUGLY_APP_ID = "8a37e3b7c7"
}