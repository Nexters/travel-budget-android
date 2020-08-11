package com.nexters.travelbudget.data.local.prefs

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.getSharedPreference
import com.nexters.travelbudget.utils.ext.putSharedPreference

/**
 * 사용자 토큰 관리 클래스
 * SharedPreference 를 통해 사용자 토큰 값을 저장 및 관리
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */
object UserTokenManager {

    /** 유저 토큰 정보 저장 */
    fun setUserToken(context: Context, userToken: UserTokenResponse) {
        val userTokenJson = Gson().toJson(userToken)
        context.putSharedPreference(Constant.PREF_USER_TOKEN, userTokenJson)
    }

    /** 유저 토큰 정보 조회 */
    fun getUserToken(context: Context): UserTokenResponse? {
        val prefData = context.getSharedPreference(Constant.PREF_USER_TOKEN, "")

        return if (prefData.isNotEmpty()) {
            Gson().fromJson(prefData, UserTokenResponse::class.java)
        } else {
            null
        }
    }

    /** 유저 토큰 정보 삭제 */
    fun deleteUserToken(context: Context) {
        context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE).edit(true) {
            remove(Constant.PREF_USER_TOKEN)
        }
    }

    /** 유저 Access Token 조회 */
    fun getUserAccessToken(context: Context): String? {
        val userToken = getUserToken(context)
        return userToken?.accessToken
    }

    /** 유저 Refresh Token 조회 */
    fun getUserRefreshToken(context: Context): String? {
        val userToken = getUserToken(context)
        return userToken?.refreshToken
    }

    /** 유저 Access Token 만료 시간 조회 (타임스탬프 형식) */
    fun getUserExpireDate(context: Context): String? {
        val userToken = getUserToken(context)
        return userToken?.expireDt
    }

    /** 유저 토큰 type 값 조회 */
    fun getUserTokenType(context: Context): String? {
        val userToken = getUserToken(context)
        return userToken?.tokenType
    }
}