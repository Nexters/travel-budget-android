package com.nexters.travelbudget.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nexters.travelbudget.ui.detail.TripDetailPersonalFragment
import com.nexters.travelbudget.ui.detail.TripDetailSharedFragment

/**
 * @author kiyeon_kim
 * @see new ViewPager
 */
class DetailVPAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val DEFAULT_TAB_SIZE = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TripDetailSharedFragment.newInstance()
            else  -> TripDetailPersonalFragment.newInstance()
        }
    }

    override fun getCount(): Int = DEFAULT_TAB_SIZE


}
