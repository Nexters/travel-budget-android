package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.remote.source.DetailPaymentRemoteDataSource
import io.reactivex.Single

class DetailPaymentRepository(private val detailPaymentRemoteDataSource: DetailPaymentRemoteDataSource) {

    fun getTripPaymentInfo(budgetId: Long, isReady: String, paymentDt: String): Single<List<TripPaymentResponse>> {
        return detailPaymentRemoteDataSource.getTripPaymentInfo(budgetId, isReady, paymentDt)
    }

}