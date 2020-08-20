package com.nexters.travelbudget.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.ui.detail.adapter.SharedDetailRVAdapter
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TripDetailSharedFragment :
    BaseFragment<FragmentDetailSharedBinding, TripDetailViewModel>(R.layout.fragment_detail_shared) {

    override val viewModel: TripDetailViewModel by sharedViewModel()

    private var day = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupDetailSharedRV()

    }

    private fun observeViewModel() {
    }

    private fun setupDetailSharedRV() {
        binding.rvDetailSharedList.run {
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
        fun newInstance(): TripDetailSharedFragment {
            return TripDetailSharedFragment()
        }
    }

}