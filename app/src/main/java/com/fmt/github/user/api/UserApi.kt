package com.fmt.github.user.api

import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @PUT("authorizations/clients/{client_id}/{fingerprint}")
    suspend fun createOrGetAuthorization(
        @Body authorizationReqModel: AuthorizationReqModel, @Path("client_id") client_id: String, @Path("fingerprint") fingerprint: String
    ): AuthorizationRespModel

    @DELETE("authorizations/{id}")
    suspend fun deleteAuthorization(@Path("id") id: Int): Response<ResponseBody>

    @GET("user")
    suspend fun getUser(): UserModel

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String, @Query("page") page: Int, @Query("per_page") per_page: Int = 10): UserListModel

    @GET("users/{user}")
    suspend fun getUserInfo(@Path("user") user: String): UserInfoModel

    @GET("users/{user}/repos")
    suspend fun getUserPublicRepos(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int = 10): List<ReposItemModel>

    @GET("users/{user}/starred")
    suspend fun getStarredRepos(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int = 10): List<ReposItemModel>

}