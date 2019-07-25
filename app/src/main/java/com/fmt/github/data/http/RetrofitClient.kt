package com.fmt.github.data.http

import android.util.Log
import com.fmt.github.BuildConfig
import com.fmt.github.data.http.interceptor.AuthorizationInterceptor
import com.fmt.github.repos.service.ReposService
import com.fmt.github.user.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"
    private const val TIME_OUT = 30L
    private const val TAG = "fmt"

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    private val mOkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e(TAG, message)
            }
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.build()
    }

    val mReposService: ReposService by lazy {
        mRetrofit.create(ReposService::class.java)
    }

    val mUserService: UserService by lazy {
        mRetrofit.create(UserService::class.java)
    }

}