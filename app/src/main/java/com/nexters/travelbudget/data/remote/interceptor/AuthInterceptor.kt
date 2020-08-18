package com.nexters.travelbudget.data.remote.interceptor

import android.content.Context
import com.google.gson.Gson
import com.nexters.travelbudget.data.local.prefs.UserTokenManager
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * 네트워크 통신 관련 인터셉터 클래스
 * 헤더에 토큰 및 데이터 담는 용도
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */
class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val userToken = UserTokenManager.getUserToken(context) ?: kotlin.run {
            return chain.proceed(originRequest.newBuilder().build())
        }

        val accessTokenExpiredDate = userToken.expireDt
        val accessToken = userToken.accessToken
        val refreshToken = userToken.refreshToken

        return if (accessTokenExpiredDate.toLong() > System.currentTimeMillis()) {
            chain.proceed(
                originRequest.newBuilder()
                    .addHeaders(accessToken)
                    .build()
            )
        } else {
            val body = JSONObject().put("refresh_token", refreshToken).toString()
                .toRequestBody("application/json".toMediaTypeOrNull())

            val requestBody = Request.Builder()
                .url("http://175.123.172.42:9050/api/auth/token/refresh")
                .post(body)
                .build()

            val serverResponse = OkHttpClient().newCall(requestBody).execute()

            if (serverResponse.isSuccessful) {
                serverResponse.body?.string()?.let {
                    val userTokenResponse = Gson().fromJson(it, UserTokenResponse::class.java)
                    UserTokenManager.setUserToken(context, userTokenResponse)
                    chain.proceed(
                        originRequest.newBuilder().addHeaders(userTokenResponse.accessToken).build()
                    )
                } ?: chain.proceed(originRequest)
            } else {
                chain.proceed(originRequest)
            }
        }
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header("Authorization", "Bearer $token") }

}