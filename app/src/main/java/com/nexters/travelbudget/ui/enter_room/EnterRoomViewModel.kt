package com.nexters.travelbudget.ui.enter_room

import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.repository.EnterRoomRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripieCompletableObserver
import io.reactivex.rxkotlin.addTo
import org.json.JSONObject
import retrofit2.HttpException

/**
 * 방 입장(초대코드 입력) 뷰모델
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class EnterRoomViewModel(roomCode: String, private val enterRoomRepository: EnterRoomRepository) :
    BaseViewModel() {

    private val _invitationCode: MutableLiveData<String> = MutableLiveData(roomCode)
    val invitationCode: MutableLiveData<String> = _invitationCode

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    private val _enterTravelRoom: SingleLiveEvent<Unit> = SingleLiveEvent()
    val enterTravelRoom: SingleLiveEvent<Unit> = _enterTravelRoom

    private val _requestSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()
    val requestSuccess: SingleLiveEvent<Unit> = _requestSuccess

    private val _requestFailed: SingleLiveEvent<String> = SingleLiveEvent()
    val requestFailed: SingleLiveEvent<String> = _requestFailed

    fun backScreen() {
        _backScreen.call()
    }

    fun requestEnterTravelRoom() {
        val inviteCode = _invitationCode.value?.trim() ?: return

        enterRoomRepository.requestEnterRoom(inviteCode)
            .applySchedulers()
            .subscribeWith(object : TripieCompletableObserver() {
                override fun onComplete() {
                    _requestSuccess.call()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if ((e as? HttpException)?.code() == 400) {
                        val errorResult = e.response()?.errorBody()?.string() ?: return
                        val jsonObjError = JSONObject(errorResult)
                        val message = jsonObjError.getString("message")
                        _requestFailed.value = message
                    }
                }
            }).addTo(compositeDisposable)

    }
}