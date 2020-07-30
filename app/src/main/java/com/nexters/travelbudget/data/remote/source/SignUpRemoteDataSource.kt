package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.AuthService
import com.nexters.travelbudget.data.remote.model.request.SignUpRequest
import com.nexters.travelbudget.data.remote.model.response.SignUpResponse
import io.reactivex.Single

/**
 * 회원가입(remote) 관련 데이터 소스 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

class SignUpRemoteDataSource(private val authService: AuthService) {

    fun requestSignUp(signUpRequest: SignUpRequest): Single<SignUpResponse> {
        return authService.requestSignUp(signUpRequest)
    }
}