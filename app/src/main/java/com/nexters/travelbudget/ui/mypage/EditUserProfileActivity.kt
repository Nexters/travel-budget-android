package com.nexters.travelbudget.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.UserResponse
import com.nexters.travelbudget.databinding.ActivityEditUserProfileBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.showToastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 사용자 정보 수정 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class EditUserProfileActivity :
    BaseActivity<ActivityEditUserProfileBinding, EditUserProfileViewModel>(R.layout.activity_edit_user_profile) {

    override val viewModel: EditUserProfileViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(Constant.EXTRA_USER_INFO))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.successEditNickname.observe(this, Observer {
            showToastMessage(getString(R.string.success_edit_nickname_toast_message))
            setResult(Constant.RESULT_OK)
            finish()
        })

        viewModel.errorEditNickname.observe(this, Observer {
            showToastMessage(getString(R.string.request_fail))
        })
    }

    companion object {
        fun getIntent(context: Context, userResponse: UserResponse): Intent {
            return Intent(context, EditUserProfileActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_USER_INFO to userResponse))
            }
        }
    }

}