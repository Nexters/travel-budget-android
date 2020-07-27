package com.nexters.travelbudget.ui.detail

import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailSharedFragment() : BaseFragment<FragmentDetailSharedBinding, TripDetailSharedViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailSharedViewModel by viewModel()
}