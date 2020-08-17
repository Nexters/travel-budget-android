package com.nexters.travelbudget.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
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

class TripDetailAloneViewModel(private val detailTripRepository: DetailTripRepository) : BaseViewModel() {
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

    fun showDateAloneDialog() {
        showDateAloneDialogEvent.call()
    }

    fun addData() {
        val dataList = getData()
        _newDetailAloneList.value = dataList
    }

    fun setAloneBudgetData(tripDetailData: TripDetailResponse.Data) {
        DLog.d(tripDetailData.budgetId.toString())
        with(tripDetailData) {
            _purposeAloneAmount.value = purposeAmount.toMoneyString()
            _remainAloneAmount.value = remainAmount.toMoneyString()
            _suggestAloneAmount.value = suggestAmount.toMoneyString()

        }
    }

    fun getTripDetailAloneData(id: Long) {
        detailTripRepository.getTripDetailInfo(id)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            //  .doOnSubscribe { _isLoading.value = true }
            //  .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                //_isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<TripDetailResponse>() {
                override fun onSuccess(result: TripDetailResponse) {
                    _tripDetailAlone.value = result
                }

            }).addTo(compositeDisposable)

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

    companion object {
        private const val DEFAULT_TITLE = "아직 계획이 없습니다"
    }
}