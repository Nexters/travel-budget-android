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
        val traffic: Int,
        val sleep: Int,
        val food: Int,
        val snack: Int,
        val shopping: Int,
        val culture: Int,
        val etc: Int
    )
}