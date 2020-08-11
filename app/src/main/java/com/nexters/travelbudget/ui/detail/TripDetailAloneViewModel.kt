package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DetailSharedData

class TripDetailAloneViewModel : BaseViewModel() {
    private val _newDetailAloneList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailSharedList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailAloneList

    private val _detailAloneMoney = MutableLiveData<String>()
    val detailAloneMoney: LiveData<String> get() = _detailAloneMoney

    private val _detailAloneTitle = MutableLiveData<String>().apply { value =
        TripDetailAloneViewModel.DEFAULT_TITLE
    }
    val detailAloneTitle: LiveData<String> get() = _detailAloneTitle

    fun addData() {
        val dataList = getData()
        _newDetailAloneList.value = dataList
    }

    private fun getData(): ArrayList<DetailSharedData> {
        return ArrayList<DetailSharedData>().apply {
            add(DetailSharedData("식비", 1500000))
            add(DetailSharedData("간식", 375000))
            add(DetailSharedData("문화", 400000))
            add(DetailSharedData("교통", 390000))
            add(DetailSharedData("기타", 200000))
            add(DetailSharedData("기타", 200000))
            add(DetailSharedData("기타", 200000))
            add(DetailSharedData("기타", 200000))
        }
    }

    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }
}