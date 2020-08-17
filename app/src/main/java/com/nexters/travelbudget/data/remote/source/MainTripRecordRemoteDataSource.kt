package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import io.reactivex.Single

/**
 * 메인 탭 여행 기록(기록할 여행, 다녀온 여행) remote DataSource
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class MainTripRecordRemoteDataSource(private val tripieService: TripieService) {
    fun getTripRecordInfo(isComing: Boolean): Single<List<TripRecordResponse>> {
        return tripieService.getMainTripInfo(isComing)
    }
}