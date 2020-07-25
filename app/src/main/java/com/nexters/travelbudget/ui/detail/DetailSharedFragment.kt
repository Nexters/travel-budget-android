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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSharedFragment() : BaseFragment<FragmentDetailSharedBinding, DetailSharedViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: DetailSharedViewModel by viewModel()

    companion object {
        fun newInstance(): DetailSharedFragment {
            return DetailSharedFragment().apply {
                arguments = bundleOf()
            }
        }
    }
}