package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailAloneBinding
import com.nexters.travelbudget.model.enums.ActivityResultType
import com.nexters.travelbudget.model.enums.ActivityResultType.*
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.edit_trip_profile.EditTripProfileActivity
import com.nexters.travelbudget.ui.record_spend.RecordSpendActivity
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.statistics.StatisticsActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.convertToViewDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TripDetailAloneActivity :
    BaseActivity<ActivityDetailAloneBinding, TripDetailAloneViewModel>(R.layout.activity_detail_alone) {
    override val viewModel: TripDetailAloneViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    private val planId by lazy {
        intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L)
    }

    private var day: String = ""

    private var modifiesTripRoomInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupDetailAloneRV()
        setDay()

        viewModel.getTripDetailAloneData(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))
        viewModel.getTripDetailAloneData(planId)

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Constant.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_EDIT_TRIP_PROFILE) {
                modifiesTripRoomInfo = true
                when (data?.getStringExtra(Constant.EXTRA_ACTIVITY_RESULT_TYPE) ?: "") {
                    SCREEN_REFRESH.name -> {
                        viewModel.getTripDetailAloneData(planId)
                    }
                    SCREEN_FINISH.name -> {
                        finish()
                    }
                    else -> {
                        return
                    }
                }
            }
        }
    }

    override fun finish() {
        if (modifiesTripRoomInfo) {
            setResult(Constant.RESULT_OK)
        }
        super.finish()
    }

    private fun observeViewModel() {
        with(viewModel) {
            showDateAloneDialogEvent.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer
                SelectDateBottomSheetDialog.newInstance(detailAloneDate.value ?: "준비", ArrayList(tripDetailResponse.dates)) {
                    setAloneDate(it)
                    val isReady = if (it == "준비") {
                        "Y"
                    } else {
                        "N"
                    }

                    if (it == "준비") {
                        viewModel.isEmptyList.value = true
                        getPaymentAloneTravelData(
                            tripDetailResponse.personal?.budgetId ?: -1L,
                            isReady,
                            viewModel.tripDetailAlone.value?.dates?.get(0) ?: "0000-00-00"
                        )
                    } else {
                        getPaymentAloneTravelData(
                            tripDetailResponse.personal?.budgetId ?: -1L,
                            isReady,
                            it.convertToServerDate()
                        )
                    }
                    getPaymentAloneTravelData(
                        tripDetailResponse.personal?.budgetId ?: -1L,
                        isReady,
                        it
                    )
                }.show(supportFragmentManager, "bottom_sheet")
            })

            goToPieScreen.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer

                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.PERSONAL
                startActivity(
                    Intent(
                        this@TripDetailAloneActivity,
                        StatisticsActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                    })
            })

            goToPaymentScreen.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = viewModel.tripDetailAlone.value ?: return@Observer
                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.PERSONAL
                val editMode = EditModeType.CREATE_MODE
                startActivity(
                    Intent(
                        this@TripDetailAloneActivity,
                        RecordSpendActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_EDIT_MODE, editMode)
                        putExtra(Constant.EXTRA_CURRENT_DATE, detailAloneDate.value)
                        putExtra(Constant.EXTRA_CURRENT_DATE, day)
                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(tripDetailResponse.dates)
                        )
                    })
            })

            startEditTripProfile.observe(this@TripDetailAloneActivity, Observer {
                val memberId = viewModel.tripDetailAlone.value?.memberId ?: -1L
                startActivityForResult(
                    Intent(
                        EditTripProfileActivity.getIntent(
                            this@TripDetailAloneActivity,
                            planId, memberId, TravelRoomType.PERSONAL.name
                        )
                    ), Constant.REQUEST_CODE_EDIT_TRIP_PROFILE
                )
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

    private fun setDay() {
        var d = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        d = if (viewModel.tripDetailAlone.value?.dates?.contains(d) == true) {
            d.convertToViewDate()
        } else {
            "준비"
        }

        this.day = d
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailAloneActivity::class.java)
        }
    }


}