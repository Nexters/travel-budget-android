package com.nexters.travelbudget.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RecordPaymentRequest(
    @SerializedName("category")
    val category: String,
    @SerializedName("is_ready")
    val isReady: String,
    @SerializedName("payment_dt")
    val paymentDt: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("budget_id")
    val budgetId: Long
)