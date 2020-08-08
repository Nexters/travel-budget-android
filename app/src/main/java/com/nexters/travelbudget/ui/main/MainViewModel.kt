package com.nexters.travelbudget.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.repository.MainTripRecordRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class MainViewModel(private val mainTripRecordRepository: MainTripRecordRepository) :
    BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _tripRecordList = MutableLiveData<List<TripRecordResponse>>()
    val tripRecordList = _tripRecordList

    private val _startCreateRoom = SingleLiveEvent<Unit>()
    val startCreateRoom = _startCreateRoom

    fun getTripRecordData(isComing: Boolean) {
        mainTripRecordRepository.getTripRecordInfo(isComing)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripRecordResponse>>() {
                override fun onSuccess(result: List<TripRecordResponse>) {
                    _tripRecordList.value = result
                }

            }).addTo(compositeDisposable)
    }

    fun createTripRoom() {
        _startCreateRoom.call()
    }

}