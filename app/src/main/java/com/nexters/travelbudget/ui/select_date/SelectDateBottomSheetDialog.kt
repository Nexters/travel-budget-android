package com.nexters.travelbudget.ui.select_date

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.BottomSheetSelectDateBinding
import com.nexters.travelbudget.ui.base.BaseBottomSheetDialogFragment
import com.nexters.travelbudget.ui.select_date.adapter.SelectDateRVAdapter
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.convertToViewDate
import com.nexters.travelbudget.utils.ext.isBold
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable
import kotlin.collections.ArrayList

class SelectDateBottomSheetDialog :
    BaseBottomSheetDialogFragment<BottomSheetSelectDateBinding, SelectDateViewModel>(
        R.layout.bottom_sheet_select_date
    ) {
    override val viewModel: SelectDateViewModel by viewModel()
    private lateinit var listener: (String) -> Unit
    private lateinit var currentDate: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        setupRecyclerView()

        arguments?.let {
            val dateItems = it.getStringArrayList(BUNDLE_DATE_LIST)?.map { date ->
                date.convertToViewDate()
            } ?: ArrayList()
            viewModel.addDateData(dateItems)
            listener = it.getSerializable(BUNDLE_CLICK_LISTENER) as (String) -> Unit
            currentDate = it.getString(BUNDLE_CURRENT_DATE) ?: "none"
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            dismissEvent.observe(this@SelectDateBottomSheetDialog, Observer {
                dismiss()
            })

            selectReadyEvent.observe(this@SelectDateBottomSheetDialog, Observer {
                listener("준비")
                dismiss()
            })

            dateList.observe(this@SelectDateBottomSheetDialog, Observer {
                if (currentDate == "준비") {
                    setReadySelected(true)
                } else {
                    (binding.rvDateList.adapter as? SelectDateRVAdapter)?.setBoldItem(currentDate)
                    setReadySelected(false)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvDateList) {
            adapter = SelectDateRVAdapter {
                listener(it)
                dismiss()
            }
        }
    }

    companion object {
        private const val BUNDLE_CURRENT_DATE = "bundle_current_date"
        private const val BUNDLE_DATE_LIST = "bundle_date_list"
        private const val BUNDLE_CLICK_LISTENER = "bundle_click_listener"

        fun newInstance(
            date: String,
            items: ArrayList<String>,
            listener: (String) -> Unit
        ): SelectDateBottomSheetDialog {
            return SelectDateBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CURRENT_DATE, date)
                    putStringArrayList(BUNDLE_DATE_LIST, items)
                    putSerializable(BUNDLE_CLICK_LISTENER, listener as Serializable)
                }
            }
        }
    }
}