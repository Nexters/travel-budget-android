package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.AuthService
import com.nexters.travelbudget.data.remote.model.response.UserTokenResponse
import io.reactivex.Single

/**
 * 유저 토큰 발급(remote) 관련 데이터 소스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */

class UserTokenRemoteDataSource(private val authService: AuthService) {

    fun createUserToken(kakaoId: String): Single<UserTokenResponse> {
        return authService.createUserToken(hashMapOf("kakao_id" to kakaoId))
    }
}