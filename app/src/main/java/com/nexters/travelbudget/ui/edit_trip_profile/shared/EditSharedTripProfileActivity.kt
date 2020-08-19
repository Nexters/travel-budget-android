package com.nexters.travelbudget.ui.edit_trip_profile.shared

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEditSharedTripProfileBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditSharedTripProfileActivity :
    BaseActivity<ActivityEditSharedTripProfileBinding, EditSharedTripProfileViewModel>(R.layout.activity_edit_shared_trip_profile) {

    override val viewModel: EditSharedTripProfileViewModel by viewModel {
        parametersOf(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTripProfileInfo()

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    companion object {
        fun getIntent(context: Context, planId: Long): Intent {
            return Intent(context, EditSharedTripProfileActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_PLAN_ID to planId))
            }
        }
    }

}