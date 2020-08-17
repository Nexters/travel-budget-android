package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.DetailSharedData
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class TripDetailPersonalViewModel(private val detailTripRepository: DetailTripRepository) : BaseViewModel() {

    private val _newDetailPersonalList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailPersonalList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailPersonalList

    private val _detailPersonalMoney = MutableLiveData<String>()
    val detailPersonalMoney: LiveData<String> get() = _detailPersonalMoney

    private val _detailPersonalDate = MutableLiveData<String>("준비")
    val detailPersonalDate: LiveData<String> get() = _detailPersonalDate

    val showPersonalDateDialogEvent = SingleLiveEvent<Unit>()

    private val _purposePersonalAmount = MutableLiveData<String>()
    val purposePersonalAmount: LiveData<String> = _purposePersonalAmount

    private val _spendPersonalAmount = MutableLiveData<String>()
    val spendPersonalAmount: LiveData<String> = _spendPersonalAmount

    private val _remainPersonalAmount = MutableLiveData<String>()
    val remainPersonalAmount: LiveData<String> = _remainPersonalAmount

    private val _suggestPersonalAmount = MutableLiveData<String>()
    val suggestPersonalAmount: LiveData<String> = _suggestPersonalAmount

    private val _isEmpty = MutableLiveData<Boolean>(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun showPersonalDateDialog() {
        showPersonalDateDialogEvent.call()
    }

    fun setPersonalDate(date: String) {
        _detailPersonalDate.value = date
    }

    fun setBudgetData(tripDetailData: TripDetailResponse.Data) {
        DLog.d(tripDetailData.budgetId.toString())
        with(tripDetailData) {
            _purposePersonalAmount.value = purposeAmount.toMoneyString()
            _remainPersonalAmount.value = remainAmount.toMoneyString()
            _suggestPersonalAmount.value = suggestAmount.toMoneyString()
        }
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

}