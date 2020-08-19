package com.nexters.travelbudget.ui.edit_trip_profile.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.TripProfileResponse
import com.nexters.travelbudget.data.repository.TripProfileRepository
import com.nexters.travelbudget.model.enums.TripMemberType
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.convertToViewDate
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.observables.ConnectableObservable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class EditSharedTripProfileViewModel(
    private val planId: Long,
    private val memberId: Long,
    private val tripProfileRepository: TripProfileRepository
) : BaseViewModel() {

    private val _isOwner: MutableLiveData<Boolean> = MutableLiveData()
    val isOwner: LiveData<Boolean> = _isOwner

    private val _tripTitle: MutableLiveData<String> = MutableLiveData()
    val tripTitle: MutableLiveData<String> = _tripTitle

    private val _startDate: MutableLiveData<String> = MutableLiveData()
    val startDate: LiveData<String> = _startDate

    private val _endDate: MutableLiveData<String> = MutableLiveData()
    val endDate: LiveData<String> = _endDate

    private val _sharedBudget: MutableLiveData<String> = MutableLiveData()
    val sharedBudget: LiveData<String> = _sharedBudget
    val sharedBudgetChanged = fun(value: String) {
        _sharedBudget.value = value
    }

    private val _personalBudget: MutableLiveData<String> = MutableLiveData()
    val personalBudget: LiveData<String> = _personalBudget
    val personalBudgetChanged = fun(value: String) {
        _personalBudget.value = value.replace(",", "")
    }

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    fun getTripProfileInfo() {
        if (planId == -1L) return

        tripProfileRepository.getSharedTripProfileInfo(planId)
            .applySchedulers()
            .subscribeWith(object : TripDisposableSingleObserver<TripProfileResponse>() {
                override fun onSuccess(result: TripProfileResponse) {
                    _isOwner.value = result.authority == TripMemberType.OWNER.name
                    _tripTitle.value = result.name
                    _sharedBudget.value = result.shared?.amount?.toMoneyString() ?: "0"
                    _personalBudget.value = result.personal?.amount?.toMoneyString() ?: "0"
                    _startDate.value = result.startDate.convertToViewDate()
                    _endDate.value = result.endDate.convertToViewDate()
                }

            }).addTo(compositeDisposable)
    }

    fun backScreen() {
        _backScreen.call()
    }

    fun preparingToEditDate() {
        showToast("날짜 수정 기능은 준비중이에요")
    }
}