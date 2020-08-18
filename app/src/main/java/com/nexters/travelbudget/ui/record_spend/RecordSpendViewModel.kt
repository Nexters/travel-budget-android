package com.nexters.travelbudget.ui.record_spend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.request.RecordPaymentRequest
import com.nexters.travelbudget.data.remote.model.response.StatisticsResponse
import com.nexters.travelbudget.data.repository.RecordSpendRepository
import com.nexters.travelbudget.model.SpendCategoryModel
import com.nexters.travelbudget.model.enums.SpendCategoryEnum
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.convertDateToMills
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import com.nexters.travelbudget.utils.observer.TripieCompletableObserver
import com.nexters.travelbudget.utils.widget.piechart.PieData
import io.reactivex.rxkotlin.addTo
import org.json.JSONObject
import retrofit2.HttpException
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RecordSpendViewModel(private val recordSpendRepository: RecordSpendRepository) :
    BaseViewModel() {
    private val defaultSpendCategoryList = ArrayList<SpendCategoryModel>().apply {
        for (item in SpendCategoryEnum.values()) {
            add(SpendCategoryModel(item.defaultRes, item.title, false))
        }
    }

    private val selectedSpendCategoryList = ArrayList<SpendCategoryModel>().apply {
        for (item in SpendCategoryEnum.values()) {
            add(SpendCategoryModel(item.selectedRes, item.title, true))
        }
    }

    private val _spendCategoryList = MutableLiveData<ArrayList<SpendCategoryModel>>().apply {
        value = ArrayList(defaultSpendCategoryList)
    }
    val spendCategoryList: LiveData<ArrayList<SpendCategoryModel>> get() = _spendCategoryList

    private val _notifySelectedCategoryItem =
        MutableLiveData<Pair<Pair<SpendCategoryModel, Int>, Pair<SpendCategoryModel, Int>>>()
    val notifySelectedCategoryItem: LiveData<Pair<Pair<SpendCategoryModel, Int>, Pair<SpendCategoryModel, Int>>>
        get() = _notifySelectedCategoryItem

    private val _isShared = MutableLiveData(true)
    val isShared: LiveData<Boolean> get() = _isShared

    private val _spendAmount = MutableLiveData<String>()
    val spendAmount: LiveData<String> get() = _spendAmount

    val spendExplain = MutableLiveData<String>()

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> get() = _selectedTime

    private val _isActivated = MutableLiveData(false)
    val isActivated: LiveData<Boolean> get() = _isActivated

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val selectDateEvent = SingleLiveEvent<Unit>()
    val selectTimeEvent = SingleLiveEvent<Unit>()

    val recordSpendFinishEvent = SingleLiveEvent<String>()

    private var latestClicked = 0
    private var selectedCategory: String? = null

    private var sharedBudgetId = -1L
    private var personalBudgetId = -1L

    fun setBudgetId(sharedBudgetId: Long, personalBudgetId: Long) {
        this.sharedBudgetId = sharedBudgetId
        this.personalBudgetId = personalBudgetId
    }

    fun categoryItemClick(spendCategory: SpendCategoryModel) {
        selectedCategory = spendCategory.title

        val values = SpendCategoryEnum.values()
        for (i in values.indices) {
            if (spendCategory.title == values[i].title) {
                _notifySelectedCategoryItem.value = Pair(
                    Pair(defaultSpendCategoryList[latestClicked], latestClicked),
                    Pair(selectedSpendCategoryList[i], i)
                )
                _spendCategoryList.value?.set(i, selectedSpendCategoryList[i])
                _spendCategoryList.value?.set(
                    latestClicked,
                    defaultSpendCategoryList[latestClicked]
                )
                latestClicked = i
                break
            }
        }
        checkComplete()
    }

    fun setSpendAmount(value: String) {
        _spendAmount.value = value
    }

    fun selectDate() {
        selectDateEvent.call()
    }

    fun selectTime() {
        selectTimeEvent.call()
    }

    fun setDate(value: String) {
        _selectedDate.value = value
    }

    fun setTime(value: String) {
        _selectedTime.value = value
    }

    fun selectShared(b: Boolean) {
        _isShared.value = b
    }

    fun checkComplete() {
        DLog.d("")
        val amount = spendAmount.value
        val explain = spendExplain.value

        _isActivated.value =
            !amount.isNullOrEmpty() && !selectedCategory.isNullOrEmpty() && !explain.isNullOrEmpty()
    }

    fun recordSpend() {
        if (isActivated.value == true) {
            val budgetId = if (isShared.value == true) {
                sharedBudgetId
            } else {
                personalBudgetId
            }

            val isReady = if (selectedDate.value == "준비") {
                "Y"
            } else {
                "N"
            }
            val paymentDt = if (isReady == "Y") {
                1596521640L
            } else {
                convertDateToMills(selectedDate.value ?: return, selectedTime.value ?: return)
            }

            recordSpendRepository.recordPayments(
                RecordPaymentRequest(
                    translateCategory(selectedCategory ?: return),
                    isReady,
                    paymentDt,
                    spendExplain.value ?: return,
                    spendAmount.value?.replace(",", "")?.toInt() ?: return,
                    budgetId
                )
            ).applySchedulers()
                .doOnSubscribe { _isLoading.value = true }
                .doAfterTerminate { _isLoading.value = false }
                .subscribeWith(object : TripieCompletableObserver() {
                    override fun onComplete() {
                        recordSpendFinishEvent.value = "지출 기록이 추가되었어요"
                    }
                }).addTo(compositeDisposable)

        }
    }

    private fun translateCategory(category: String): String {
        return when (category) {
            "식비" -> "FOOD"
            "문화" -> "CULTURE"
            "교통" -> "TRAFFIC"
            "쇼핑" -> "SHOPPING"
            "숙박" -> "SLEEP"
            "간식" -> "SNACK"
            "기타" -> "ETC"

            else -> ""
        }
    }
}