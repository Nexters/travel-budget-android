package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.TripProfileResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * 여행 프로필 관련 데이터 소스 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.19
 */
class TripProfileRemoteDataSource(private val tripieService: TripieService) {

    fun getTripProfileInfo(planId: Long): Single<TripProfileResponse> {
        return tripieService.getTripProfileInfo(planId)
    }

    fun modifyTripProfile(
        planId: Long,
        name: String,
        sharedAmount: Long?,
        personalAmount: Long
    ): Completable {
        val param = hashMapOf<String, Any>().apply {
            put("name", name)
            put("personal_amount", personalAmount)
            if (sharedAmount != null) {
                put("shared_amount", sharedAmount)
            }
        }
        return tripieService.modifyTripProfile(planId, param)
    }

    fun exitTripRoom(planId: Long, memberId: Long): Completable {
        return tripieService.deleteTripMember(planId, memberId)
    }

    fun deleteTripRoom(planId: Long): Completable {
        return tripieService.deleteTripRoom(planId)
    }
}