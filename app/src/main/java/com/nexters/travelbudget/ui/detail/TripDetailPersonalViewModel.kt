package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.repository.DetailPaymentRepository
import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.DetailSharedData
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class TripDetailPersonalViewModel(private val detailPaymentRepository: DetailPaymentRepository) : BaseViewModel() {

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

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _isEmpty = MutableLiveData<Boolean>(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _tripPaymentPersonalList = MutableLiveData<List<TripPaymentResponse>>()
    val tripPaymentPersonalList: LiveData<List<TripPaymentResponse>> = _tripPaymentPersonalList

    private val _sumPayment = MutableLiveData<String>()
    val sumPayment: LiveData<String> = _sumPayment

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

    fun getPaymentPersonalTravelData(budgetId: Long, isReady: String, paymentDt: String) {
        if (budgetId == -1L) return

        detailPaymentRepository.getTripPaymentInfo(budgetId, isReady, paymentDt)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
                _sumPayment.value = it.map(TripPaymentResponse::price).sum().toInt().toMoneyString()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripPaymentResponse>>() {
                override fun onSuccess(result: List<TripPaymentResponse>) {
                    _tripPaymentPersonalList.value = result
                }

            }).addTo(compositeDisposable)
    }



}