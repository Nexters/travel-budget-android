package com.nexters.travelbudget.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TripDetailPersonalFragment() :
    BaseFragment<FragmentDetailPersonalBinding, TripDetailPersonalViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailPersonalViewModel by viewModel()

    private val budgetData by lazy {
        arguments?.getParcelable<TripDetailResponse.Data>(BUDGET_DATA)
    }

    private val dateItems by lazy {
        arguments?.getStringArrayList(DATE_ITEMS) ?: listOf<String>()
    }

    private val budgetId by lazy {
        budgetData?.budgetId ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailPersonalRV()

        var date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        date = if (dateItems.contains(date)) {
            date
        } else {
            "준비"
        }
        viewModel.setPersonalDate(date)
        (requireActivity() as? TripDetailActivity)?.setDay(viewModel.detailPersonalDate.value ?: "")

        val isReady = if (date == "준비") {
            "Y"
        } else {
            "N"
        }

        viewModel.getPaymentPersonalTravelData(budgetId, "Y", "2020-08-04")

        budgetData?.let {
            viewModel.setBudgetData(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            showPersonalDateDialogEvent.observe(this@TripDetailPersonalFragment, Observer {
                SelectDateBottomSheetDialog.newInstance(ArrayList(dateItems)) {
                    setPersonalDate(it)
                    (requireActivity() as? TripDetailActivity)?.setDay(it)
                    val isReady = if (it == "준비") {
                        "Y"
                    } else {
                        "N"
                    }

                    getPaymentPersonalTravelData(budgetId, isReady, it)
                }.show(parentFragmentManager, "bottom_sheet")
            })
        }
    }

    private fun setupDetailPersonalRV() {
        binding.rvDetailPersonalList.run {
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
        private const val BUDGET_DATA = "budget_data"
        private const val DATE_ITEMS = "DATE_ITEMS"

        fun newInstance(
            data: TripDetailResponse.Data?,
            items: List<String>
        ): TripDetailPersonalFragment {
            return TripDetailPersonalFragment().apply {
                arguments = bundleOf(BUDGET_DATA to data, DATE_ITEMS to items)
            }
        }
    }


}