package com.nexters.travelbudget.data.remote.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable