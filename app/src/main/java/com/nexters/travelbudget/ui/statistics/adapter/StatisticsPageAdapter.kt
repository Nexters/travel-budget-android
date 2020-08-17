package com.nexters.travelbudget.ui.statistics.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.statistics.StatisticsFragment

class StatisticsPageAdapter(
    fragmentManager: FragmentManager,
    private val roomType: TravelRoomType,
    private val budgetIdArr: LongArray
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return StatisticsFragment.newInstance(budgetIdArr[position])
    }

    override fun getCount(): Int = if (roomType == TravelRoomType.SHARED) {
        2
    } else {
        1
    }
}