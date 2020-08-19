package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripProfileResponse
import com.nexters.travelbudget.data.remote.source.TripProfileRemoteDataSource
import io.reactivex.Single

/**
 * 여행 프로필 관련 레파지토리 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.19
 */
class TripProfileRepository(private val tripProfileRemoteDataSource: TripProfileRemoteDataSource) {

    fun getSharedTripProfileInfo(planId: Long): Single<TripProfileResponse> {
        return tripProfileRemoteDataSource.getTripProfileInfo(planId)
    }
}