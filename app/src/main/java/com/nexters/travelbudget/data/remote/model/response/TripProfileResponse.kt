package com.nexters.travelbudget.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class TripProfileResponse(
    @SerializedName("authority")
    val authority: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("personal")
    val personal: Personal?,
    @SerializedName("shared")
    val shared: Shared?,
    @SerializedName("start_date")
    val startDate: String
) {
    data class Personal(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("budget_id")
        val budgetId: Int
    )

    data class Shared(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("budget_id")
        val budgetId: Int
    )
}