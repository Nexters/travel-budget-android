package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.utils.DLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailSharedFragment :
    BaseFragment<FragmentDetailSharedBinding, TripDetailSharedViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailSharedViewModel by viewModel()

    private val budgetData by lazy {
        arguments?.getParcelable<TripDetailResponse.Data>(BUDGET_DATA)
    }

    private val dateItems by lazy {
        arguments?.getStringArrayList(DATE_ITEMS) ?: listOf<String>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailSharedRV()
        viewModel.addData()

        budgetData?.let {
            viewModel.setBudgetData(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            showDateDialogEvent.observe(this@TripDetailSharedFragment, Observer {
                SelectDateBottomSheetDialog(dateItems) {
                    setSharedDate(it)
                }.show(parentFragmentManager, "bottom_sheet")
            })
        }
    }

    private fun setupDetailSharedRV() {
        with(binding.rvDetailSharedList) {
            adapter = SharedDetailRVAdapter()
        }
    }


    companion object {
        private const val BUDGET_DATA = "budget_data"
        private const val DATE_ITEMS = "DATE_ITEMS"

        fun newInstance(data: TripDetailResponse.Data, items: List<String>): TripDetailSharedFragment {
            return TripDetailSharedFragment().apply {
                arguments = bundleOf(BUDGET_DATA to data, DATE_ITEMS to items)
            }
        }
    }
}