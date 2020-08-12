package com.nexters.travelbudget.ui.create_room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRoomTitleInputBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 방 제목 입력 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class RoomTitleInputFragment :
    BaseFragment<FragmentRoomTitleInputBinding, CreateRoomViewModel>(R.layout.fragment_room_title_input) {

    override val viewModel: CreateRoomViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): RoomTitleInputFragment = RoomTitleInputFragment()
    }
}