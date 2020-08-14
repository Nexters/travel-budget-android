package com.nexters.travelbudget.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("kakao_id")
    val kakaoId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String,
    @SerializedName("user_id")
    val userId: Int
)