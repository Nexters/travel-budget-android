package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.repository.DetailPaymentRepository
import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DetailSharedData
import com.nexters.travelbudget.utils.ext.DEFAULT_DATE
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import java.util.*

class TripDetailAloneViewModel(
    private val detailTripRepository: DetailTripRepository,
    private val detailPaymentRepository: DetailPaymentRepository
) : BaseViewModel() {
    private val _newDetailAloneList = MutableLiveData<ArrayList<DetailSharedData>>()
    val newDetailSharedList: LiveData<ArrayList<DetailSharedData>> get() = _newDetailAloneList

    private val _detailAloneMoney = MutableLiveData<String>()
    val detailAloneMoney: LiveData<String> get() = _detailAloneMoney

    private val _detailAloneTitle = MutableLiveData<String>().apply {
        value = TripDetailAloneViewModel.DEFAULT_TITLE
    }
    val detailAloneTitle: LiveData<String> get() = _detailAloneTitle

//    private val _detailAloneDate = MutableLiveData<String>("준비")
//    val detailAloneDate: LiveData<String> get() = _detailAloneDate

    private val _focusDate = MutableLiveData<String>("준비")
    val focusDate: LiveData<String> get() = _focusDate

    private val _isReady = MutableLiveData<String>("Y")
    val isReady get() = _isReady

    private val _focusBudgetDate = MutableLiveData<TripDetailResponse.Data?>()
    val focusBudgetDate = _focusBudgetDate


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

    private val _goToPieScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val goToPieScreen: SingleLiveEvent<Unit> = _goToPieScreen

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    private val _goToPaymentScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val goToPaymentScreen: SingleLiveEvent<Unit> = _goToPaymentScreen

    private val _startEditTripProfile: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startEditTripProfile: SingleLiveEvent<Unit> = _startEditTripProfile

    fun showDateAloneDialog() {
        showDateAloneDialogEvent.call()
    }

    fun setFocusDate(date: String) {
        _focusDate.value = date
    }

    fun getTripDetailAloneData(id: Long) {
        detailTripRepository.getTripDetailInfo(id)
            .applySchedulers()
            .doOnSuccess {


            }
            .subscribeWith(object : TripDisposableSingleObserver<TripDetailResponse>() {
                override fun onSuccess(result: TripDetailResponse) {
                    _tripDetailAlone.value = result
                    _detailAloneTitle.value = result.name
                    _focusBudgetDate.value = result.personal

                    setBudgetData()
                    updateEachDateSpendList()
                }

            }).addTo(compositeDisposable)
    }

    fun updateEachDateSpendList() {
        val date = _focusDate.value!!
        val callDate: String
        if (date == "준비") {
            isReady.value = "Y"
            isEmptyList.value = true
            callDate = DEFAULT_DATE
        } else {
            isReady.value = "N"
            callDate = date
        }

        focusBudgetDate.value?.let {
            getPaymentAloneTravelData(
                it.budgetId,
                isReady.value!!,
                callDate.convertToServerDate()
            )
        }
    }

    private fun setBudgetData() {
        with(_focusBudgetDate.value) {
            this?.let {
                _purposeAloneAmount.value = purposeAmount.toMoneyString()
                _remainAloneAmount.value = remainAmount.toMoneyString()
                _suggestAloneAmount.value = suggestAmount.toMoneyString()
            } ?: {
                _purposeAloneAmount.value = "default"
                _remainAloneAmount.value = "default"
                _suggestAloneAmount.value = "default"
            }()

        }
    }


    fun getPaymentAloneTravelData(budgetId: Long, isReady: String, paymentDt: String) {
        if (budgetId == -1L) {
            return
        }

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
                    _tripPaymentAloneList.value = result
                }

            }).addTo(compositeDisposable)
    }

    fun goToPieScreen() {
        _goToPieScreen.call()
    }

    fun backScreen() {
        _backScreen.call()
    }

    fun goToPaymentScreen() {
        _goToPaymentScreen.call()
    }

    fun goToEditTripProfileScreen() {
        _startEditTripProfile.call()
    }

    companion object {
        private const val DEFAULT_TITLE = ""
    }
}