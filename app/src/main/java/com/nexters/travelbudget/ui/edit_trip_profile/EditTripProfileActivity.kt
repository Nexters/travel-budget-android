package com.nexters.travelbudget.ui.edit_trip_profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEditTripProfileBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditTripProfileActivity :
    BaseActivity<ActivityEditTripProfileBinding, EditTripProfileViewModel>(R.layout.activity_edit_trip_profile) {

    override val viewModel: EditTripProfileViewModel by viewModel {
        parametersOf(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getIntent(context: Context, planId: Long): Intent {
            return Intent(context, EditTripProfileActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_PLAN_ID to planId))
            }
        }
    }

}