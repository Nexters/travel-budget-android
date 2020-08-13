package com.nexters.travelbudget.ui.create_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * 사용자가 직접 방 정보를 입력 후 생성하는 화면의 ViewModel
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class CreateRoomViewModel(userName: String, roomType: String) : BaseViewModel() {

    private val nextScreenSubject: PublishSubject<String> = PublishSubject.create()

    /** 사용자 이름 */
    private val _userName: MutableLiveData<String> = MutableLiveData(userName)
    val userName: LiveData<String> = _userName

    /** 여행 타입
     *  Shared : 공동 여행
     *  Personal : 개인 여행
     *  */
    private val _roomType: MutableLiveData<String> = MutableLiveData(roomType)
    val roomType: LiveData<String> = _roomType
    val isSharedRoom = roomType == TravelRoomType.SHARED.name

    /** 여행 이름 */
    private val _roomName: MutableLiveData<String> = MutableLiveData()
    val roomName: MutableLiveData<String> = _roomName

    private val _openCalendar: SingleLiveEvent<Unit> = SingleLiveEvent()
    val openCalendar: SingleLiveEvent<Unit> = _openCalendar

    /** 여행 시작 날짜 */
    private val _travelStartDate: MutableLiveData<String> = MutableLiveData()
    val travelStartDate: MutableLiveData<String> = _travelStartDate

    /** 여행 종료 날짜 */
    private val _travelEndDate: MutableLiveData<String> = MutableLiveData()
    val travelEndDate: MutableLiveData<String> = _travelEndDate

    /** 공동 여행 비용 */
    private val _travelSharedBudget: MutableLiveData<String> = MutableLiveData("")
    val travelSharedBudget: MutableLiveData<String> = _travelSharedBudget
    val sharedBudgetChanged = fun(value: String) {
        _travelSharedBudget.value = value
    }

    /** 마지막 스크린인지 여부 */
    private val _isLastScreen: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLastScreen: MutableLiveData<Boolean> = _isLastScreen

    private val _nextScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val nextScreen: SingleLiveEvent<Unit> = _nextScreen

    private val _successCreateRoom: SingleLiveEvent<Unit> = SingleLiveEvent()
    val successCreateRoom: SingleLiveEvent<Unit> = _successCreateRoom

    private val _backScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val backScreen: SingleLiveEvent<Unit> = _backScreen

    private val _finishScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val finishScreen: SingleLiveEvent<Unit> = _finishScreen

    init {
        nextScreenSubject
            .throttleFirst(1000L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                if (it == NEXT_STEP) {
                    _nextScreen.call()
                } else {
                    _successCreateRoom.call()
                }
            }.addTo(compositeDisposable)
    }

    fun openCalendar() {
        openCalendar.call()
    }

    fun nextScreen() {
        if (_isLastScreen.value!!) {
            nextScreenSubject.onNext(CREATE_ROOM)
        } else {
            nextScreenSubject.onNext(NEXT_STEP)
        }
    }

    fun backScreen() {
        _backScreen.call()
    }

    fun finishActivity() {
        _finishScreen.call()
    }

    companion object {
        const val NEXT_STEP = "NEXT_STEP"
        const val CREATE_ROOM = "CREATE_ROOM"
    }
}