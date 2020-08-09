package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.DetailVPAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailActivity :
    BaseActivity<ActivityDetailBinding, TripDetailViewModel>(R.layout.activity_detail) {
    override val viewModel: TripDetailViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isPersonal = true
        binding.isPersonal = isPersonal
        setTabLayout()

    }

    private fun setTabLayout() {
        binding.tlDetail.run {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpDetailPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }

            })
        }

        binding.vpDetailPager.run {
            adapter = DetailVPAdapter(supportFragmentManager, TAB_COUNT)
            offscreenPageLimit = TAB_COUNT - 1
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlDetail))
        }
    }

    companion object {
        private const val TAB_COUNT = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailActivity::class.java)
        }
    }


}