package com.nexters.travelbudget.ui.create_room

import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRoomSharedBudgetInputBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 공용 여행 예산 입력 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class RoomSharedBudgetInputFragment :
    BaseFragment<FragmentRoomSharedBudgetInputBinding, CreateRoomViewModel>(R.layout.fragment_room_shared_budget_input) {

    override val viewModel: CreateRoomViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): RoomSharedBudgetInputFragment = RoomSharedBudgetInputFragment()
    }
}