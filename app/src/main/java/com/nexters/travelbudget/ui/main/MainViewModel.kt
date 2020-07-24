package com.nexters.travelbudget.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val _toTest = MutableLiveData<Boolean>().apply {value = false}
    val toTest: LiveData<Boolean> get() = _toTest

    fun toTest() {
        _toTest.value = true
    }

}