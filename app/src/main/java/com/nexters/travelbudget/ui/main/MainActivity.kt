package com.nexters.travelbudget.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityMainBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.enter_room.EnterRoomActivity
import com.nexters.travelbudget.ui.main.record.RecordingTravelFragment
import com.nexters.travelbudget.ui.mypage.MyPageActivity
import com.nexters.travelbudget.ui.select_room_type.SelectRoomTypeActivity
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setTabLayout()
        viewModel.getUserInfo()
        checkDeepLink()

        viewModel.startCreateRoom.observe(this, Observer {
            goToSelectRoomTypeActivity()
        })

        viewModel.startMyPage.observe(this, Observer {
            startActivityForResult(
                MyPageActivity.getIntent(this),
                Constant.REQUEST_CODE_EDIT_USER_NAME
            )
        })

        viewModel.startEnterRoom.observe(this, Observer {
            goToEnterRoomCodeActivity()
        })
    }

    private fun setTabLayout() {
        binding.tlMainTab.run {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpMainPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                override fun onTabReselected(tab: TabLayout.Tab) = Unit

            })
        }

        binding.vpMainPager.run {
            adapter = MainVPAdapter(supportFragmentManager, TAB_COUNT)
            offscreenPageLimit = TAB_COUNT - 1
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlMainTab))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constant.REQUEST_CODE_EDIT_USER_NAME -> if (resultCode == Constant.RESULT_OK) {
                viewModel.getUserInfo()
            }
            Constant.REQUEST_CODE_TRIP_DETAIL, Constant.REQUEST_CODE_CREATE_ROOM, Constant.REQUEST_CODE_ENTER_ROOM ->
                if (resultCode == Constant.RESULT_OK) {
                    for (fragment in supportFragmentManager.fragments) {
                        fragment.onActivityResult(requestCode, resultCode, data)
                    }
                }
        }
    }

    private fun checkDeepLink() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                if (pendingDynamicLinkData == null) {
                    return@addOnSuccessListener
                }

                val deepLink = pendingDynamicLinkData.link
                when (deepLink?.lastPathSegment) {
                    Constant.SEGMENT_ROOM -> {
                        val code = deepLink.getQueryParameter(Constant.KEY_ROOM_CODE)
                        goToEnterRoomCodeActivity(code ?: "")
                    }
                }
            }
    }

    private fun goToEnterRoomCodeActivity(roomCode: String = "") {
        startActivityForResult(
            EnterRoomActivity.getIntent(this, roomCode),
            Constant.REQUEST_CODE_ENTER_ROOM
        )
    }

    fun goToSelectRoomTypeActivity() {
        startActivityForResult(
            SelectRoomTypeActivity.getIntent(
                this,
                viewModel.userName.value ?: getString(R.string.default_user_name)
            ), Constant.REQUEST_CODE_CREATE_ROOM
        )
    }

    companion object {
        private const val TAB_COUNT = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }
}
