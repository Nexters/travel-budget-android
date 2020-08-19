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

    private val _isReady = MutableLiveData<Boolean>(false)
    val isReady: LiveData<Boolean> = _isReady

    val dismissEvent = SingleLiveEvent<Unit>()

    fun dismiss() {
        dismissEvent.call()
    }

    fun addDateData(dates: List<String>) {
        _dateList.value = dates
    }

    fun setReady() {
        _isReady.value = true
    }
}