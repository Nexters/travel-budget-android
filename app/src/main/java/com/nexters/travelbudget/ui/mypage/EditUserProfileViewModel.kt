package com.nexters.travelbudget.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.data.repository.EditUserProfileRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripieCompletableObserver
import io.reactivex.rxkotlin.addTo

/**
 * 사용자 정보 수정 화면 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class EditUserProfileViewModel(
    userResponse: UserResponse,
    private val editUserProfileRepository: EditUserProfileRepository
) : BaseViewModel() {

    private val _userInfo: MutableLiveData<UserResponse> = MutableLiveData(userResponse)
    val userInfo: LiveData<UserResponse> = _userInfo

    private val _userName: MutableLiveData<String> = MutableLiveData(userResponse.nickname)
    val userName: MutableLiveData<String> = _userName

    private val _successEditNickname: SingleLiveEvent<Unit> = SingleLiveEvent()
    val successEditNickname: SingleLiveEvent<Unit> = _successEditNickname

    private val _errorEditNickname: SingleLiveEvent<Unit> = SingleLiveEvent()
    val errorEditNickname: SingleLiveEvent<Unit> = _errorEditNickname

    fun requestEditUserProfile() {
        if (userName.value.isNullOrEmpty()) {
            return
        }
        editUserProfileRepository.requestEditUserProfile(userName.value!!)
            .applySchedulers()
            .subscribeWith(object : TripieCompletableObserver() {
                override fun onComplete() {
                    _successEditNickname.call()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    _errorEditNickname.call()
                }
            }).addTo(compositeDisposable)
    }
}