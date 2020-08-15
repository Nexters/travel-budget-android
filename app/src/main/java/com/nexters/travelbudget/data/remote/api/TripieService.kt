package com.nexters.travelbudget.data.remote.api

import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

//    @GET("api/payments")
//    fun abc(@Query("budget_id") budget_id: Long, @Query("is_ready") is_ready: String, @Query("payment_dt") payment_dt: String) : Single<>
}