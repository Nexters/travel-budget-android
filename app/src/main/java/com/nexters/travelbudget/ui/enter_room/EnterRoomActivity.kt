package com.nexters.travelbudget.ui.enter_room

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEnterRoomBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.showToastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 방 입장(초대코드 입력) 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class EnterRoomActivity :
    BaseActivity<ActivityEnterRoomBinding, EnterRoomViewModel>(R.layout.activity_enter_room) {

    override val viewModel: EnterRoomViewModel by viewModel {
        parametersOf(intent.getStringExtra(Constant.EXTRA_ROOM_CODE) ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })

        viewModel.requestSuccess.observe(this, Observer {
            setResult(Constant.RESULT_OK)
            finish()
        })

        viewModel.requestFailed.observe(this, Observer { message ->
            if (message.isNotEmpty()) {
                showToastMessage(message)
            }
        })
    }

    companion object {
        fun getIntent(context: Context, roomCode: String): Intent {
            return Intent(context, EnterRoomActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_ROOM_CODE to roomCode))
            }
        }
    }
}