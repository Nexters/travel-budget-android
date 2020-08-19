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
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.statistics.StatisticsActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TripDetailAloneActivity :
    BaseActivity<ActivityDetailAloneBinding, TripDetailAloneViewModel>(R.layout.activity_detail_alone) {
    override val viewModel: TripDetailAloneViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    private var day: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupDetailAloneRV()

        viewModel.getTripDetailAloneData(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))
        viewModel.getPaymentAloneTravelData(intent.getLongExtra(Constant.EXTRA_BUDGET_ID, -1L), "Y", "2020-08-04")

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    private fun observeViewModel() {
        with(viewModel) {
            showDateAloneDialogEvent.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer
                SelectDateBottomSheetDialog.newInstance(ArrayList(tripDetailResponse.dates)) {
                    setAloneDate(it)
                    val isReady = if (it == "준비") {
                        "Y"
                    } else {
                        "N"
                    }

                    getPaymentAloneTravelData(tripDetailResponse.personal.budgetId, isReady, it)
                }.show(supportFragmentManager, "bottom_sheet")
            })

            goToPieScreen.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = viewModel.tripDetailAlone.value ?: return@Observer
                val sharedBudgetId = tripDetailResponse.shared.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal.budgetId ?: -1L
                val roomType = TravelRoomType.SHARED
                startActivity(Intent(this@TripDetailAloneActivity, StatisticsActivity::class.java).apply {
                    putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                    putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                    putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                })
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