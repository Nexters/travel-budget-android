package com.nexters.travelbudget.data.remote.api

import com.nexters.travelbudget.data.remote.model.request.CreateRoomRequest
import com.nexters.travelbudget.data.remote.model.request.RecordPaymentRequest
import com.nexters.travelbudget.data.remote.model.response.*

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.POST

import retrofit2.http.Query
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

    @GET("api/plans/{id}")
    fun getTripDetailInfo(@Path("id") id: Long): Single<TripDetailResponse>

    @POST("api/payments")
    fun recordPayments(@Body request: RecordPaymentRequest): Completable

    @PUT("api/payments/{paymentId}")
    fun modifyPayments(@Path("paymentId") paymentId: Long, @Body request: RecordPaymentRequest): Completable

    @DELETE("api/payments/{paymentId}")
    fun removePayments(@Path("paymentId") paymentId: Long): Completable

    @GET("api/budgets/{id}/statics")
    fun getStatisticsInfo(@Path("id") id: Long): Single<StatisticsResponse>

    @GET("api/users/me")
    fun getUserInfo(): Single<UserResponse>

    @POST("api/plans")
    fun requestCreateRoom(@Body param: CreateRoomRequest): Single<CreateRoomResponse>

    @POST("api/members")
    fun requestEnterRoom(@Body request: HashMap<String, String>): Completable

    @PUT("api/users/me")
    fun requestEditUserProfile(@Body request: HashMap<String, String>): Completable

    @GET("api/plans/{id}/members")
    fun getTripMembers(@Path("id") planId: Long): Single<TripMemberResponse>

    @DELETE("api/plans/{planId}/members/{memberId}")
    fun deleteTripMember(
        @Path("planId") planId: Long,
        @Path("memberId") memberId: Long
    ): Completable
}