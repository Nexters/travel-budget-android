package com.nexters.travelbudget.ui.select_date

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.BottomSheetSelectDateBinding
import com.nexters.travelbudget.ui.base.BaseBottomSheetDialogFragment
import com.nexters.travelbudget.ui.select_date.adapter.SelectDateRVAdapter
import com.nexters.travelbudget.utils.DLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectDateBottomSheetDialog
    : BaseBottomSheetDialogFragment<BottomSheetSelectDateBinding, SelectDateViewModel>(
    R.layout.bottom_sheet_select_date
) {
    override val viewModel: SelectDateViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvDateList.adapter = SelectDateRVAdapter {
            DLog.d(it)
        }.apply {
            add("2018.7.28")
            add("2019.7.28")
            add("2020.7.28")
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            dismissEvent.observe(this@SelectDateBottomSheetDialog, Observer {
                this@SelectDateBottomSheetDialog.dismiss()
            })
        }
    }
}