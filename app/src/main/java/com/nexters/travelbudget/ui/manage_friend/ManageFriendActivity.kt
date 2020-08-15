package com.nexters.travelbudget.ui.manage_friend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityManageFriendBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 여행 친구 관리 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageFriendActivity :
    BaseActivity<ActivityManageFriendBinding, ManageFriendViewModel>(R.layout.activity_manage_friend) {

    override val viewModel: ManageFriendViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getIntent(context: Context, planId: Int): Intent {
            return Intent(context, ManageFriendActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_PLAN_ID to planId))
            }
        }
    }
}