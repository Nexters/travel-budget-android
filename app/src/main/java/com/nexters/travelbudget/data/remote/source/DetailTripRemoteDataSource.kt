package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import io.reactivex.Single

class DetailTripRemoteDataSource(private val tripieService: TripieService) {
    fun getTripDetailInfo(id: Long): Single<TripDetailResponse> {
        return tripieService.getTripDetailInfo(id)
    }
}