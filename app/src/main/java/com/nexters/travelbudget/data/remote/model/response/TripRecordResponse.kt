package com.nexters.travelbudget.data.remote.model.response

import com.google.gson.annotations.SerializedName


data class TripRecordResponse(
    @SerializedName("day_count")
    val dayCount: String, // D-DAY
    @SerializedName("budget_id")
    val budgetId: Long,
    @SerializedName("plan_id")
    val planId: Long,
    @SerializedName("invite_code")
    val inviteCode: String, // 방 초대 해시 코드
    @SerializedName("is_doing")
    val isDoing: String, // 여행중인 여부
    @SerializedName("is_public")
    val isPublic: String, // 여행 공용 여부 Y, N
    @SerializedName("name")
    val name: String, // 여행 명
    @SerializedName("purpose_amount")
    val purposeAmount: Int, // 목표 예상 (개인 여행 및 목표금액 미설정시 -1)
    @SerializedName("start_date")
    val startDate: String, // 시작 일자
    @SerializedName("end_date")
    val endDate: String, // 끝 일자
    @SerializedName("used_amount")
    val usedAmount: Int, // 사용된 예산
    @SerializedName("user_count")
    val userCount: Int // 여행 참가 인원 수
)