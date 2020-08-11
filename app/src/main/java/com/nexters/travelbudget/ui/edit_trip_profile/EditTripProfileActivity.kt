package com.nexters.travelbudget.ui.edit_trip_profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEditTripProfileBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTripProfileActivity :
    BaseActivity<ActivityEditTripProfileBinding, EditTripProfileViewModel>(
        R.layout.activity_edit_trip_profile
    ) {
    override val viewModel: EditTripProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbTitle)
    }
}