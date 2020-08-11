package com.nexters.travelbudget.di

import com.nexters.travelbudget.data.remote.source.MainTripRecordRemoteDataSource
import com.nexters.travelbudget.data.remote.source.SignUpRemoteDataSource
import com.nexters.travelbudget.data.remote.source.UserInfoRemoteDataSource
import com.nexters.travelbudget.data.remote.source.UserTokenRemoteDataSource
import org.koin.dsl.module

/**
 * Remote 관련 DI 설정 하는 곳
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

val remoteModule = module {
    single { SignUpRemoteDataSource(get()) }
    single { UserTokenRemoteDataSource(get()) }
    single { MainTripRecordRemoteDataSource(get()) }
    single { UserInfoRemoteDataSource(get()) }
}