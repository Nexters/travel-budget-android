package com.nexters.travelbudget.ui.statistics

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityStatisticsBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.statistics.adapter.PieChartRVAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsActivity : BaseActivity<ActivityStatisticsBinding, StatisticsViewModel>(
    R.layout.activity_statistics
) {
    override val viewModel: StatisticsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupStatisticsRV()
        viewModel.addData()
    }

    private fun observeViewModel() {
        with(viewModel) {
            newPieDataList.observe(this@StatisticsActivity, Observer {
                for (pie in it) {
                    binding.pcStatistics.addData(pie)
                }
            })
        }
    }

    private fun setupStatisticsRV() {
        with(binding.rvStatistics) {
            adapter = PieChartRVAdapter()
        }
    }
}