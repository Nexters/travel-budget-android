package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailAloneBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailAloneActivity :
    BaseActivity<ActivityDetailAloneBinding, TripDetailAloneViewModel>(R.layout.activity_detail_alone) {
    override val viewModel: TripDetailAloneViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupDetailAloneRV()
        viewModel.addData()

    }

    private fun observeViewModel() {
    }

    private fun setupDetailAloneRV() {
        with(binding.rvDetailAloneList) {
            adapter = com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter()
        }
    }



}