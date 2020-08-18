package com.nexters.travelbudget.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TripDetailResponse (
    @SerializedName("member_id")
    val memberId: Long,
    @SerializedName("shared")
    val shared: Data,
    @SerializedName("personal")
    val personal: Data,
    @SerializedName("name")
    val name: String,
    @SerializedName("dates")
    val dates: List<String>
) {
    @Parcelize
    data class Data(
        @SerializedName("purpose_amount")
        val purposeAmount: Int,
        @SerializedName("suggest_amount")
        val suggestAmount: Int,
        @SerializedName("payment_amount")
        val paymentAmount: Int,
        @SerializedName("remain_amount")
        val remainAmount: Int,
        @SerializedName("budget_id")
        val budgetId: Long
    ) : Parcelable
}