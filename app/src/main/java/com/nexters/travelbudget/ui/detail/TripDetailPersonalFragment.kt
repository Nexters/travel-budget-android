package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import androidx.lifecycle.Observer
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailPersonalFragment() :
    BaseFragment<FragmentDetailPersonalBinding, TripDetailPersonalViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailPersonalViewModel by viewModel()

    private val budgetData by lazy {
        arguments?.getParcelable<TripDetailResponse.Data>(BUDGET_DATA)
    }

    private val dateItems by lazy {
        arguments?.getStringArrayList(DATE_ITEMS) ?: listOf<String>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailPersonalRV()

        viewModel.getPaymentPersonalTravelData(35, "N", "2020-08-04") // test 여기서 값을 어떻게 받아오니

        budgetData?.let {
            viewModel.setBudgetData(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            showPersonalDateDialogEvent.observe(this@TripDetailPersonalFragment, Observer {
                SelectDateBottomSheetDialog(dateItems) {
                    setPersonalDate(it)
                }.show(parentFragmentManager, "bottom_sheet")
            })
        }
    }

    private fun setupDetailPersonalRV() {
        binding.rvDetailPersonalList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
                Log.e("dain", tripPaymentResponse.paymentDt.toString())
//                if (tripPaymentResponse.paymentDt == "Y") {
//                    startActivity(TripDetailActivity.getIntent(context.applicationContext).apply {
//                        putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
//                    })
//                } else {
//                    startActivity(
//                        TripDetailAloneActivity.getIntent(context.applicationContext).apply {
//                            putExtra(Constant.EXTRA_PLAN_ID, tripRecordResponse.planId)
//                        })
//                }
            }
        }
    }

    companion object {
        private const val BUDGET_DATA = "budget_data"
        private const val DATE_ITEMS = "DATE_ITEMS"

        fun newInstance(data: TripDetailResponse.Data?, items: List<String>): TripDetailPersonalFragment {
            return TripDetailPersonalFragment().apply {
                arguments = bundleOf(BUDGET_DATA to data, DATE_ITEMS to items)
            }
        }

//        fun newInstance(): TripDetailPersonalFragment {
//            return TripDetailPersonalFragment()
//        }
    }


}