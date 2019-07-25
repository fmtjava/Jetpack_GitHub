package com.fmt.github.data.http.interceptor

import android.util.Base64
import com.fmt.github.constant.Constant
import com.fmt.github.data.storage.Preference
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    var mToken by Preference(Constant.USER_TOKEN, "")
    var mUserName by Preference(Constant.USER_NAME, "")
    var mPassword by Preference(Constant.USER_PASSWORD, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        return chain.proceed(request.newBuilder().apply {
            when {
                request.url.pathSegments.contains(Constant.AUTHORIZATIONS) -> {//授权接口
                    val userCredentials = "$mUserName:$mPassword"
                    val auth = "basic ${Base64.encodeToString(userCredentials.toByteArray(), Base64.NO_WRAP)}"
                    addHeader(Constant.AUTHORIZATION, auth)
                }
                !mToken.isNullOrEmpty() -> {
                    val auth = "Token $mToken"
                    addHeader(Constant.AUTHORIZATION, auth)
                }
            }

        }.build())

    }
}