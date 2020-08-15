package com.nexters.travelbudget.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.ui.detail.TripDetailPersonalFragment
import com.nexters.travelbudget.ui.detail.TripDetailSharedFragment

class DetailVPAdapter(
    fragmentManager: FragmentManager,
    private val tabCount: Int,
    private val dates : List<String>,
    private val sharedBudgetData: TripDetailResponse.Data,
    private val personalBudgetData: TripDetailResponse.Data?
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TripDetailSharedFragment.newInstance(sharedBudgetData, dates)
            else -> TripDetailPersonalFragment.newInstance()
        }
    }

    override fun getCount(): Int = tabCount
}