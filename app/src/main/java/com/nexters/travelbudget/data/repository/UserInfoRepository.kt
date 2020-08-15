package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.data.remote.source.UserInfoRemoteDataSource
import io.reactivex.Single

/**
 * 유저 정보 가져오기 (Repository)
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class UserInfoRepository(private val userInfoRemoteDataSource: UserInfoRemoteDataSource) {

    fun getUserInfo(): Single<UserResponse> {
        return userInfoRemoteDataSource.getUserInfo()
    }
}