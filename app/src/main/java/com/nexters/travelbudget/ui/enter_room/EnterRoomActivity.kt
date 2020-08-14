package com.nexters.travelbudget.ui.enter_room

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEnterRoomBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 방 입장(초대코드 입력) 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class EnterRoomActivity :
    BaseActivity<ActivityEnterRoomBinding, EnterRoomViewModel>(R.layout.activity_enter_room) {

    override val viewModel: EnterRoomViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, EnterRoomActivity::class.java)
        }
    }
}