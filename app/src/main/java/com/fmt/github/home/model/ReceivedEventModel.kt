package com.fmt.github.home.model

data class ReceivedEventModel(
    val id: String,
    val type: String,
    val actor: Actor,
    val repo: Repo,
    val created_at: String
)

data class Actor(val login: String, val display_login: String, val avatar_url: String)

data class Repo(val name: String)