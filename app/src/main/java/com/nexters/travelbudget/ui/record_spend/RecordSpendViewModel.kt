package com.nexters.travelbudget.ui.record_spend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.model.SpendCategoryModel
import com.nexters.travelbudget.model.enums.SpendCategoryEnum
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class RecordSpendViewModel : BaseViewModel() {
    private val defaultSpendCategoryList = ArrayList<SpendCategoryModel>().apply {
        for (item in SpendCategoryEnum.values()) {
            add(SpendCategoryModel(item.defaultRes, item.titleKor, false))
        }
    }

    private val selectedSpendCategoryList = ArrayList<SpendCategoryModel>().apply {
        for (item in SpendCategoryEnum.values()) {
            add(SpendCategoryModel(item.selectedRes, item.titleKor, true))
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

    val selectDateEvent = SingleLiveEvent<Unit>()
    val selectTimeEvent = SingleLiveEvent<Unit>()

    private var latestClicked = 0
    private var selectedCategory: String? = null

    fun categoryItemClick(spendCategory: SpendCategoryModel) {
        selectedCategory = spendCategory.title

        val values = SpendCategoryEnum.values()
        for (i in values.indices) {
            if (spendCategory.title == values[i].titleKor) {
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

        _isActivated.value = !amount.isNullOrEmpty() && !selectedCategory.isNullOrEmpty() && !explain.isNullOrEmpty()
    }

    fun recordSpend() {
        if (isActivated.value == true) {
            val type = if (isShared.value == true) {
                "공용"
            } else {
                "개인"
            }

            liveToastMessage.value = "$type, ${spendAmount.value}원, ${selectedCategory}, ${spendExplain.value}"
        }
    }
}