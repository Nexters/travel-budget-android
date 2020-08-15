package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import io.reactivex.Completable

/**
 * 유저 정보 수정 데이터 소스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class EditUserProfileRemoteDataSource(private val tripieService: TripieService) {

    fun requestEditUserProfile(nickname: String): Completable {
        return tripieService.requestEditUserProfile(hashMapOf("nickname" to nickname))
    }
}