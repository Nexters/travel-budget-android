package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DetailSharedData


class TripDetailSharedViewModel : BaseViewModel() {
    private val _newDetailSharedList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailSharedList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailSharedList

    private val _detailSharedMoney = MutableLiveData<String>()
    val detailSharedMoney: LiveData<String> get() = _detailSharedMoney

    fun addData() {
        val dataList = getData()
        _newDetailSharedList.value = dataList

//        _newDetailSharedList.value = dataList
//        _detailSharedMoney.value = ?
    }

    private fun getData(): ArrayList<DetailSharedData> {
        return ArrayList<DetailSharedData>().apply {
            add(DetailSharedData("식비", 1500000))
            add(DetailSharedData("간식", 375000))
            add(DetailSharedData("문화", 400000))
            add(DetailSharedData("교통", 390000))
            add(DetailSharedData("기타", 200000))
        }
    }
}