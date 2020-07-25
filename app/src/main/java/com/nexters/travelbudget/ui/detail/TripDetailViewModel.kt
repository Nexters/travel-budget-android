package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class TripDetailViewModel : BaseViewModel() {


    private val _detailTitle = MutableLiveData<String>().apply { value = DEFAULT_TITLE }
    val detailTitle: LiveData<String> get() = _detailTitle

    private val _toShared = SingleLiveEvent<Unit>()
    val toShared = _toShared

    private val _toPersonal = SingleLiveEvent<Unit>()
    val toPersonal = _toPersonal

    fun toShared() { // 이렇게 두번의 과정을 거쳐야하나
        _toShared.call()
    }

    fun toPersonal() {
        _toPersonal.call()
    }

    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }

}