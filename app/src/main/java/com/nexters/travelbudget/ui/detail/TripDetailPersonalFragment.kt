package com.nexters.travelbudget.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.databinding.FragmentDetailPersonalBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TripDetailPersonalFragment() :
    BaseFragment<FragmentDetailPersonalBinding, TripDetailViewModel>(R.layout.fragment_detail_personal) {

    override val viewModel: TripDetailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailPersonalRV()
    }

    private fun observeViewModel() {
    }


    private fun setupDetailPersonalRV() {
        binding.rvDetailPersonalList.run {
            adapter = SharedDetailRVAdapter { tripPaymentResponse ->
            }
            addItemDecoration(object : CustomItemDecoration() {
                override fun setSpacingForDirection(
                    outRect: Rect,
                    layoutManager: RecyclerView.LayoutManager?,
                    position: Int,
                    itemCount: Int
                ) {
                    outRect.top = resources.getDimensionPixelSize(R.dimen.spacing_size_12dp)
                    outRect.bottom = if (position == itemCount - 1) {
                        resources.getDimensionPixelSize(R.dimen.spacing_size_24dp)
                    } else {
                        0
                    }
                }
            })
        }
    }
    companion object {

        fun newInstance(): TripDetailPersonalFragment {
            return TripDetailPersonalFragment()
        }
    }
}


