package com.fmt.github.user.model

import java.io.Serializable

data class UserModel(val login: String, val avatar_url: String, val id: Int = 0) : Serializable