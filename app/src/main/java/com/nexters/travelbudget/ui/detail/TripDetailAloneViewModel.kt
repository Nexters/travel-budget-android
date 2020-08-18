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

class TripDetailAloneViewModel(private val detailTripRepository: DetailTripRepository, private val detailPaymentRepository: DetailPaymentRepository) : BaseViewModel() {
    private val _newDetailAloneList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailSharedList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailAloneList

    private val _detailAloneMoney = MutableLiveData<String>()
    val detailAloneMoney: LiveData<String> get() = _detailAloneMoney

    private val _detailAloneTitle = MutableLiveData<String>().apply {
        value = TripDetailAloneViewModel.DEFAULT_TITLE
    }
    val detailAloneTitle: LiveData<String> get() = _detailAloneTitle

    private val _detailAloneDate = MutableLiveData<String>("준비")
    val detailAloneDate: LiveData<String> get() = _detailAloneDate

    private val _purposeAloneAmount = MutableLiveData<String>()
    val purposeAloneAmount: LiveData<String> = _purposeAloneAmount

    private val _spendAloneAmount = MutableLiveData<String>()
    val spendAloneAmount: LiveData<String> = _spendAloneAmount

    private val _remainAloneAmount = MutableLiveData<String>()
    val remainAloneAmount: LiveData<String> = _remainAloneAmount

    private val _suggestAloneAmount = MutableLiveData<String>()
    val suggestAloneAmount: LiveData<String> = _suggestAloneAmount

    val showDateAloneDialogEvent = SingleLiveEvent<Unit>()

    private val _tripDetailAlone = MutableLiveData<TripDetailResponse>()
    val tripDetailAlone: LiveData<TripDetailResponse> = _tripDetailAlone

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _tripPaymentAloneList = MutableLiveData<List<TripPaymentResponse>>()
    val tripPaymentAloneList: LiveData<List<TripPaymentResponse>> = _tripPaymentAloneList

    private val _emptyPersonalBudget = MutableLiveData<Boolean>(true)
    val emptyPersonalBudget: LiveData<Boolean> = _emptyPersonalBudget

    private val _sumPayment = MutableLiveData<String>()
    val sumPayment: LiveData<String> = _sumPayment

    fun showDateAloneDialog() {
        showDateAloneDialogEvent.call()
    }

    fun setAloneDate(date: String) {
        _detailAloneDate.value = date
    }

    fun getTripDetailAloneData(id: Long) {
        detailTripRepository.getTripDetailInfo(id)
            .applySchedulers()
            //  .doOnSubscribe { _isLoading.value = true }
            //  .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
//                _isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<TripDetailResponse>() {
                override fun onSuccess(result: TripDetailResponse) {
                    _tripDetailAlone.value = result
                    _detailAloneTitle.value = result.name
                    if(result.personal != null) {
                        _purposeAloneAmount.value = result.personal.purposeAmount.toMoneyString()
                        _remainAloneAmount.value = result.personal.remainAmount.toMoneyString()
                        _suggestAloneAmount.value = result.personal.suggestAmount.toMoneyString()
                    }

                }

            }).addTo(compositeDisposable)

    }

    fun getPaymentAloneTravelData(budgetId: Long, isReady: String, paymentDt: String) {
        detailPaymentRepository.getTripPaymentInfo(budgetId, isReady, paymentDt)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
                _sumPayment.value = it.map(TripPaymentResponse::price).sum().toInt().toMoneyString()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripPaymentResponse>>() {
                override fun onSuccess(result: List<TripPaymentResponse>) {
                    _tripPaymentAloneList.value = result
                }

            }).addTo(compositeDisposable)
    }

    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }
}