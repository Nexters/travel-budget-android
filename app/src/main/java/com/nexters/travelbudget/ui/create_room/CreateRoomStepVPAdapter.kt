package com.nexters.travelbudget.ui.create_room

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 방 생성화면에서 사용하는 ViewPager Adapter
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class CreateRoomStepVPAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<Fragment>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}