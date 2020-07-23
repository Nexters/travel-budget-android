package com.nexters.travelbudget.ui.detail

import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    override val viewModel: DetailViewModel by viewModel()


}