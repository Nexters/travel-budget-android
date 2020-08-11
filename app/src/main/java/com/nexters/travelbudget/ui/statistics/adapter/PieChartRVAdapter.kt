package com.nexters.travelbudget.ui.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nexters.travelbudget.databinding.ItemPieDataListBinding
import com.nexters.travelbudget.ui.base.adapter.BaseItemVH
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.widget.piechart.PieData
import java.text.DecimalFormat
import java.util.*

class PieChartRVAdapter : BaseRVAdapter<PieData>() {
    override fun onBindView(binding: ViewDataBinding, viewHolder: BaseItemVH, item: PieData) {
        with(binding as ItemPieDataListBinding) {
            tvCategoryTitle.text = item.tag
            tvSpendPercent.text = String.format(Locale.KOREA, "%d%%", (item.value / getTotalValue() * 100).toInt())
            tvSpendAmount.text = String.format(Locale.KOREA, "%s원", item.value.toMoneyString())
            viewColor.setBackgroundColor(item.color)
        }
    }

    override fun onCreateBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemPieDataListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
    }

    private fun getTotalValue(): Float {
        var value = 0f
        for (item in items) {
            value += item.value
        }

        return value
    }
}
