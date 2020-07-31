package com.nexters.travelbudget.di

import com.nexters.travelbudget.ui.login.LoginViewModel
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import com.nexters.travelbudget.ui.main.MainViewModel
import com.nexters.travelbudget.ui.splash.SplashViewModel
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
    viewModel { SplashViewModel() }
    viewModel { (kakaoLogin: KakaoLogin) ->
        LoginViewModel(kakaoLogin, get(), get())
    }
    viewModel { MainViewModel() }
}