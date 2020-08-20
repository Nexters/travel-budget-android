package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.model.enums.BudgetType
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.DEFAULT_DATE
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import retrofit2.HttpException

class TripDetailViewModel(private val detailTripRepository: DetailTripRepository) :
    BaseViewModel() {

    private val _detailTitle = MutableLiveData<String>().apply {
        value = DEFAULT_TITLE
    }
    val detailTitle: LiveData<String> get() = _detailTitle


    private val _tabFocus = MutableLiveData<BudgetType>(BudgetType.SHARED)
    val tabFocus get() = _tabFocus

    private val _tripInfoData = MutableLiveData<TripDetailResponse>()
    val tripInfoData: LiveData<TripDetailResponse> = _tripInfoData

    private val _focusBudgetDate = MutableLiveData<TripDetailResponse.Data?>()
    val focusBudgetDate = _focusBudgetDate


    private val _startManageMember: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startManageMember: SingleLiveEvent<Unit> = _startManageMember

    private val _startRecordSpend: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startRecordSpend: SingleLiveEvent<Unit> = _startRecordSpend

    private val _goToPaymentScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val goToPaymentScreen: SingleLiveEvent<Unit> = _goToPaymentScreen

    private val _startEditTripProfile: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startEditTripProfile: SingleLiveEvent<Unit> = _startEditTripProfile

    private val _goToPieScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val goToPieScreen: SingleLiveEvent<Unit> = _goToPieScreen

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen


    // TODO: 2020/08/19 fragment start
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isReady = MutableLiveData<String>("Y")
    val isReady get() = _isReady

    val _showDateDialogEvent = SingleLiveEvent<Unit>()
    val showDateDialogEvent get() = _showDateDialogEvent

    private val _focusDate = MutableLiveData<String>("준비")
    val focusDate: LiveData<String> get() = _focusDate

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _remainAmount = MutableLiveData<String>()
    val remainAmount: LiveData<String> = _remainAmount

    private val _suggestAmount = MutableLiveData<String>()
    val suggestAmount: LiveData<String> = _suggestAmount

    private val _purposeAmount = MutableLiveData<String>()
    val purposeAmount: LiveData<String> = _purposeAmount

    private val _sumPayment = MutableLiveData<String>()
    val sumPayment: LiveData<String> = _sumPayment

    private val _tripPaymentList = MutableLiveData<List<TripPaymentResponse>>()
    val tripPaymentList: LiveData<List<TripPaymentResponse>> = _tripPaymentList

    private val _errorTripDetailInfo: SingleLiveEvent<Unit> = SingleLiveEvent()
    val errorTripDetailInfo: SingleLiveEvent<Unit> = _errorTripDetailInfo


    fun showDateDialog() {
        showDateDialogEvent.call()
    }

    //call Payment case
    fun getPaymentTravelData(budgetId: Long, isReady: String, paymentDt: String) {
        if (budgetId == -1L) {
            return
        }

        detailTripRepository.getTripPaymentInfo(budgetId, isReady, paymentDt)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
                _sumPayment.value = it.map(TripPaymentResponse::price).sum().toInt().toMoneyString()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripPaymentResponse>>() {
                override fun onSuccess(result: List<TripPaymentResponse>) {
                    _tripPaymentList.value = result

                }

            }).addTo(compositeDisposable)
    }

    fun setFocusDate(date: String) {
        _focusDate.value = date
    }


    private fun setBudgetData() {
        with(_focusBudgetDate.value) {
            this?.let {
                _purposeAmount.value = purposeAmount.toMoneyString()
                _remainAmount.value = remainAmount.toMoneyString()
                _suggestAmount.value = suggestAmount.toMoneyString()
            } ?: {
                _purposeAmount.value = "default"
                _remainAmount.value = "default"
                _suggestAmount.value = "default"
            }()

        }
    }


    fun getTripDetailData(planId: Long) {
        detailTripRepository.getTripDetailInfo(planId)
            .applySchedulers()
            .doOnSuccess {
            }
            .subscribeWith(object : TripDisposableSingleObserver<TripDetailResponse>() {
                override fun onSuccess(result: TripDetailResponse) {
                    _detailTitle.value = result.name
                    _tripInfoData.value = result
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if ((e as? HttpException)?.code() == 400) {
                        _errorTripDetailInfo.call()
                    }
                }
            }).addTo(compositeDisposable)
    }

    fun goToManageMemberScreen() {
        _startManageMember.call()
    }

    fun goToShowRecordSpend() {
        _startRecordSpend.call()
    }

    fun goToPaymentScreen() {
        _goToPaymentScreen.call()
    }

    fun goToEditTripProfileScreen() {
        _startEditTripProfile.call()
    }

    fun goToPieScreen() {
        _goToPieScreen.call()
    }

    fun backScreen() {
        _backScreen.call()
    }

    fun updateBudgetDate() {
        when (_tabFocus.value) {
            BudgetType.SHARED -> _focusBudgetDate.value = _tripInfoData.value?.shared
            BudgetType.PERSONAL -> _focusBudgetDate.value = _tripInfoData.value?.personal
        }
        setBudgetData()
        updateEachDateSpendList()
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
            getPaymentTravelData(
                it.budgetId,
                isReady.value!!,
                callDate.convertToServerDate()
            )
        }
    }

    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }

}