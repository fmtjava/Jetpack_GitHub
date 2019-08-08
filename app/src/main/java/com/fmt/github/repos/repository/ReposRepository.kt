package com.fmt.github.repos.repository

import com.fmt.github.data.http.ReposService
import com.fmt.github.repos.model.ReposListModel
import okhttp3.ResponseBody
import retrofit2.Response

class ReposRepository {

    suspend fun searchRepos(query: String, page: Int): ReposListModel =
        ReposService.searchRepos(query, page)

    suspend fun checkRepoStarred(owner: String, repo: String): Response<ResponseBody> =//返回原始类型
        ReposService.checkRepoStarred(owner, repo)

    suspend fun starRepo(owner: String, repo: String): Response<ResponseBody> =
        ReposService.starRepo(owner, repo)

    suspend fun unStarRepo(owner: String, repo: String): Response<ResponseBody> =
        ReposService.unStarRepo(owner, repo)


}