package com.nexters.travelbudget.ui.statistics.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ItemPieDataListBinding
import com.nexters.travelbudget.model.StatisticsItemModel
import com.nexters.travelbudget.model.enums.SpendCategoryEnum
import com.nexters.travelbudget.utils.ext.toMoneyString
import com.nexters.travelbudget.utils.widget.piechart.PieData
import kotlin.collections.ArrayList

class PieChartRVAdapter : RecyclerView.Adapter<PieChartRVAdapter.PieChartViewHolder>() {
    private val items = ArrayList<PieData>()
    private val colorList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartViewHolder {
        colorList.apply {
            add(parent.context.resources.getColor(R.color.fill_blue, null))
            add(parent.context.resources.getColor(R.color.fill_blue_2, null))
            add(parent.context.resources.getColor(R.color.fill_blue_3, null))
            add(parent.context.resources.getColor(R.color.fill_blue_4, null))
            add(parent.context.resources.getColor(R.color.fill_blue_5, null))
            add(parent.context.resources.getColor(R.color.fill_blue_6, null))
            add(parent.context.resources.getColor(R.color.fill_blue_7, null))
            add(parent.context.resources.getColor(R.color.fill_blue_8, null))
        }

        return PieChartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pie_data_list, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PieChartViewHolder, position: Int) {
        with(ItemPieDataListBinding.bind(holder.itemView)) {
            val pieData = items[position]
            val perc = (100f * pieData.value / getTotalValue()).toInt()
            var icon = 0
            for (category in SpendCategoryEnum.values()) {
                if (category.title == pieData.tag) {
                    icon = category.selectedRes
                    break
                }
            }

            model = StatisticsItemModel(
                pieData.tag,
                pieData.value.toMoneyString(),
                perc,
                colorList[position],
                icon
            )

            viewDivider.visibility = if (position != itemCount - 1) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    fun setItemList(items: List<PieData>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun getTotalValue(): Float {
        var value = 0f
        for (item in items) {
            value += item.value
        }

        return value
    }

    class PieChartViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
