package com.fmt.github.user.model

data class UserInfoModel(
    val login: String, val bio: String, val email: String,
    val blog: String, val followers: Int, val following: Int,
    val public_repos: Int, val public_gists: Int,val location:String
)