package com.nexters.travelbudget.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.model.enums.BudgetType
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.record_spend.RecordSpendActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList

class TripDetailPersonalFragment() :
    BaseFragment<FragmentDetailPersonalBinding, TripDetailViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailPersonalRV()
    }

    private fun observeViewModel() {
    }


    private fun setupDetailPersonalRV() {
        binding.rvDetailPersonalList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
                val tripDetailResponse = viewModel.tripInfoData.value ?: return@SharedDetailRVAdapter
                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.SHARED
                val editMode = EditModeType.EDIT_MODE
                val focusType = BudgetType.PERSONAL
                val paymentId = tripPaymentResponse.paymentId
                val paymentTitle = tripPaymentResponse.title
                val paymentCategory = tripPaymentResponse.category
                val paymentAmount = tripPaymentResponse.price

                startActivityForResult(
                    Intent(
                        requireContext(),
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

        fun newInstance(): TripDetailPersonalFragment {
            return TripDetailPersonalFragment()
        }
    }
}


