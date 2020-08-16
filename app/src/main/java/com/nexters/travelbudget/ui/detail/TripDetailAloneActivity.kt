package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailAloneBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
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
        with(binding.rvDetailAloneSharedList) {
            adapter = com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter()
        }
    }


    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailAloneActivity::class.java)
        }
    }


}