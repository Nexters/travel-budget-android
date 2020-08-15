package com.nexters.travelbudget.di

import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.data.repository.MainTripRecordRepository
import com.nexters.travelbudget.data.repository.SignUpRepository
import com.nexters.travelbudget.data.repository.UserTokenRepository
import org.koin.dsl.module

/**
 * Repository 관련 DI 설정 하는 곳
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.24
 */

val repositoryModule = module {
    single { SignUpRepository(get()) }
    single { UserTokenRepository(get(), get()) }
    single { MainTripRecordRepository(get()) }
    single { DetailTripRepository(get()) }
}