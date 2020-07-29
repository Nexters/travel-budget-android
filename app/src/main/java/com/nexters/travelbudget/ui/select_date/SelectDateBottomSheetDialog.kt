package com.nexters.travelbudget.ui.select_date

import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.BottomSheetSelectDateBinding
import com.nexters.travelbudget.ui.base.BaseBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectDateBottomSheetDialog
    : BaseBottomSheetDialogFragment<BottomSheetSelectDateBinding, SelectDateViewModel>(
    R.layout.bottom_sheet_select_date
) {
    override val viewModel: SelectDateViewModel by viewModel()
}