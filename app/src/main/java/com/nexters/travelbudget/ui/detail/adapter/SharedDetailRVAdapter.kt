package com.nexters.travelbudget.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.nexters.travelbudget.data.remote.model.response.TripPaymentResponse
import com.nexters.travelbudget.data.remote.model.response.TripRecordResponse
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.databinding.ItemSharedDetailMoneyListBinding
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseListAdapter
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.ui.base.adapter.OnRecyclerViewItemClick
import com.nexters.travelbudget.utils.DetailSharedData
import java.text.DecimalFormat
import java.util.*

class SharedDetailRVAdapter(onItemClick: OnRecyclerViewItemClick<TripPaymentResponse>) :
    BaseListAdapter<TripPaymentResponse>(object : DiffUtil.ItemCallback<TripPaymentResponse>() {
        override fun areItemsTheSame(
            oldItem: TripPaymentResponse,
            newItem: TripPaymentResponse
        ): Boolean = oldItem.budgetId == newItem.budgetId

        override fun areContentsTheSame(
            oldItem: TripPaymentResponse,
            newItem: TripPaymentResponse
        ): Boolean = oldItem == newItem

    }, onItemClick) {

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemSharedDetailMoneyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: TripPaymentResponse
    ) {
        (binding as ItemSharedDetailMoneyListBinding)?.run {
            tvDetailSharedItemTitle.text = item.title
            tvDetailSharedItemMoney.text =
                String.format(Locale.KOREA, "-%s원", item.price) // toMoneyString
        }
    }

//    private fun getTotalMoney(): Float {
//        var value = 0f
//        for (item in items) {
//            value += item.money
//        }
//
//        return value
//    }

}