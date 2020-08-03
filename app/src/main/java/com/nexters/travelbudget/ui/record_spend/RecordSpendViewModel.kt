package com.nexters.travelbudget.ui.record_spend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.model.SpendCategoryModel
import com.nexters.travelbudget.model.enums.SpendCategoryEnum
import com.nexters.travelbudget.ui.base.BaseViewModel
import kotlin.collections.ArrayList

class RecordSpendViewModel : BaseViewModel() {
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

    private var latestClicked = 0

    fun categoryItemClick(spendCategory: SpendCategoryModel) {
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
    }
}