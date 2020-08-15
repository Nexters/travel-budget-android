package com.nexters.travelbudget.ui.manage_member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import com.nexters.travelbudget.data.repository.TripMemberRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo

/**
 * 여행 친구 관리 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageMemberViewModel(planId: Long, private val tripMemberRepository: TripMemberRepository) :
    BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _planId: MutableLiveData<Long> = MutableLiveData(planId)
    val planId: LiveData<Long> = _planId

    private val _tripMembers: MutableLiveData<TripMemberResponse> = MutableLiveData()
    val tripMembers: LiveData<TripMemberResponse> = _tripMembers

    fun getTripMembers() {
        val planId = _planId.value ?: return

        if (planId == -1L) return

        tripMemberRepository.getTripMembers(planId)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .subscribeWith(object : TripDisposableSingleObserver<TripMemberResponse>() {
                override fun onSuccess(result: TripMemberResponse) {
                    _tripMembers.value = result
                }

            }).addTo(compositeDisposable)
    }
}