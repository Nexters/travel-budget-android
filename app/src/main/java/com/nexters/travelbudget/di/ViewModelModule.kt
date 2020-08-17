package com.nexters.travelbudget.di

import com.nexters.travelbudget.ui.detail.TripDetailAloneViewModel
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.ui.create_room.CreateRoomViewModel
import com.nexters.travelbudget.ui.login.LoginViewModel
import com.nexters.travelbudget.ui.login.kakao.KakaoLogin
import com.nexters.travelbudget.ui.main.MainViewModel
import com.nexters.travelbudget.ui.splash.SplashViewModel
import com.nexters.travelbudget.ui.detail.TripDetailPersonalViewModel
import com.nexters.travelbudget.ui.detail.TripDetailSharedViewModel
import com.nexters.travelbudget.ui.detail.TripDetailViewModel
import com.nexters.travelbudget.ui.edit_trip_profile.EditTripProfileViewModel
import com.nexters.travelbudget.ui.enter_room.EnterRoomViewModel
import com.nexters.travelbudget.ui.main.record.RecordedTravelViewModel
import com.nexters.travelbudget.ui.main.record.RecordingTravelViewModel
import com.nexters.travelbudget.ui.manage_member.ManageMemberViewModel
import com.nexters.travelbudget.ui.manage_member.OutMemberNoticeViewModel
import com.nexters.travelbudget.ui.mypage.EditUserProfileViewModel
import com.nexters.travelbudget.ui.mypage.MyPageViewModel
import com.nexters.travelbudget.ui.record_spend.RecordSpendViewModel
import com.nexters.travelbudget.ui.select_date.SelectDateViewModel
import com.nexters.travelbudget.ui.select_room_type.SelectRoomTypeViewModel
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
    viewModel { MainViewModel(get()) }
    viewModel { RecordingTravelViewModel(get()) }
    viewModel { RecordedTravelViewModel(get()) }
    viewModel { (userName: String) ->
        SelectRoomTypeViewModel(userName)
    }
    viewModel { (userName: String, roomType: String) ->
        CreateRoomViewModel(userName, roomType, get())
    }
    viewModel { StatisticsViewModel(get()) }
    viewModel { MyPageViewModel(get()) }
    viewModel { (userResponse: UserResponse) ->
        EditUserProfileViewModel(userResponse, get())
    }
    viewModel { (roomCode: String) ->
        EnterRoomViewModel(roomCode, get())
    }
    viewModel { TripDetailViewModel(get()) }
    viewModel { TripDetailSharedViewModel(get()) }
    viewModel { TripDetailPersonalViewModel(get()) }
    viewModel { TripDetailAloneViewModel() }
    viewModel { EditTripProfileViewModel() }
    viewModel { SelectDateViewModel() }
    viewModel { RecordSpendViewModel() }
    viewModel { (planId: Long, roomTitle: String) ->
        ManageMemberViewModel(planId, roomTitle, get())
    }
    viewModel { (username: String, memberId: Long) ->
        OutMemberNoticeViewModel(username, memberId)
    }
}