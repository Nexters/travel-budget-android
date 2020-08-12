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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.setType(it.getString("type", BUNDLE_STATISTICS_SHARED))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        setupStatisticsRV()
        viewModel.addData()
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
        const val BUNDLE_STATISTICS_SHARED = "bundle_statistics_shared"
        const val BUNDLE_STATISTICS_PERSONAL = "bundle_statistics_personal"

        fun newInstance(type: String): StatisticsFragment {
            return StatisticsFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }
        }
    }
}