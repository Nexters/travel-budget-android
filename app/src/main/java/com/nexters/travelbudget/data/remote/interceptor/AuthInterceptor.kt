package com.nexters.travelbudget.data.remote.interceptor

import android.content.Context
import com.nexters.travelbudget.data.local.prefs.UserTokenManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 네트워크 통신 관련 인터셉터 클래스
 * 헤더에 토큰 및 데이터 담는 용도
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */
class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader("Content-Type", "application/json")

        UserTokenManager.getUserAccessToken(context)?.let { accessToken ->
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}