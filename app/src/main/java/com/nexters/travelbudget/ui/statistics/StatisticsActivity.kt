package com.nexters.travelbudget.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.detail.TripDetailAloneViewModel
import com.nexters.travelbudget.ui.statistics.adapter.StatisticsPageAdapter
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val sharedBudgetId = intent.getLongExtra(Constant.EXTRA_SHARED_BUDGET_ID, -1)
        val personalBudgetId = intent.getLongExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, -1)

        val list = ArrayList<Long>().apply {
            add(sharedBudgetId)
            add(personalBudgetId)
        }.toLongArray()

        val roomType = intent.getSerializableExtra(Constant.EXTRA_ROOM_TYPE) as TravelRoomType
        val tl = findViewById<TabLayout>(R.id.tl_spend_statistics)
        val vp = findViewById<ViewPager>(R.id.vp_statistics).apply {
            adapter = StatisticsPageAdapter(supportFragmentManager, roomType, list)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl))
        }

        tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp.currentItem = tab.position
            }
        })

        val divider = findViewById<View>(R.id.view_divider)

        if (roomType == TravelRoomType.SHARED) {
            divider.visibility = View.VISIBLE
            tl.visibility = View.VISIBLE
        } else {
            divider.visibility = View.INVISIBLE
            tl.visibility = View.GONE
        }
    }
}