package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import io.reactivex.Single

/**
 * 여행 친구 목록 조회 데이터 소스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class TripMemberRemoteDataSource(private val tripieService: TripieService) {

    fun getTripMembers(planId: Long): Single<TripMemberResponse> {
        return tripieService.getTripMembers(planId)
    }
}