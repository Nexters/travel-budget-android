package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.data.repository.DetailTripRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class TripDetailViewModel(
    private val planId: Long,
    private val detailTripRepository: DetailTripRepository
) :
    BaseViewModel() {

    private val _detailTitle = MutableLiveData<String>().apply {
        value = DEFAULT_TITLE
    }
    val detailTitle: LiveData<String> get() = _detailTitle

    private val _planIdLiveData = MutableLiveData<Long>().apply {
        value = planId
    }
    val planIdLiveData get() = _planIdLiveData

    private val _toShared = SingleLiveEvent<Unit>()
    val toShared = _toShared

    private val _toPersonal = SingleLiveEvent<Unit>()
    val toPersonal = _toPersonal

    private val _tripDetail = MutableLiveData<TripDetailResponse>()
    val tripDetail: LiveData<TripDetailResponse> = _tripDetail

    private val _startManageMember: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startManageMember: SingleLiveEvent<Unit> = _startManageMember

    private val _startRecordSpend: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startRecordSpend: SingleLiveEvent<Unit> = _startRecordSpend

    private val _goToPaymentScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val goToPaymentScreen: SingleLiveEvent<Unit> = _goToPaymentScreen


    fun toShared() {
        _toShared.call()
    }

    fun toPersonal() {
        _toPersonal.call()
    }

    fun setDetailTitle(tripName: String) {
        with(tripName) {
            _detailTitle.value = tripName
        }
    }

    fun getTripDetailData() {
        detailTripRepository.getTripDetailInfo(_planIdLiveData.value!!)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            //  .doOnSubscribe { _isLoading.value = true }
            //  .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                //_isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<TripDetailResponse>() {
                override fun onSuccess(result: TripDetailResponse) {
                    _tripDetail.value = result
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


    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }

}