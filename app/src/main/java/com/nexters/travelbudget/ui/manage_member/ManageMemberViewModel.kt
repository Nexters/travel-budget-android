package com.nexters.travelbudget.ui.manage_member

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import com.nexters.travelbudget.data.repository.TripMemberRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import com.nexters.travelbudget.utils.observer.TripieCompletableObserver
import io.reactivex.rxkotlin.addTo

/**
 * 여행 친구 관리 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageMemberViewModel(
    planId: Long,
    roomTitle: String,
    private val tripMemberRepository: TripMemberRepository
) :
    BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _planId: MutableLiveData<Long> = MutableLiveData(planId)
    val planId: LiveData<Long> = _planId

    private val _roomTitle: MutableLiveData<String> = MutableLiveData(roomTitle)
    val roomTitle: LiveData<String> = _roomTitle

    private val _tripMemberResponse: MutableLiveData<TripMemberResponse> = MutableLiveData()
    val tripMemberResponse: LiveData<TripMemberResponse> = _tripMemberResponse

    private val _successDeleteMember: SingleLiveEvent<Unit> = SingleLiveEvent()
    val successDeleteMember: SingleLiveEvent<Unit> = _successDeleteMember

    private val _failedDeleteMember: SingleLiveEvent<Unit> = SingleLiveEvent()
    val failedDeleteMember: SingleLiveEvent<Unit> = _failedDeleteMember

    private val _successCreateDeepLink: SingleLiveEvent<String> = SingleLiveEvent()
    val successCreateDeepLink: SingleLiveEvent<String> = _successCreateDeepLink

    private val _failedCreateDeepLink: SingleLiveEvent<Unit> = SingleLiveEvent()
    val failedCreateDeepLink: SingleLiveEvent<Unit> = _failedCreateDeepLink

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    fun getTripMembers() {
        val planId = _planId.value ?: -1L

        if (planId == -1L) return

        tripMemberRepository.getTripMembers(planId)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .subscribeWith(object : TripDisposableSingleObserver<TripMemberResponse>() {
                override fun onSuccess(result: TripMemberResponse) {
                    _tripMemberResponse.value = result
                }

            }).addTo(compositeDisposable)
    }

    fun deleteTripMember(memberId: Long) {
        val planId = _planId.value ?: -1L

        if (planId == -1L) return

        tripMemberRepository.deleteTripMember(planId, memberId)
            .applySchedulers()
            .doOnSubscribe { _isLoading.value = true }
            .doAfterTerminate { _isLoading.value = false }
            .subscribeWith(object : TripieCompletableObserver() {
                override fun onComplete() {
                    _successDeleteMember.call()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    _failedDeleteMember.call()
                }
            }).addTo(compositeDisposable)
    }

    fun createDynamicLink() {
        val roomCode = _tripMemberResponse.value?.inviteCode ?: return

        Firebase.dynamicLinks.shortLinkAsync {
            link =
                Uri.parse("https://tripie.com/${Constant.SEGMENT_ROOM}?${Constant.KEY_ROOM_CODE}=$roomCode")
            domainUriPrefix = "https://tripie.page.link"
            androidParameters("com.nexters.travelbudget") {
                build()
            }
        }.addOnSuccessListener {
            _successCreateDeepLink.value = it.shortLink.toString()
        }.addOnFailureListener {
            _failedCreateDeepLink.call()
        }
    }

    fun backScreen() {
        _backScreen.call()
    }
}