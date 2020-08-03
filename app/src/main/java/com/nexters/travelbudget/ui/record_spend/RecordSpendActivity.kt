package com.nexters.travelbudget.ui.record_spend

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityRecordSpendBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordSpendActivity : BaseActivity<ActivityRecordSpendBinding, RecordSpendViewModel>(
    R.layout.activity_record_spend
) {
    override val viewModel: RecordSpendViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSpendCategoryRV()
    }

    private fun setupSpendCategoryRV() {
        with(binding.rvCategory) {
            adapter = SpendCategoryRVAdapter {
                viewModel.categoryItemClick(it)
            }
        }
    }
}