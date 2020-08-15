package com.nexters.travelbudget.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityMyPageBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.base.TravelApplication
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

        viewModel.logout.observe(this, Observer {
            TravelApplication.instance.logout()
        })

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MyPageActivity::class.java)
        }
    }
}