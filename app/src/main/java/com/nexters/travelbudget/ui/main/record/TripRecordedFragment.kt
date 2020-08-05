package com.nexters.travelbudget.ui.main.record

import android.os.Bundle
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentTripRecordedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 다녀온 여행 fragment class
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.05
 */
class TripRecordedFragment :
    BaseFragment<FragmentTripRecordedBinding, MainViewModel>(R.layout.fragment_trip_recorded) {

    override val viewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance(): TripRecordedFragment = TripRecordedFragment()
    }
}