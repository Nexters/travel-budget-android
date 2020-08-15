package com.nexters.travelbudget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.data.remote.model.response.StatisticsResponse
import com.nexters.travelbudget.data.repository.StatisticsRepository
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import com.nexters.travelbudget.utils.widget.piechart.PieData
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class StatisticsViewModel(private val statisticsRepo: StatisticsRepository) : BaseViewModel() {
    private val _newPieDataList = MutableLiveData<ArrayList<PieData>>()
    val newPieDataList: LiveData<ArrayList<PieData>> get() = _newPieDataList

    private val _totalBudget = MutableLiveData<String>()
    val totalBudget: LiveData<String> get() = _totalBudget

    private val _spendAmount = MutableLiveData<String>()
    val spendAmount: LiveData<String> get() = _spendAmount

    private val _isEmptyData = MutableLiveData(false)
    val isEmptyData: LiveData<Boolean> get() = _isEmptyData

    fun setData(budgetId: Long) {
        statisticsRepo.getStatisticsInfo(budgetId)
            .delay(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .subscribeWith(object : TripDisposableSingleObserver<StatisticsResponse>() {
                override fun onSuccess(result: StatisticsResponse) {
                    _spendAmount.value = result.usedAmount.toMoneyString()
                    _totalBudget.value = result.purposeAmount.toMoneyString()

                    _newPieDataList.value = ArrayList<PieData>().apply {
                        val category = result.categories
                        addIfNotZero(PieData("식비", category.food))
                        addIfNotZero(PieData("문화", category.culture))
                        addIfNotZero(PieData("교통", category.traffic))
                        addIfNotZero(PieData("쇼핑", category.shopping))
                        addIfNotZero(PieData("숙박", category.sleep))
                        addIfNotZero(PieData("간식", category.snack))
                        addIfNotZero(PieData("기타", category.etc))
                    }
                    _isEmptyData.value = newPieDataList.value.isNullOrEmpty()
                }
            }).addTo(compositeDisposable)
    }

    private fun ArrayList<PieData>.addIfNotZero(data: PieData) {
        if (data.value != 0) {
            DLog.d("")
            add(data)
        }
    }
}