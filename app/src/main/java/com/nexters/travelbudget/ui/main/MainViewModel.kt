package com.nexters.travelbudget.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.data.repository.UserInfoRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo

class MainViewModel(private val userInfoRepository: UserInfoRepository) : BaseViewModel() {

    private val _startCreateRoom = SingleLiveEvent<Unit>()
    val startCreateRoom = _startCreateRoom

    private val _startMyPage = SingleLiveEvent<Unit>()
    val startMyPage = _startMyPage

    private val _startEnterRoom = SingleLiveEvent<Unit>()
    val startEnterRoom = _startEnterRoom

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun getUserInfo() {
        userInfoRepository.getUserInfo()
            .applySchedulers()
            .subscribeWith(object : TripDisposableSingleObserver<UserResponse>() {
                override fun onSuccess(userResponse: UserResponse) {
                    _userName.value = userResponse.nickname
                }

            }).addTo(compositeDisposable)
    }

    fun createTripRoom() {
        _startCreateRoom.call()
    }

    fun goToMyPage() {
        _startMyPage.call()
    }

    fun enterTripRoom() {
        _startEnterRoom.call()
    }
}