package com.nexters.travelbudget.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nexters.travelbudget.databinding.FragmentDetailSharedBinding
import com.nexters.travelbudget.databinding.ItemSharedDetailMoneyListBinding
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.utils.DetailSharedData
import java.text.DecimalFormat
import java.util.*

class SharedDetailRVAdapter : BaseRVAdapter<DetailSharedData>() {
    override fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: DetailSharedData
    ) {
        with(binding as ItemSharedDetailMoneyListBinding) {
            tvDetailSharedItemTitle.text = item.title
            tvDetailSharedItemMoney.text =
                String.format(Locale.KOREA, "-%s원", item.money) // toMoneyString
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemSharedDetailMoneyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    private fun getTotalMoney(): Float {
        var value = 0f
        for (item in items) {
            value += item.money
        }

        return value
    }
}