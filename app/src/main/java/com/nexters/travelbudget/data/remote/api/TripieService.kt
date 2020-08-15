package com.nexters.travelbudget.data.remote.api

import com.nexters.travelbudget.data.remote.model.request.CreateRoomRequest
import com.nexters.travelbudget.data.remote.model.response.CreateRoomResponse
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * 헤더에 토큰을 담아서 보내야되는 Api 항목들
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.30
 */

interface TripieService {

    @GET("api/plans")
    fun getMainTripInfo(@Query("isComing") isComing: Boolean): Single<List<TripRecordResponse>>

    @GET("api/users/me")
    fun getUserInfo(): Single<UserResponse>

    @POST("api/plans")
    fun requestCreateRoom(@Body param: CreateRoomRequest): Single<CreateRoomResponse>

    @POST("api/members")
    fun requestEnterRoom(@Body request: HashMap<String, String>): Completable

    @PUT("api/users/me")
    fun requestEditUserProfile(@Body request: HashMap<String, String>): Completable
}