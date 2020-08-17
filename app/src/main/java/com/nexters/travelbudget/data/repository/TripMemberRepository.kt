package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import com.nexters.travelbudget.data.remote.source.TripMemberRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

/**
 * 여행 친구 목록 조회 Repository
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class TripMemberRepository(private val tripMemberRemoteDataSource: TripMemberRemoteDataSource) {

    fun getTripMembers(planId: Long): Single<TripMemberResponse> {
        return tripMemberRemoteDataSource.getTripMembers(planId)
    }

    fun deleteTripMember(planId: Long, memberId: Long): Completable {
        return tripMemberRemoteDataSource.deleteTripMember(planId, memberId)
    }
}