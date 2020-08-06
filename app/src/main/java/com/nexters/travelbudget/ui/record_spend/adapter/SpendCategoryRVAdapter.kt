package com.nexters.travelbudget.ui.record_spend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ItemSpendCategoryBinding
import com.nexters.travelbudget.model.SpendCategoryModel
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter

class SpendCategoryRVAdapter(
    onItemClick: (SpendCategoryModel) -> Unit
) : BaseRVAdapter<SpendCategoryModel>(onItemClick) {
    override fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: SpendCategoryModel
    ) {
        with(binding as ItemSpendCategoryBinding) {
            ivIcon.setImageResource(item.icon)
            tvName.text = item.title
            tvName.setTextColor(
                if (item.isSelect) {
                    root.resources.getColor(R.color.fill_grey_1, null)
                } else {
                    root.resources.getColor(R.color.colorTextHint, null)
                }
            )
            if (item.isSelect) {
                ivIcon.setBackgroundResource(R.drawable.bg_circle_fill_blue_8)
            } else {
                ivIcon.background = null
            }
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemSpendCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    fun updateItem(item: SpendCategoryModel, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }
}