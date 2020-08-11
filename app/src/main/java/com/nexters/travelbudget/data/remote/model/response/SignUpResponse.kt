package com.nexters.travelbudget.data.remote.model.response

import com.google.gson.annotations.SerializedName

/**
 * 회원가입 성공 응답값
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */
data class SignUpResponse(
    @SerializedName("kakao_id")
    val kakaoId: String
)