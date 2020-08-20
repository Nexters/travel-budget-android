package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.remote.source.DetailTripRemoteDataSource
import io.reactivex.Single

class DetailTripRepository(private val detailTripRemoteDataSource: DetailTripRemoteDataSource) {
    fun getTripDetailInfo(id: Long): Single<TripDetailResponse> {
        return detailTripRemoteDataSource.getTripDetailInfo(id)
    }

    fun getTripPaymentInfo(budgetId: Long, isReady: String, paymentDt: String): Single<List<TripPaymentResponse>> {
        return detailTripRemoteDataSource.getTripPaymentInfo(budgetId, isReady, paymentDt)
    }

}