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
        (binding as ItemSpendCategoryBinding).model = item
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