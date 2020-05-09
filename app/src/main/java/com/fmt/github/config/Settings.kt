package com.fmt.github.config

import com.fmt.github.constant.Constant
import com.fmt.github.data.storage.Preference

object Settings {

    object Account {
        var token by Preference(Constant.USER_TOKEN, "")
        var loginUser by Preference(Constant.LOGIN_USER, "")
        var userName by Preference(Constant.USER_NAME, "")
        var password by Preference(Constant.USER_PASSWORD, "")
    }
}