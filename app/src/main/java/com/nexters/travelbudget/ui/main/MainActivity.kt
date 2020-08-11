package com.nexters.travelbudget.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityMainBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.TripDetailActivity
import com.nexters.travelbudget.ui.select_room_type.SelectRoomTypeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setTabLayout()

        viewModel.startCreateRoom.observe(this, Observer {
            startActivity(SelectRoomTypeActivity.getIntent(this))
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

    companion object {
        private const val TAB_COUNT = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
