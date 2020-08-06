package com.nexters.travelbudget.ui.main.record

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRecordingTravelBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 기록할 여행 fragment class
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class RecordingTravelFragment :
    BaseFragment<FragmentRecordingTravelBinding, MainViewModel>(R.layout.fragment_recording_travel),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@RecordingTravelFragment)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, activity?.theme))
        }
    }

    override fun onRefresh() {
    }

    companion object {
        fun newInstance(): RecordingTravelFragment = RecordingTravelFragment()
    }
}