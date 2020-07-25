package com.nexters.travelbudget.data.remote.model.response


import com.google.gson.annotations.SerializedName

/**
 * 유저 토큰 생성 성공 응답값
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */

data class UserTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expire_dt")
    val expireDt: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String
)