package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.remote.model.response.UserResponse
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


class TripDetailSharedViewModel(private val detailPaymentRepository: DetailPaymentRepository) : BaseViewModel() {
    private val _newDetailSharedList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailSharedList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailSharedList

    private val _detailSharedMoney = MutableLiveData<String>()
    val detailSharedMoney: LiveData<String> get() = _detailSharedMoney

    private val _detailSharedDate = MutableLiveData<String>("준비")
    val detailSharedDate: LiveData<String> get() = _detailSharedDate

    val showDateDialogEvent = SingleLiveEvent<Unit>()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _tripDetailList = MutableLiveData<TripDetailResponse>()
    val tripDetailList: LiveData<TripDetailResponse> = _tripDetailList

    private val _tripPaymentList = MutableLiveData<List<TripPaymentResponse>>()
    val tripPaymentList: LiveData<List<TripPaymentResponse>> = _tripPaymentList

    private val _purposeAmount = MutableLiveData<String>()
    val purposeAmount: LiveData<String> = _purposeAmount

    private val _spendAmount = MutableLiveData<String>()
    val spendAmount: LiveData<String> = _spendAmount

    private val _remainAmount = MutableLiveData<String>()
    val remainAmount: LiveData<String> = _remainAmount

    private val _suggestAmount = MutableLiveData<String>()
    val suggestAmount: LiveData<String> = _suggestAmount

    fun showDateDialog() {
        showDateDialogEvent.call()
    }

    fun setSharedDate(date: String) {
        _detailSharedDate.value = date
    }

    fun setBudgetData(tripDetailData: TripDetailResponse.Data) {
        DLog.d(tripDetailData.budgetId.toString())
        with(tripDetailData) {
            _purposeAmount.value = purposeAmount.toMoneyString()
            _remainAmount.value = remainAmount.toMoneyString()
            _suggestAmount.value = suggestAmount.toMoneyString()
        }
    }

    fun getPaymentTravelData(budgetId: Long, isReady: String, paymentDt: String) {
        detailPaymentRepository.getTripPaymentInfo(budgetId, isReady, paymentDt)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripPaymentResponse>>() {
                override fun onSuccess(result: List<TripPaymentResponse>) {
                    _tripPaymentList.value = result
                }

            }).addTo(compositeDisposable)
    }


}