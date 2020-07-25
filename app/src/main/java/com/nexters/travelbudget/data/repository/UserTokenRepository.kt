package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.local.source.UserTokenLocalDataSource
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse
import com.nexters.travelbudget.data.remote.source.UserTokenRemoteDataSource
import io.reactivex.Single

/**
 * 유저 토큰 발급 Repository 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */

class UserTokenRepository(
    private val userTokenRemoteDataSource: UserTokenRemoteDataSource,
    private val userTokenLocalDataSource: UserTokenLocalDataSource
) {

    fun createUserToken(kakaoId: String): Single<UserTokenResponse> {
        return userTokenRemoteDataSource.createUserToken(kakaoId).doOnSuccess {
            userTokenLocalDataSource.saveUserTokenToSharedPrefs(it)
        }
    }
}