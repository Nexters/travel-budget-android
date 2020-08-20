package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TripDetailPersonalFragment() :
    BaseFragment<FragmentDetailPersonalBinding, TripDetailViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailPersonalRV()
    }

    private fun observeViewModel() {
    }


    private fun setupDetailPersonalRV() {
        binding.rvDetailPersonalList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
                // TODO: 2020/08/20  아이템 수정 항목 Event 추가
            }

        }
    }
    companion object {

        fun newInstance(): TripDetailPersonalFragment {
            return TripDetailPersonalFragment()
        }
    }
}


