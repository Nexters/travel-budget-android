package com.nexters.travelbudget.ui.main.record

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRecordingTravelBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.main.record.adapter.TravelRecordRVAdapter
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 기록할 여행 fragment class
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class RecordingTravelFragment :
    BaseFragment<FragmentRecordingTravelBinding, RecordingTravelViewModel>(R.layout.fragment_recording_travel),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: RecordingTravelViewModel by viewModel()

    private var isFirstExecution = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
        setRecordingTravelRV()

        viewModel.startCreateRoom.observe(this, Observer {
            Toast.makeText(context, "여행 만들기", Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirstExecution) {
            isFirstExecution = false
            viewModel.getRecordingTravelData()
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@RecordingTravelFragment)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, activity?.theme))
        }
    }

    private fun setRecordingTravelRV() {
        binding.rvRecordingTravel.run {
            adapter =
                TravelRecordRVAdapter { tripRecordResponse ->
                    // TODO 여행 상세 화면으로 연결 작업
                    Toast.makeText(context, "상세 화면 전환", Toast.LENGTH_LONG).show()
                }
            addItemDecoration(object : CustomItemDecoration() {
                override fun setSpacingForDirection(
                    outRect: Rect,
                    layoutManager: RecyclerView.LayoutManager?,
                    position: Int,
                    itemCount: Int
                ) {
                    if (position == 0) {
                        outRect.top =
                            context.resources.getDimensionPixelSize(R.dimen.spacing_size_30dp)
                    }
                    outRect.bottom =
                        context.resources.getDimensionPixelSize(R.dimen.spacing_size_20dp)
                    outRect.left =
                        context.resources.getDimensionPixelSize(R.dimen.spacing_size_24dp)
                    outRect.right =
                        context.resources.getDimensionPixelSize(R.dimen.spacing_size_24dp)
                }

            })
        }
    }

    override fun onRefresh() {
        viewModel.getRecordingTravelData()
    }

    companion object {
        fun newInstance(): RecordingTravelFragment = RecordingTravelFragment()
    }
}