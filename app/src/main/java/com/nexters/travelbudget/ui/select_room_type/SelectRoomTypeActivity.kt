package com.nexters.travelbudget.ui.select_room_type

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivitySelectRoomTypeBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 어떤 유형(공용/개인)의 방을 만들지 선택하는 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.11
 */

class SelectRoomTypeActivity :
    BaseActivity<ActivitySelectRoomTypeBinding, SelectRoomTypeViewModel>(R.layout.activity_select_room_type) {

    override val viewModel: SelectRoomTypeViewModel by viewModel {
        parametersOf(intent.getStringExtra(Constant.EXTRA_USER_NAME))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.finishScreen.observe(this, Observer {
            finish()
        })
    }

    companion object {
        fun getIntent(context: Context, userName: String): Intent {
            return Intent(context, SelectRoomTypeActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_USER_NAME to userName))
            }
        }
    }
}