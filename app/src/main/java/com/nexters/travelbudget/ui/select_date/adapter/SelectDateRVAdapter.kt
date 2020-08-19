package com.nexters.travelbudget.ui.select_date.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ItemDateSelectBinding
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter

class SelectDateRVAdapter(
    listener: (String) -> Unit
) : BaseRVAdapter<String>(listener) {
    override fun onBindView(binding: ViewDataBinding, viewHolder: BaseItemVH, item: String) {
        with (binding as ItemDateSelectBinding) {
            tvDate.text = item
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemDateSelectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

}
