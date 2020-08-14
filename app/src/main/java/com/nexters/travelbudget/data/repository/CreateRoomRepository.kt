package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.request.CreateRoomRequest
import com.nexters.travelbudget.data.remote.model.response.CreateRoomResponse
import com.nexters.travelbudget.data.remote.source.CreateRoomRemoteDataSource
import io.reactivex.Single

/**
 * 여행 방 생성 Repository
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */
class CreateRoomRepository(private val createRoomRemoteDataSource: CreateRoomRemoteDataSource) {

    fun requestCreateRoom(request: CreateRoomRequest): Single<CreateRoomResponse> {
        return createRoomRemoteDataSource.requestCreateRoom(request)
    }
}