package com.nexters.travelbudget.ui.edit_trip_profile

import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class EditSharedTripProfileViewModel(private val planId: Long) : BaseViewModel() {

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    fun backScreen() {
        _backScreen.call()
    }
}