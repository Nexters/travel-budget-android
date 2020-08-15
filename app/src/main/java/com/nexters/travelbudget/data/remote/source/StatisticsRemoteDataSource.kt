package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.StatisticsResponse
import io.reactivex.Single

class StatisticsRemoteDataSource(private val service: TripieService) {
    fun getStatisticsInfo(id: Long): Single<StatisticsResponse> {
        return service.getStatisticsInfo(id)
    }
}