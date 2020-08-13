package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.request.CreateRoomRequest
import com.nexters.travelbudget.data.remote.model.response.CreateRoomResponse
import io.reactivex.Single

/**
 * 방 생성(remote) 관련 데이터 소스 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */
class CreateRoomRemoteDataSource(private val tripieService: TripieService) {

    fun requestCreateRoom(request: CreateRoomRequest): Single<CreateRoomResponse> {
        return tripieService.requestCreateRoom(request)
    }
}