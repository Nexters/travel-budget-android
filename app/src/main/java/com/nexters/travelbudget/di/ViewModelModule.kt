package com.nexters.travelbudget.di

import com.nexters.travelbudget.ui.login.LoginViewModel
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import com.nexters.travelbudget.ui.main.MainViewModel
import com.nexters.travelbudget.ui.splash.SplashViewModel
import com.nexters.travelbudget.ui.detail.TripDetailPersonalViewModel
import com.nexters.travelbudget.ui.detail.TripDetailSharedViewModel
import com.nexters.travelbudget.ui.detail.TripDetailViewModel
import com.nexters.travelbudget.ui.edit_trip_profile.EditTripProfileViewModel
import com.nexters.travelbudget.ui.select_date.SelectDateViewModel
import com.nexters.travelbudget.ui.statistics.StatisticsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel DI 설정을 위한 파일
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */

/** 뷰모델 모듈(DI) 설정 */
val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { (kakaoLogin: KakaoLogin) ->
        LoginViewModel(kakaoLogin, get(), get())
    }
    viewModel { MainViewModel() }
    viewModel { StatisticsViewModel() }
    viewModel { TripDetailViewModel() }
    viewModel { TripDetailSharedViewModel() }
    viewModel { TripDetailPersonalViewModel() }
    viewModel { EditTripProfileViewModel() }
    viewModel { SelectDateViewModel() }
}