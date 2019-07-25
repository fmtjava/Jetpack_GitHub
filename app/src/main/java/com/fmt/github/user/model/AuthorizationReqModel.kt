package com.fmt.github.user.model

data class AuthorizationReqModel(
    val client_secret: String, val scopes: List<String>,
    val note: String, val note_url: String
)