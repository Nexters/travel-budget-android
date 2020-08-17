package com.nexters.travelbudget.ui.create_room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityCreateRoomBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.showToastMessage
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

    private val createRoomStepVPAdapter by lazy {
        CreateRoomStepVPAdapter(supportFragmentManager, childFragments)
    }

    private val childFragments: List<Fragment> by lazy {
        if (viewModel.isSharedRoom) {
            listOf(
                RoomTitleInputFragment.newInstance(),
                RoomPeriodInputFragment.newInstance(),
                RoomSharedBudgetInputFragment.newInstance()
            )
        } else {
            listOf(
                RoomTitleInputFragment.newInstance(),
                RoomPeriodInputFragment.newInstance()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCreateRoomVP()

        viewModel.nextScreen.observe(this, Observer {
            with(binding.nonSwipeViewPager) {
                currentItem = currentItem.plus(1)
            }
        })

        viewModel.successCreateRoom.observe(this, Observer {
            showToastMessage(getString(R.string.create_travel_room_success))
            setResult(Constant.RESULT_OK)
            finish()
        })

        viewModel.errorCreateRoom.observe(this, Observer {
            showToastMessage(getString(R.string.create_travel_room_fail))
            setResult(Constant.RESULT_ERROR)
            finish()
        })

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })

        viewModel.finishScreen.observe(this, Observer {
            setResult(Constant.RESULT_CANCEL)
            finish()
        })
    }

    private fun setCreateRoomVP() {
        binding.nonSwipeViewPager.run {
            adapter = createRoomStepVPAdapter
            offscreenPageLimit = childFragments.size - 1
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    viewModel.isLastScreen.value = childFragments.size - 1 == position
                }

                override fun onPageSelected(position: Int) = Unit

            })
        }
    }

    override fun onBackPressed() {
        if (binding.nonSwipeViewPager.currentItem == 0) {
            setResult(Constant.RESULT_CANCEL)
            super.onBackPressed()
        } else {
            with(binding.nonSwipeViewPager) {
                currentItem = currentItem.minus(1)
            }
        }
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