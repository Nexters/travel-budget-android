package com.nexters.travelbudget.di

import com.nexters.travelbudget.data.remote.source.DetailTripRemoteDataSource
import com.nexters.travelbudget.data.remote.source.MainTripRecordRemoteDataSource
import com.nexters.travelbudget.data.remote.source.SignUpRemoteDataSource
import com.nexters.travelbudget.data.remote.source.StatisticsRemoteDataSource
import com.nexters.travelbudget.data.remote.source.UserTokenRemoteDataSource
import com.nexters.travelbudget.data.remote.source.*
import com.nexters.travelbudget.data.repository.TripMemberRepository
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
    single { DetailTripRemoteDataSource(get()) }
    single { UserInfoRemoteDataSource(get()) }
    single { CreateRoomRemoteDataSource(get()) }
    single { StatisticsRemoteDataSource(get()) }
    single { EnterRoomRemoteDataSource(get()) }
    single { EditUserProfileRemoteDataSource(get()) }
    single { TripMemberRemoteDataSource(get()) }
}