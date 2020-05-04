package com.fmt.github.data.http.interceptor

import android.util.Base64
import com.fmt.github.config.Settings
import com.fmt.github.constant.Constant
import com.fmt.github.ext.isLogin
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        return request.url.pathSegments.contains(Constant.DOWNLOAD).yes {//下载Apk，不需要添加请求头
            chain.proceed(request)
        }.otherwise {
            chain.proceed(request.newBuilder().apply {
                when {
                    request.url.pathSegments.contains(Constant.AUTHORIZATIONS) -> {//登陆授权/退出接口
                        val userCredentials =
                            "${Settings.Account.userName}:${Settings.Account.password}"
                        val auth = "basic ${Base64.encodeToString(
                            userCredentials.toByteArray(),
                            Base64.NO_WRAP
                        )}"
                        addHeader(Constant.AUTHORIZATION, auth)
                    }
                    isLogin() -> {//权限接口
                        val auth = "Token ${Settings.Account.token}"
                        addHeader(Constant.AUTHORIZATION, auth)
                    }
                }

            }.build())
        }
    }
}