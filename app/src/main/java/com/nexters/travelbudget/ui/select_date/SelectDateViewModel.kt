package com.nexters.travelbudget.ui.select_date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class SelectDateViewModel : BaseViewModel() {
    private val _dateList = MutableLiveData<ArrayList<String>>()
    val dateList: LiveData<ArrayList<String>> get() = _dateList

    val dismissEvent = SingleLiveEvent<Unit>()

    fun dismiss() {
        dismissEvent.call()
    }

    fun addDateData() {
        val list = ArrayList<String>().apply {
            add("2018.7.28")
            add("2019.7.28")
            add("2020.7.28")
            add("2020.7.29")
            add("2020.7.30")
            add("2020.7.31")
        }

        _dateList.value = list
    }
}