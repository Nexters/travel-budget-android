package com.nexters.travelbudget.ui.main.record

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
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

    private var isFirstExecution = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()

        viewModel.startCreateRoom.observe(this, Observer {
            Toast.makeText(context, "여행 만들기", Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirstExecution) {
            isFirstExecution = false
            viewModel.getTripRecordData(true)
        }
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