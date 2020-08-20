package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TripDetailSharedFragment :
    BaseFragment<FragmentDetailSharedBinding, TripDetailViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailViewModel by sharedViewModel()

    private var day = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailSharedRV()
    }

    private fun observeViewModel() {
    }

    private fun setupDetailSharedRV() {
        binding.rvDetailSharedList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
                // TODO: 2020/08/20  아이템 수정 항목 Event 추가

            }

        }
    }

    companion object {
        fun newInstance(): TripDetailSharedFragment {
            return TripDetailSharedFragment()
        }
    }

}