package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.request.SignUpRequest
import com.nexters.travelbudget.data.remote.model.response.SignUpResponse
import com.nexters.travelbudget.data.remote.source.SignUpRemoteDataSource
import io.reactivex.Single

/**
 * 회원가입 Repository 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

class SignUpRepository(private val signUpRemoteDataSource: SignUpRemoteDataSource) {

    fun requestSignUp(signUpRequest: SignUpRequest): Single<SignUpResponse> {
        return signUpRemoteDataSource.requestSignUp(signUpRequest)
    }
}