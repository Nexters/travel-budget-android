package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripProfileResponse
import com.nexters.travelbudget.data.remote.source.TripProfileRemoteDataSource
import io.reactivex.Completable
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

    fun modifyTripProfile(
        planId: Long,
        name: String,
        sharedAmount: Long? = null,
        personalAmount: Long
    ): Completable {
        return tripProfileRemoteDataSource.modifyTripProfile(
            planId,
            name,
            sharedAmount,
            personalAmount
        )
    }
}