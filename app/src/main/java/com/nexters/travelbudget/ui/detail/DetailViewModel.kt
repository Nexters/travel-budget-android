package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel

class DetailViewModel : BaseViewModel() {

    private val _detailTitle = MutableLiveData<String>().apply {
        value = "아직 계획이 없습니다"
    }
    val detailTitle: LiveData<String> get() = _detailTitle


}