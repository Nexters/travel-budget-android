package com.nexters.travelbudget.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.convertToViewDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TripDetailSharedFragment :
    BaseFragment<FragmentDetailSharedBinding, TripDetailSharedViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailSharedViewModel by viewModel()

    private val budgetData by lazy {
        arguments?.getParcelable<TripDetailResponse.Data>(BUDGET_DATA)
    }

    private val dateItems by lazy {
        arguments?.getStringArrayList(DATE_ITEMS) ?: listOf<String>()
    }

    private val budgetId by lazy {
        budgetData?.budgetId ?: -1
    }

    private var day = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailSharedRV()
        setDay()

        viewModel.setSharedDate(day)
        (requireActivity() as? TripDetailActivity)?.setDay(viewModel.detailSharedDate.value ?: "")

        val isReady = if (day == "준비") {
            "Y"
        } else {
            "N"
        }

        if (day == "준비") {
            viewModel.isEmptyList.value = true
            viewModel.getPaymentTravelData(budgetId, isReady, day)
        }
        else {
            viewModel.getPaymentTravelData(budgetId, isReady, day.convertToServerDate())
        }

        budgetData?.let {
            viewModel.setBudgetData(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            showDateDialogEvent.observe(this@TripDetailSharedFragment, Observer {
                SelectDateBottomSheetDialog.newInstance(day, ArrayList(dateItems)) {
                    setSharedDate(it)
                    (requireActivity() as? TripDetailActivity)?.setDay(it)
                    val isReady = if (it == "준비") {
                        "Y"
                    } else {
                        "N"
                    }

                    if (it == "준비") {
                        viewModel.isEmptyList.value = true
                        getPaymentTravelData(budgetId, isReady, dateItems[0])
                    }
                    else {
                        getPaymentTravelData(budgetId, isReady, it.convertToServerDate())
                    }
                }.show(parentFragmentManager, "bottom_sheet")
            })
        }
    }

    private fun setupDetailSharedRV() {
        binding.rvDetailSharedList.run {
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
        d = if (dateItems.contains(d)) {
            d.convertToViewDate()
        } else {
            "준비"
        }

        day = d
    }

    companion object {
        private const val BUDGET_DATA = "budget_data"
        private const val DATE_ITEMS = "DATE_ITEMS"

        fun newInstance(
            data: TripDetailResponse.Data?,
            items: List<String>
        ): TripDetailSharedFragment {
            return TripDetailSharedFragment().apply {
                arguments = bundleOf(BUDGET_DATA to data, DATE_ITEMS to items)
            }
        }
    }
}