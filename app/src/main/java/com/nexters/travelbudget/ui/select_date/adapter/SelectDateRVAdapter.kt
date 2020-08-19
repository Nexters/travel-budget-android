package com.nexters.travelbudget.ui.select_date.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ItemDateSelectBinding
import com.nexters.travelbudget.model.BottomSheetDateItemModel
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.ext.convertToServerDate

class SelectDateRVAdapter(
    listener: (String) -> Unit
) : BaseRVAdapter<String>(listener) {
    private var currentDate: String = ""

    override fun onBindView(binding: ViewDataBinding, viewHolder: BaseItemVH, item: String) {
        with (binding as ItemDateSelectBinding) {
            DLog.d("curr: $currentDate")
            model = BottomSheetDateItemModel(item, item == currentDate)
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemDateSelectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    fun setBoldItem(date: String) {
        for (i in items.indices) {
            if (items[i] == date) {
                currentDate = date
                notifyItemChanged(i)
                break
            }
        }
    }
}
