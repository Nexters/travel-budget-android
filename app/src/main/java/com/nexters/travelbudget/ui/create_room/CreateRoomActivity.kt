package com.nexters.travelbudget.ui.create_room

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityCreateRoomBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 사용자가 직접 방 정보를 입력 후 생성하는 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class CreateRoomActivity :
    BaseActivity<ActivityCreateRoomBinding, CreateRoomViewModel>(R.layout.activity_create_room) {

    override val viewModel: CreateRoomViewModel by viewModel {
        parametersOf(
            intent.getStringExtra(Constant.EXTRA_USER_NAME),
            intent.getStringExtra(Constant.EXTRA_ROOM_TYPE)
        )
    }

    companion object {
        fun getIntent(context: Context, userName: String, roomType: String): Intent {
            return Intent(context, CreateRoomActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        Constant.EXTRA_USER_NAME to userName,
                        Constant.EXTRA_ROOM_TYPE to roomType
                    )
                )
            }
        }
    }
}