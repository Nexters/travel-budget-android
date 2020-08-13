package com.nexters.travelbudget.ui.select_room_type

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivitySelectRoomTypeBinding
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.ui.create_room.CreateRoomActivity
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
        binding.activity = this

        viewModel.finishScreen.observe(this, Observer {
            finish()
        })

        viewModel.goToNextScreen.observe(this, Observer {
            if (viewModel.travelRoomType.value != null) {
                val intent = CreateRoomActivity.getIntent(this, viewModel.userName.value ?: getString(R.string.default_user_name), viewModel.travelRoomType.value!!).apply {
                    addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.request_fail), Toast.LENGTH_SHORT).show()
            }
            finish()
        })
    }

    fun onCheckedChanged(@IdRes checkedId: Int) {
        viewModel.allowsGotoNextScreen.value = true
        when (checkedId) {
            R.id.rb_personal_travel_room -> {
                viewModel.travelRoomType.value = TravelRoomType.PERSONAL.name
                /** 이미지 변경 */
                binding.ivSelectRoomTypeScreenImage.setImageResource(R.drawable.bg_personal_room_type)
                /** 라디오 속성 변경 */
                binding.rbSharedTravelRoom.run {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    typeface = Typeface.DEFAULT
                }
                binding.rbPersonalTravelRoom.run {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.btn_enable_icon, 0)
                    typeface = Typeface.DEFAULT_BOLD
                }
            }
            R.id.rb_shared_travel_room -> {
                viewModel.travelRoomType.value = TravelRoomType.SHARED.name
                /** 이미지 변경 */
                binding.ivSelectRoomTypeScreenImage.setImageResource(R.drawable.bg_shared_room_type)
                /** 라디오 속성 변경 */
                binding.rbPersonalTravelRoom.run {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    typeface = Typeface.DEFAULT
                }
                binding.rbSharedTravelRoom.run {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.btn_enable_icon, 0)
                    typeface = Typeface.DEFAULT_BOLD
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context, userName: String): Intent {
            return Intent(context, SelectRoomTypeActivity::class.java).apply {
                putExtras(bundleOf(Constant.EXTRA_USER_NAME to userName))
            }
        }
    }
}