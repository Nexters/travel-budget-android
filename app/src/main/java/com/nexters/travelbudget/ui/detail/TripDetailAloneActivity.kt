package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.ActivityDetailAloneBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailAloneActivity :
    BaseActivity<ActivityDetailAloneBinding, TripDetailAloneViewModel>(R.layout.activity_detail_alone) {
    override val viewModel: TripDetailAloneViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupDetailAloneRV()

        viewModel.getTripDetailAloneData(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))

        viewModel.getPaymentAloneTravelData(intent.getLongExtra(Constant.EXTRA_BUDGET_ID, -1L), "Y", "2020-08-04")
    }

    private fun observeViewModel() {
        with(viewModel) {
            showDateAloneDialogEvent.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer
//                SelectDateBottomSheetDialog(tripDetailResponse.dates) {
//                    setAloneDate(it)
//                    viewModel.getPaymentAloneTravelData(tripDetailResponse.personal?.budgetId ?: -1L, "N", it)
//                }.show(supportFragmentManager, "bottom_sheet")
            })
        }
    }

    private fun setupDetailAloneRV() {
        binding.rvDetailAloneSharedList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
            }
            addItemDecoration(object : CustomItemDecoration() {
                override fun setSpacingForDirection(
                    outRect: Rect,
                    layoutManager: RecyclerView.LayoutManager?,
                    position: Int,
                    itemCount: Int
                ) {
                    outRect.top = resources.getDimensionPixelSize(R.dimen.spacing_size_12dp)
                    outRect.bottom = if (position == itemCount - 1) {
                        resources.getDimensionPixelSize(R.dimen.spacing_size_24dp)
                    } else {
                        0
                    }
                }
            })
        }
    }


    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailAloneActivity::class.java)
        }
    }


}