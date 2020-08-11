package com.nexters.travelbudget.ui.main

import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class MainViewModel : BaseViewModel() {

    private val _startCreateRoom = SingleLiveEvent<Unit>()
    val startCreateRoom = _startCreateRoom

    fun createTripRoom() {
        _startCreateRoom.call()
    }
}