package com.nexters.travelbudget.ui.statistics

import android.graphics.Color
import android.os.Bundle
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityStatisticsBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.widget.piechart.PieData
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsActivity : BaseActivity<ActivityStatisticsBinding, StatisticsViewModel>(
    R.layout.activity_statistics
) {
    override val viewModel: StatisticsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.pcStatistics.addData(PieData("a", 20f, Color.rgb(190,50,50)))
        binding.pcStatistics.addData(PieData("a", 20f, Color.rgb(130,150,50)))
        binding.pcStatistics.addData(PieData("c", 20f, Color.rgb(50,150,130)))
    }
}