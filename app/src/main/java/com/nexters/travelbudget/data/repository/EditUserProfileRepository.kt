package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.source.EditUserProfileRemoteDataSource
import io.reactivex.Completable

/**
 * 유저 정보 수정 Repository
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class EditUserProfileRepository(private val editUserProfileRemoteDataSource: EditUserProfileRemoteDataSource) {

    fun requestEditUserProfile(nickname: String): Completable {
        return editUserProfileRemoteDataSource.requestEditUserProfile(nickname)
    }
}