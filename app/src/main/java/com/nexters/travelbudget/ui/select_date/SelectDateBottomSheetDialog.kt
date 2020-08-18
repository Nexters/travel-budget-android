package com.nexters.travelbudget.ui.select_date

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.BottomSheetSelectDateBinding
import com.nexters.travelbudget.ui.base.BaseBottomSheetDialogFragment
import com.nexters.travelbudget.ui.select_date.adapter.SelectDateRVAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class SelectDateBottomSheetDialog : BaseBottomSheetDialogFragment<BottomSheetSelectDateBinding, SelectDateViewModel>(
    R.layout.bottom_sheet_select_date
) {
    override val viewModel: SelectDateViewModel by viewModel()
    private lateinit var listener: (String) -> Unit

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        setupRecyclerView()

        arguments?.let {
            viewModel.addDateData(it.getStringArrayList(BUNDLE_DATE_LIST) ?: ArrayList())
            listener = it.getSerializable(BUNDLE_CLICK_LISTENER) as (String) -> Unit
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            dismissEvent.observe(this@SelectDateBottomSheetDialog, Observer {
                this@SelectDateBottomSheetDialog.dismiss()
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
        private const val BUNDLE_DATE_LIST = "bundle_date_list"
        private const val BUNDLE_CLICK_LISTENER = "bundle_click_listener"

        fun newInstance(items: ArrayList<String>, listener: (String) -> Unit): SelectDateBottomSheetDialog {
            return SelectDateBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putStringArrayList(BUNDLE_DATE_LIST, items)
                    putSerializable(BUNDLE_CLICK_LISTENER, listener as Serializable)
                }
            }
        }
    }
}