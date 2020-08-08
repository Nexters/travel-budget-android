package com.nexters.travelbudget.ui.statistics

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.widget.piechart.PieData

class StatisticsViewModel : BaseViewModel() {
    private val _newPieDataList = MutableLiveData<ArrayList<PieData>>()
    val newPieDataList: LiveData<ArrayList<PieData>> get() = _newPieDataList

    private val _totalBudget = MutableLiveData<String>()
    val totalBudget: LiveData<String> get() = _totalBudget

    private val _spendAmount = MutableLiveData<String>()
    val spendAmount: LiveData<String> get() = _spendAmount

    fun addData() {
        val dataList = getData()
        _newPieDataList.value = dataList

        var totalSpend = 0
        for (pie in dataList) {
            totalSpend += pie.value
        }
        _spendAmount.value = totalSpend.toMoneyString()
        _totalBudget.value = 3000000.toMoneyString()
    }

    private fun getData(): ArrayList<PieData> {
        return ArrayList<PieData>().apply {
            add(PieData("식비", 1500000, Color.rgb(190,50,50)))
            add(PieData("간식", 375000, Color.rgb(190,190,20)))
            add(PieData("문화", 400000, Color.rgb(100,180,70)))
            add(PieData("교통", 390000, Color.rgb(220,90,100)))
            add(PieData("기타", 200000, Color.rgb(50,150,130)))
        }.apply {
            sort()
        }
    }
}