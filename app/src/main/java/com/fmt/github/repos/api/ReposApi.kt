package com.fmt.github.repos.api

import com.fmt.github.config.Configs
import com.fmt.github.repos.model.ReposListModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ReposApi {

    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String, @Query(
            "page"
        ) page: Int, @Query("per_page") per_page: Int = Configs.PAGE_SIZE
    ): ReposListModel

    @GET("user/starred/{owner}/{repo}")
    suspend fun checkRepoStarred(@Path("owner") owner: String, @Path("repo") repo: String): Response<ResponseBody>

    @PUT("user/starred/{owner}/{repo}")
    suspend fun starRepo(@Path("owner") owner: String, @Path("repo") repo: String): Response<ResponseBody>

    @DELETE("user/starred/{owner}/{repo}")
    suspend fun unStarRepo(@Path("owner") owner: String, @Path("repo") repo: String): Response<ResponseBody>
}