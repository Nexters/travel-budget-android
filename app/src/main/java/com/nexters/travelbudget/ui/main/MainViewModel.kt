package com.nexters.travelbudget.ui.main

import android.util.Log
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.repository.MainTripRecordRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo

class MainViewModel(private val mainTripRecordRepository: MainTripRecordRepository) :
    BaseViewModel() {

    fun getTripRecordData(isComing: Boolean) {
        mainTripRecordRepository.getTripRecordInfo(isComing)
            .applySchedulers()
            .subscribeWith(object : TripDisposableSingleObserver<List<TripRecordResponse>>() {
                override fun onSuccess(result: List<TripRecordResponse>) {

                }

            }).addTo(compositeDisposable)
    }

}