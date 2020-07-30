package com.nexters.travelbudget.data.remote.interceptor

import android.content.Context
import com.google.gson.Gson
import com.nexters.travelbudget.data.local.prefs.UserTokenManager
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * access token 만료 시 refresh token 값을 이용해 갱신하는 클래스 (interceptor)
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.30
 */
class TokenRefreshAuthenticator(
    private val context: Context
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request.newBuilder()
        val refreshToken = UserTokenManager.getUserRefreshToken(context)
        if (response.code == 401 && refreshToken.isNullOrEmpty().not()) {

            val body = JSONObject().put("refresh_token", refreshToken).toString()
                .toRequestBody("application/json".toMediaTypeOrNull())

            val requestBody = Request.Builder()
                .url("http://175.123.172.42:9050/api/auth/token/create")
                .post(body)
                .build()

            OkHttpClient().newCall(requestBody).execute().body?.string()?.let {
                val userTokenResponse = Gson().fromJson(it, UserTokenResponse::class.java)
                UserTokenManager.setUserToken(context, userTokenResponse)
                return request.addHeader("Authorization", "Bearer ${userTokenResponse.accessToken}")
                    .build()
            }

        }
        return null
    }
}