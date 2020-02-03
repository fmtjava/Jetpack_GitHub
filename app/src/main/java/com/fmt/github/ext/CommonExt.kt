package com.fmt.github.ext

import com.fmt.github.config.Settings

fun isLogin(): Boolean = !Settings.Account.token.isBlank()

