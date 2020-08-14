package com.nexters.travelbudget.data.remote.model.response

import com.google.gson.annotations.SerializedName

/**
 * 방 생성 결과 모델 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */
data class CreateRoomResponse(
    @SerializedName("plan_id")
    val planId: Int
)