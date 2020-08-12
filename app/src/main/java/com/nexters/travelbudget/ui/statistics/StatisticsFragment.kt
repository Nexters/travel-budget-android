package com.nexters.travelbudget.ui.statistics

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentStatisticsBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.statistics.adapter.PieChartRVAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticsViewModel> (
    R.layout.fragment_statistics
) {
    override val viewModel: StatisticsViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        setupStatisticsRV()
//        viewModel.setData(14)
    }

    private fun observeViewModel() {
        with(viewModel) {
            newPieDataList.observe(viewLifecycleOwner, Observer {
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

    companion object {
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }
}