package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPersonalFragment() : BaseFragment<FragmentDetailPersonalBinding, DetailPersonalViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: DetailPersonalViewModel by viewModel()

    companion object {
        fun newInstance(): DetailPersonalFragment {
            return DetailPersonalFragment().apply {
                arguments = bundleOf()
            }
        }
    }

}