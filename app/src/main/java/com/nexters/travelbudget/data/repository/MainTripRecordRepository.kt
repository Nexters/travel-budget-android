package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.remote.source.MainTripRecordRemoteDataSource
import io.reactivex.Single

/**
 * 메인 탭 여행 기록(기록할 여행, 다녀온 여행) Repository
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class MainTripRecordRepository(private val mainTripRecordRemoteDataSource: MainTripRecordRemoteDataSource) {
    fun getTripRecordInfo(isComing: Boolean): Single<List<TripRecordResponse>> {
        return mainTripRecordRemoteDataSource.getTripRecordInfo(isComing)
    }
}