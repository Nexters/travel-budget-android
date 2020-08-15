package com.nexters.travelbudget.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.ui.base.BaseViewModel

/**
 * 사용자 정보 수정 화면 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class EditUserProfileViewModel(userResponse: UserResponse) : BaseViewModel() {

    private val _userInfo: MutableLiveData<UserResponse> = MutableLiveData(userResponse)
    val userInfo: LiveData<UserResponse> = _userInfo

    private val _userName: MutableLiveData<String> = MutableLiveData(userResponse.nickname)
    val userName: MutableLiveData<String> = _userName
}