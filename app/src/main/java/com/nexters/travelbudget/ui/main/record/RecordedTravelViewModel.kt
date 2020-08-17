package com.nexters.travelbudget.ui.main.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.data.repository.MainTripRecordRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * 다녀온 여행 ViewModel class
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.08
 */
class RecordedTravelViewModel(private val mainTripRecordRepository: MainTripRecordRepository) :
    BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    private val _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList = _isEmptyList

    private val _tripRecordedList = MutableLiveData<List<TripRecordResponse>>()
    val tripRecordedList: LiveData<List<TripRecordResponse>> = _tripRecordedList

    fun getTripRecordedData() {
        mainTripRecordRepository.getTripRecordInfo(false)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .doOnSuccess {
                _isEmptyList.value = it.isEmpty()
            }
            .subscribeWith(object : TripDisposableSingleObserver<List<TripRecordResponse>>() {
                override fun onSuccess(result: List<TripRecordResponse>) {
                    _tripRecordedList.value = result
                }

            }).addTo(compositeDisposable)

    }


}