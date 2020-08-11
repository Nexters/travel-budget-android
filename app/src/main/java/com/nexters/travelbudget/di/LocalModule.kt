package com.nexters.travelbudget.di

import com.nexters.travelbudget.data.local.source.UserTokenLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Local 관련 DI 설정 하는 곳
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.25
 */

val localModule = module {
    single {
        UserTokenLocalDataSource(androidContext())
    }
}