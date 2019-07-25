package com.fmt.github.repos.repository

import com.fmt.github.data.http.RetrofitClient
import com.fmt.github.repos.model.ReposListModel
import okhttp3.ResponseBody
import retrofit2.Response

class ReposRepository {

    suspend fun searchRepos(query: String, page: Int): ReposListModel =
        RetrofitClient.mReposService.searchRepos(query, page)

    suspend fun checkRepoStarred(owner: String, repo: String): Response<ResponseBody> =
        RetrofitClient.mReposService.checkRepoStarred(owner, repo)

    suspend fun starRepo(owner: String, repo: String): Response<ResponseBody> =
        RetrofitClient.mReposService.starRepo(owner, repo)

    suspend fun unStarRepo(owner: String, repo: String): Response<ResponseBody> =
        RetrofitClient.mReposService.unStarRepo(owner, repo)


}