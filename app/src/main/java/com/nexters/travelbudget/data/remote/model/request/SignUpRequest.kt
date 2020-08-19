package com.nexters.travelbudget.data.remote.model.request

import com.google.gson.annotations.SerializedName

/**
 * 회원가입 Request
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */
data class SignUpRequest(
    @SerializedName("kakao_id")
    val kakaoId: String,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String?,
    @SerializedName("profile_image")
    val profileImage: String?
)