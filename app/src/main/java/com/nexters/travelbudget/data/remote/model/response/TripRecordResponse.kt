package com.nexters.travelbudget.data.remote.model.response

import com.google.gson.annotations.SerializedName


data class TripRecordResponse(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("day_count")
    val dayCount: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_public")
    val isPublic: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("plan_period")
    val planPeriod: String,
    @SerializedName("user_count")
    val userCount: Int
)