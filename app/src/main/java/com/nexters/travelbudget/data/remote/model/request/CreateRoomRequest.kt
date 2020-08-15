package com.nexters.travelbudget.data.remote.model.request

import com.google.gson.annotations.SerializedName

/**
 * 여행 등록 모델 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
data class CreateRoomRequest(
    @SerializedName("name")
    val name: String, // 여행 이름
    @SerializedName("shared_budget")
    val shared_budget: Int, // 공용 금액
    @SerializedName("is_public")
    val is_public: String, // Y : 공용 여행 N : 개인 여행
    @SerializedName("start_date")
    val start_date: String, // 여행 시작 날짜
    @SerializedName("end_date")
    val end_date: String // 여행 종료 날짜
)