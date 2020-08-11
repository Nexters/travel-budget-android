package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import io.reactivex.Single

/**
 * 유저 정보 가져오는(remote) 데이터 소스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class UserInfoRemoteDataSource(private val tripPieService: TripieService) {

    fun getUserInfo(): Single<UserResponse> {
        return tripPieService.getUserInfo()
    }
}