package com.nexters.travelbudget.ui.manage_member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityManageMemberBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 여행 친구 관리 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageMemberActivity :
    BaseActivity<ActivityManageMemberBinding, ManageMemberViewModel>(R.layout.activity_manage_member) {

    override val viewModel: ManageMemberViewModel by viewModel()

    private val manageMemberRVAdapter by lazy {
        ManageMemberRVAdapter(onClickExportMember = {

        }, onClickInviteMember = {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setManageMemberRV()
    }

    private fun setManageMemberRV() {
        binding.rvMembers.run {
            adapter = manageMemberRVAdapter
        }
    }

    companion object {
        fun getIntent(context: Context, planId: Int): Intent {
            return Intent(context, ManageMemberActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_PLAN_ID to planId))
            }
        }
    }
}