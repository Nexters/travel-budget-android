package com.nexters.travelbudget.ui.select_date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class SelectDateViewModel : BaseViewModel() {
    private val _dateList = MutableLiveData<List<String>>()
    val dateList: LiveData<List<String>> get() = _dateList

    private val _tripDetailList = MutableLiveData<TripDetailResponse>()
    val tripDetailList: LiveData<TripDetailResponse> = _tripDetailList

    private val _isReadySelected = MutableLiveData(false)
    val isReadySelected: LiveData<Boolean> get() = _isReadySelected

    val selectReadyEvent = SingleLiveEvent<Unit>()

    val dismissEvent = SingleLiveEvent<Unit>()

    fun closeDialog() {
        dismissEvent.call()
    }

    fun addDateData(dates: List<String>) {
        _dateList.value = dates
    }

    fun selectReady() {
        selectReadyEvent.call()
    }

    fun setReadySelected(b: Boolean) {
        _isReadySelected.value = b
    }
}