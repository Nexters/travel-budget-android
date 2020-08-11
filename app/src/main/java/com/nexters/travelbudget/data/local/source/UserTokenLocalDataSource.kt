package com.nexters.travelbudget.data.local.source

import android.content.Context
import com.nexters.travelbudget.data.local.prefs.UserTokenManager
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse

/**
 * 유저 토큰 관리(local) 데이터 소스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */

class UserTokenLocalDataSource(private val context: Context) {

    fun saveUserTokenToSharedPrefs(userTokenResponse: UserTokenResponse) {
        UserTokenManager.setUserToken(context, userTokenResponse)
    }

    fun getUserTokenToSharedPrefs() = UserTokenManager.getUserToken(context)

    fun deleteUserToken() {
        UserTokenManager.deleteUserToken(context)
    }
}