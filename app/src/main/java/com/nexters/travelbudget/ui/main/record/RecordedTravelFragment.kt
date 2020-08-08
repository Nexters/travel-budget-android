package com.nexters.travelbudget.ui.main.record

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRecordedTravelBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 다녀온 여행 fragment class
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class RecordedTravelFragment :
    BaseFragment<FragmentRecordedTravelBinding, RecordedTravelViewModel>(R.layout.fragment_recorded_travel),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: RecordedTravelViewModel by viewModel()

    private var isFirstExecution = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstExecution) {
            isFirstExecution = false
            viewModel.getTripRecordedData()
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@RecordedTravelFragment)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, activity?.theme))
        }
    }

    override fun onRefresh() {
        viewModel.getTripRecordedData()
    }

    companion object {
        fun newInstance(): RecordedTravelFragment = RecordedTravelFragment()
    }
}