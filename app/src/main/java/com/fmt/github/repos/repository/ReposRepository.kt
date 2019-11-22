package com.fmt.github.repos.repository

import com.fmt.github.repos.api.ReposApi
import com.fmt.github.repos.model.ReposListModel
import okhttp3.ResponseBody
import retrofit2.Response

class ReposRepository(private val mReposApi: ReposApi) {

    suspend fun searchRepos(query: String, sort: String,order: String, page: Int): ReposListModel =
        mReposApi.searchRepos(query, sort,order, page)

    suspend fun checkRepoStarred(owner: String, repo: String): Response<ResponseBody> =//返回原始类型
        mReposApi.checkRepoStarred(owner, repo)

    suspend fun starRepo(owner: String, repo: String): Response<ResponseBody> =
        mReposApi.starRepo(owner, repo)

    suspend fun unStarRepo(owner: String, repo: String): Response<ResponseBody> =
        mReposApi.unStarRepo(owner, repo)


}
