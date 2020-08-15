package com.nexters.travelbudget.ui.select_room_type

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

/**
 * 어떤 유형(공용/개인)의 방을 만들지 선택하는 화면의 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.11
 */

class SelectRoomTypeViewModel(userName: String) : BaseViewModel() {

    private val _userName = MutableLiveData<String>(userName)
    val userName: LiveData<String> = _userName

    private val _travelRoomType = MutableLiveData<String>()
    val travelRoomType = _travelRoomType

    private val _finishScreen = SingleLiveEvent<Unit>()
    val finishScreen = _finishScreen

    private val _goToNextScreen = SingleLiveEvent<Unit>()
    val goToNextScreen = _goToNextScreen

    val allowsGotoNextScreen = MutableLiveData<Boolean>(false)

    fun finishActivity() {
        _finishScreen.call()
    }

    fun goToNextScreen() {
        _goToNextScreen.call()
    }

}