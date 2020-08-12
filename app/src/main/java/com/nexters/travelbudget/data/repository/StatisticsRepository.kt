package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.StatisticsResponse
import com.nexters.travelbudget.data.remote.source.StatisticsRemoteDataSource
import io.reactivex.Single

class StatisticsRepository(private val statisticsRemoteDataSource: StatisticsRemoteDataSource) {
    fun getStatisticsInfo(id: Long): Single<StatisticsResponse> {
        return statisticsRemoteDataSource.getStatisticsInfo(id)
    }
}