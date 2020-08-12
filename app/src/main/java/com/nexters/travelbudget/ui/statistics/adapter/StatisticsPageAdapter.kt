package com.nexters.travelbudget.ui.statistics.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nexters.travelbudget.ui.statistics.StatisticsFragment

class StatisticsPageAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StatisticsFragment.newInstance()
            else -> StatisticsFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2
}