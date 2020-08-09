package com.nexters.travelbudget.ui.main.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.databinding.ItemRecordingTravelBinding
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseListAdapter
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.ui.base.adapter.OnRecyclerViewItemClick

/**
 * 기록할 여행 RecyclerView Adapter
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.09
 */

class RecordingTravelRVAdapter(onItemClick: OnRecyclerViewItemClick<TripRecordResponse>) :
    BaseListAdapter<TripRecordResponse>(object : DiffUtil.ItemCallback<TripRecordResponse>() {
        override fun areItemsTheSame(
            oldItem: TripRecordResponse,
            newItem: TripRecordResponse
        ): Boolean = oldItem.planId == newItem.planId

        override fun areContentsTheSame(
            oldItem: TripRecordResponse,
            newItem: TripRecordResponse
        ): Boolean = oldItem == newItem

    }, onItemClick) {

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemRecordingTravelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: TripRecordResponse
    ) {
        (binding as? ItemRecordingTravelBinding)?.run {
            this.item = item
            roomType = if (item.isPublic == "Y") {
                "공용"
            } else {
                "개인"
            }
            val resId = if (item.isPublic == "Y") {
                if (item.isDoing == "Y") {
                    R.drawable.ic_shared_blue
                } else {
                    R.drawable.ic_shared_grey
                }
            } else {
                if (item.isDoing == "Y") {
                    R.drawable.ic_personal_blue
                } else {
                    R.drawable.ic_personal_grey
                }
            }
            ivRoomTypeIcon.setImageResource(resId)
        }
    }
}