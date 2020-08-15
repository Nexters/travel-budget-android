package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.source.EnterRoomRemoteDataSource
import io.reactivex.Completable

/**
 * 방 입장(초대코드 입력) 관련 레파지토리 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class EnterRoomRepository(private val enterRoomRemoteDataSource: EnterRoomRemoteDataSource) {

    fun requestEnterRoom(invitationCode: String): Completable {
        return enterRoomRemoteDataSource.requestEnterRoom(invitationCode)
    }
}