package com.fmt.github.repos.model

data class ReposItemModel(
    val name: String, val description: String, val owner: ReposOwnerModel,
    val language: String, val stargazers_count: Long, val forks: Long,
    val html_url: String
)