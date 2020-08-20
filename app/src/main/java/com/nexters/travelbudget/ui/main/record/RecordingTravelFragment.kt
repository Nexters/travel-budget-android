package com.nexters.travelbudget.ui.main.record

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRecordingTravelBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.TripDetailActivity
import com.nexters.travelbudget.ui.detail.TripDetailAloneActivity
import com.nexters.travelbudget.ui.main.MainActivity
import com.nexters.travelbudget.ui.main.record.adapter.TravelRecordRVAdapter
import com.nexters.travelbudget.utils.Constant
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
        setRecordingTravelRV()
        viewModel.getRecordingTravelData()

        viewModel.startCreateRoom.observe(this, Observer {
            context?.run {
                (activity as? MainActivity)?.goToSelectRoomTypeActivity()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getRecordingTravelData()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@RecordingTravelFragment)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, activity?.theme))
        }
    }

    private fun setRecordingTravelRV() {
        binding.rvRecordingTravel.run {
            adapter = TravelRecordRVAdapter { tripRecordResponse ->

                if (tripRecordResponse.isPublic == "Y") {
                    activity?.startActivityForResult(
                        TripDetailActivity.getIntent(context.applicationContext).apply {
                            putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
                            putExtra(Constant.EXTRA_PLAN_START_DATE, tripRecordResponse.startDate)
                            putExtra(Constant.EXTRA_PLAN_END_DATE, tripRecordResponse.endDate)
                        }, Constant.REQUEST_CODE_TRIP_DETAIL
                    )
                } else {
                    activity?.startActivityForResult(
                        TripDetailAloneActivity.getIntent(context.applicationContext).apply {
                            putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
                            putExtra(Constant.EXTRA_PLAN_START_DATE, tripRecordResponse.startDate)
                            putExtra(Constant.EXTRA_PLAN_END_DATE, tripRecordResponse.endDate)
                            putExtra(Constant.EXTRA_BUDGET_ID, tripRecordResponse.budgetId)
                        }, Constant.REQUEST_CODE_TRIP_DETAIL
                    )
                }
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