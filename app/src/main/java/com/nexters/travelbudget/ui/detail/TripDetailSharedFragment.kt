package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailSharedFragment() : BaseFragment<FragmentDetailSharedBinding, TripDetailSharedViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailSharedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailSharedRV()
        viewModel.addData()
    }

    private fun observeViewModel() {
        with(viewModel) {
            newDetailSharedList.observe(this@TripDetailSharedFragment, Observer {
                for (pie in it) {
                    binding.rvDetailSharedList.addData(pie)
                }
            })
        }
    }

    private fun setupDetailSharedRV() {
        with(binding.rvDetailSharedList) {
            adapter = SharedDetailRVAdapter()
        }
    }


    companion object {
        fun newInstance(): TripDetailSharedFragment {
            return TripDetailSharedFragment()
        }
    }
}