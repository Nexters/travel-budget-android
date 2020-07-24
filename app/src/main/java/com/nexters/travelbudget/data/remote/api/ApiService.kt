package com.nexters.travelbudget.data.remote.api

import com.nexters.travelbudget.data.remote.model.request.SignUpRequest
import com.nexters.travelbudget.data.remote.model.response.SignUpResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 서버 API 들을 관리하는 인터페이스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */

interface ApiService {

    @POST("api/kakao/signup")
    fun requestSignUp(@Body signUpRequest: SignUpRequest): Single<SignUpResponse>
}