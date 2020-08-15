package com.nexters.travelbudget.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityMyPageBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.base.TravelApplication
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 마이페이지 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class MyPageActivity :
    BaseActivity<ActivityMyPageBinding, MyPageViewModel>(R.layout.activity_my_page) {

    override val viewModel: MyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserInfo()

        viewModel.startEditUserProfile.observe(this, Observer {
            startActivityForResult(
                EditUserProfileActivity.getIntent(
                    this,
                    viewModel.userInfo.value ?: return@Observer
                ), Constant.REQUEST_CODE_EDIT_USER_NAME
            )
        })

        viewModel.logout.observe(this, Observer {
            TravelApplication.instance.logout()
        })

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_EDIT_USER_NAME && resultCode == Constant.RESULT_OK) {
            viewModel.editUserProfile.value = true
            viewModel.getUserInfo()
        }
    }

    override fun finish() {
        if (viewModel.editUserProfile.value!!) {
            setResult(Constant.RESULT_OK)
        }
        super.finish()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MyPageActivity::class.java)
        }
    }
}