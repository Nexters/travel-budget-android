package com.nexters.travelbudget.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.data.repository.UserInfoRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo

/**
 * 마이페이지 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class MyPageViewModel(private val userInfoRepository: UserInfoRepository) : BaseViewModel() {

    private val _userInfo: MutableLiveData<UserResponse> = MutableLiveData()
    val userInfo: LiveData<UserResponse> = _userInfo

    private val _startEditUserProfile: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startEditUserProfile: SingleLiveEvent<Unit> = _startEditUserProfile

    private val _logout: SingleLiveEvent<Unit> = SingleLiveEvent()
    val logout: SingleLiveEvent<Unit> = _logout

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    fun getUserInfo() {
        userInfoRepository.getUserInfo()
            .applySchedulers()
            .subscribeWith(object : TripDisposableSingleObserver<UserResponse>() {
                override fun onSuccess(userResponse: UserResponse) {
                    _userInfo.value = userResponse
                }

            }).addTo(compositeDisposable)
    }

    fun startEditUserProfile() {
        _startEditUserProfile.call()
    }

    fun logout() {
        _logout.call()
    }

    fun backScreen() {
        _backScreen.call()
    }
}