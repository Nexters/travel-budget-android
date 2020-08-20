package com.nexters.travelbudget.ui.main.record

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRecordedTravelBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.TripDetailActivity
import com.nexters.travelbudget.ui.detail.TripDetailAloneActivity
import com.nexters.travelbudget.ui.main.record.adapter.TravelRecordRVAdapter
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.ext.showToastMessage
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
        setRecordedTravelRV()
        viewModel.getTripRecordedData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getTripRecordedData()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@RecordedTravelFragment)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, activity?.theme))
        }
    }

    private fun setRecordedTravelRV() {
        binding.rvRecordedTravel.run {
            adapter = TravelRecordRVAdapter { tripRecordResponse ->
                if (tripRecordResponse.isPublic == "Y") {
                    activity?.startActivityForResult(TripDetailActivity.getIntent(context.applicationContext).apply {
                        putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
                    }, Constant.REQUEST_CODE_TRIP_DETAIL)
                } else {
                    activity?.startActivityForResult(
                        TripDetailAloneActivity.getIntent(context.applicationContext).apply {
                            putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
                        }, Constant.REQUEST_CODE_TRIP_DETAIL)
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
        viewModel.getTripRecordedData()
    }

    companion object {
        fun newInstance(): RecordedTravelFragment = RecordedTravelFragment()
    }
}