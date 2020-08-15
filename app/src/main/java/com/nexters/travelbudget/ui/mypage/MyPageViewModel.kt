package com.nexters.travelbudget.ui.mypage

import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

/**
 * 마이페이지 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class MyPageViewModel: BaseViewModel() {

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    fun backScreen() {
        _backScreen.call()
    }
}