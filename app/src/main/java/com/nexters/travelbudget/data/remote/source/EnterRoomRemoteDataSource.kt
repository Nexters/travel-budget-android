package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import io.reactivex.Completable

/**
 * 방 입장(초대코드 입력) 관련 데이터 소스 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class EnterRoomRemoteDataSource(private val tripieService: TripieService) {

    fun requestEnterRoom(invitationCode: String): Completable {
        return tripieService.requestEnterRoom(hashMapOf("invite_code" to invitationCode))
    }
}