package com.nexters.travelbudget.ui.detail

import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailPersonalFragment() : BaseFragment<FragmentDetailPersonalBinding, TripDetailPersonalViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailPersonalViewModel by viewModel()

    companion object {
        fun newInstance(): TripDetailPersonalFragment {
            return TripDetailPersonalFragment().apply {
                arguments = bundleOf()
            }
        }
    }

}