package com.nexters.travelbudget.ui.edit_trip_profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityEditTripProfileBinding
import com.nexters.travelbudget.model.enums.ActivityResultType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditTripProfileActivity :
    BaseActivity<ActivityEditTripProfileBinding, EditTripProfileViewModel>(R.layout.activity_edit_trip_profile) {

    override val viewModel: EditTripProfileViewModel by viewModel {
        parametersOf(
            intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1),
            intent.getLongExtra(Constant.EXTRA_MEMBER_ID, -1),
            intent.getStringExtra(Constant.EXTRA_ROOM_TYPE)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTripProfileInfo()

        viewModel.successModificationTripProfile.observe(this, Observer {
            // 여행 정보 수정 (멤버 or 방장)
            val bundle =
                bundleOf(Constant.EXTRA_ACTIVITY_RESULT_TYPE to ActivityResultType.SCREEN_REFRESH.name)
            setResult(Constant.RESULT_OK, Intent().putExtras(bundle))
            finish()
        })

        viewModel.successExitTripRoom.observe(this, Observer {
            // 방 나가기 (멤버)
            val bundle =
                bundleOf(Constant.EXTRA_ACTIVITY_RESULT_TYPE to ActivityResultType.SCREEN_FINISH.name)
            setResult(Constant.RESULT_OK, Intent().putExtras(bundle))
            finish()
        })

        viewModel.successDeleteTripRoom.observe(this, Observer {
            // 여행 삭제 (방장)
            val bundle =
                bundleOf(Constant.EXTRA_ACTIVITY_RESULT_TYPE to ActivityResultType.SCREEN_FINISH.name)
            setResult(Constant.RESULT_OK, Intent().putExtras(bundle))
            finish()
        })

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    companion object {
        fun getIntent(
            context: Context,
            planId: Long,
            memberId: Long,
            tripRoomType: String
        ): Intent {
            return Intent(context, EditTripProfileActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        Constant.EXTRA_PLAN_ID to planId,
                        Constant.EXTRA_MEMBER_ID to memberId,
                        Constant.EXTRA_ROOM_TYPE to tripRoomType
                    )
                )
            }
        }
    }

}