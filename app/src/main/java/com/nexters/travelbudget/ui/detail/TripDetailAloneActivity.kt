package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailAloneBinding
import com.nexters.travelbudget.model.enums.ActivityResultType.SCREEN_FINISH
import com.nexters.travelbudget.model.enums.ActivityResultType.SCREEN_REFRESH
import com.nexters.travelbudget.model.enums.BudgetType
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.edit_trip_profile.EditTripProfileActivity
import com.nexters.travelbudget.ui.record_spend.RecordSpendActivity
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.statistics.StatisticsActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.convertToViewDate
import com.nexters.travelbudget.utils.ext.showToastMessage
import com.nexters.travelbudget.utils.getNowDate
import com.nexters.travelbudget.utils.isBetweenDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TripDetailAloneActivity :
    BaseActivity<ActivityDetailAloneBinding, TripDetailAloneViewModel>(R.layout.activity_detail_alone) {
    override val viewModel: TripDetailAloneViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    private val planId by lazy {
        intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L)
    }


    private var modifiesTripRoomInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupDetailAloneRV()

        viewModel.getTripDetailAloneData(planId)

        val startDate = intent.getStringExtra(Constant.EXTRA_PLAN_START_DATE)!!
        val endDate = intent.getStringExtra(Constant.EXTRA_PLAN_END_DATE)!!

        if (getNowDate().isBetweenDate(startDate, endDate)) {
            viewModel.setFocusDate(getNowDate().convertToViewDate())
        }

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
            } else if (requestCode == Constant.REQUEST_CODE_SPEND_CREATE) {
                viewModel.getTripDetailAloneData(planId)
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
                SelectDateBottomSheetDialog.newInstance(
                    focusDate.value!!,
                    ArrayList(tripDetailAlone.value!!.dates)
                ) {
                    setFocusDate(it)
                }.show(fragmentManager, "bottom_sheet")
            })

            goToPieScreen.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer

                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.PERSONAL
                val focusType = BudgetType.PERSONAL

                if (personalBudgetId == -1L) {
                    showToastMessage("예산을 먼저 입력해주세요!")
                    return@Observer
                }

                startActivity(
                    Intent(
                        this@TripDetailAloneActivity,
                        StatisticsActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_FOCUS_TYPE, focusType)
                    })
            })

            focusDate.observe(this@TripDetailAloneActivity, Observer {
                updateEachDateSpendList()
            })

            goToPaymentScreen.observe(this@TripDetailAloneActivity, Observer {
                val tripDetailResponse = tripDetailAlone.value ?: return@Observer
                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.PERSONAL
                val editMode = EditModeType.CREATE_MODE
                val focusType = BudgetType.PERSONAL
                startActivityForResult(
                    Intent(
                        this@TripDetailAloneActivity,
                        RecordSpendActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_EDIT_MODE, editMode)
                        putExtra(Constant.EXTRA_FOCUS_TYPE, focusType)
                        putExtra(Constant.EXTRA_CURRENT_DATE, focusDate.value)

                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(tripDetailResponse.dates)
                        )
                    }, Constant.REQUEST_CODE_SPEND_CREATE)
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
                val tripDetailResponse = viewModel.tripDetailAlone.value ?: return@SharedDetailRVAdapter
                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.PERSONAL
                val editMode = EditModeType.EDIT_MODE
                val focusType = BudgetType.PERSONAL
                val paymentId = tripPaymentResponse.paymentId
                val paymentTitle = tripPaymentResponse.title
                val paymentCategory = tripPaymentResponse.category
                val paymentAmount = tripPaymentResponse.price

                startActivityForResult(
                    Intent(
                        this@TripDetailAloneActivity,
                        RecordSpendActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_EDIT_MODE, editMode)
                        putExtra(Constant.EXTRA_FOCUS_TYPE, focusType)
                        putExtra(Constant.EXTRA_CURRENT_DATE, viewModel.focusDate.value)
                        putExtra(Constant.EXTRA_PAYMENT_ID, paymentId)
                        putExtra(Constant.EXTRA_PAYMENT_TITLE, paymentTitle)
                        putExtra(Constant.EXTRA_PAYMENT_CATEGORY, paymentCategory)
                        putExtra(Constant.EXTRA_PAYMENT_AMOUNT, paymentAmount)

                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(tripDetailResponse.dates)
                        )
                    }, Constant.REQUEST_CODE_SPEND_CREATE)
            }
        }
    }


    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailAloneActivity::class.java)
        }
    }


}