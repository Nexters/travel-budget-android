package com.nexters.travelbudget.data.remote.model.response


import com.google.gson.annotations.SerializedName

/**
 * 여행 친구 목록 모델 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
data class TripMemberResponse(
    @SerializedName("invite_code")
    val inviteCode: String,
    @SerializedName("my_authority")
    val myAuthority: String,
    @SerializedName("members")
    val members: List<Member>
) {
    data class Member(
        @SerializedName("authority")
        val authority: String,
        @SerializedName("member_id")
        val memberId: Long,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("profile_image")
        val profileImage: String
    )
}