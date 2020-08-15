package com.nexters.travelbudget.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class StatisticsResponse(
    @SerializedName("purpose_amount")
    val purposeAmount: Int,
    @SerializedName("used_amount")
    val usedAmount: Int,
    @SerializedName("categories")
    val categories: Category
) {
    data class Category(
        @SerializedName("TRAFFIC")
        val traffic: Int,
        @SerializedName("SLEEP")
        val sleep: Int,
        @SerializedName("FOOD")
        val food: Int,
        @SerializedName("SNACK")
        val snack: Int,
        @SerializedName("SHOPPING")
        val shopping: Int,
        @SerializedName("CULTURE")
        val culture: Int,
        @SerializedName("ETC")
        val etc: Int
    )
}