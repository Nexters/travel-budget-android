package com.nexters.travelbudget.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TripPaymentResponse (
    @SerializedName("budget_id")
    val budgetId: Long,
    @SerializedName("category")
    val category: String,
    @SerializedName("is_ready")
    val isReady: String,
    @SerializedName("payment_dt")
    val paymentDt: Long,
    @SerializedName("payment_id")
    val paymentId: Long,
    @SerializedName("price")
    val price: Long,
    @SerializedName("title")
    val title: String
)