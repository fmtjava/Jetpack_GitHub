package com.fmt.github.home.api

import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface DownloadApi {

    @Streaming
    @GET
    suspend fun download(@Url url: String): ResponseBody
}