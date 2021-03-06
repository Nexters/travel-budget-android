package com.nexters.travelbudget.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nexters.travelbudget.ui.main.record.RecordedTravelFragment
import com.nexters.travelbudget.ui.main.record.RecordingTravelFragment

/**
 * 메인 viewpager 탭 adapter
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class MainVPAdapter(
    fragmentManager: FragmentManager,
    private val tabCount: Int
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RecordingTravelFragment.newInstance()
            else -> RecordedTravelFragment.newInstance()
        }
    }

    override fun getCount(): Int = tabCount
}