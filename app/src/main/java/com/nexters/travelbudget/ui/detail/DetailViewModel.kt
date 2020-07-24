package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel

class DetailViewModel : BaseViewModel() {

    private val _detailTitle = MutableLiveData<String>().apply { value = "아직 계획이 없습니다" }
    val detailTitle: LiveData<String> get() = _detailTitle

    private val _toShared = MutableLiveData<Boolean>().apply {value = false}
    val toShared: LiveData<Boolean> get() = _toShared

    private val _toPersonal = MutableLiveData<Boolean>().apply {value = false}
    val toPersonal: LiveData<Boolean> get() = _toPersonal

    fun toShared() { // 이렇게 두번의 과정을 거쳐야하나
        _toShared.value = true
    }

    fun toPersonal() {
        _toPersonal.value =  true
    }

}