package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import io.reactivex.Single

class DetailTripRemoteDataSource(private val tripieService: TripieService) {
    fun getTripDetailInfo(id: Long): Single<TripDetailResponse> {
        return tripieService.getTripDetailInfo(id)
    }

    fun getTripPaymentInfo(budgetId: Long, isReady: String, paymentDt: String): Single<List<TripPaymentResponse>> {
        return tripieService.getTripPaymentInfo(budgetId, isReady, paymentDt)
    }
}