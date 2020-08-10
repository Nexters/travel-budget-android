package com.nexters.travelbudget.utils.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.model.SpendCategoryModel
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import com.nexters.travelbudget.ui.statistics.adapter.PieChartRVAdapter
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.widget.piechart.PieData

/**
 * RecyclerView 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.18
 */

// RecyclerView Adapter 사용 시
@Suppress("UNCHECKED_CAST")
@BindingAdapter("addAll")
fun RecyclerView.addAllItems(items: List<Any>?) {
    (adapter as? BaseRVAdapter<Any>)?.addAll(items)
}

// RecyclerView Adapter 사용 시
@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAll")
fun RecyclerView.replaceAllItems(items: List<Any>?) {
    (adapter as? BaseRVAdapter<Any>)?.replaceAll(items)
}

// ListAdapter 사용 시
@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun RecyclerView.submitList(items: List<Any>?) {
    (adapter as? ListAdapter<Any, *>)?.submitList(items)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("notifySelectSpendCategoryItem")
fun RecyclerView.notifySelectSpendCategoryItem(pair: Pair<Pair<SpendCategoryModel, Int>, Pair<SpendCategoryModel, Int>>?) {
    pair?.let {
        (adapter as? SpendCategoryRVAdapter)?.updateItem(it.first.first, it.first.second)
        (adapter as? SpendCategoryRVAdapter)?.updateItem(it.second.first, it.second.second)
    }
}

@BindingAdapter("setPieDataItemList")
fun RecyclerView.setPieDataItemList(items: List<PieData>?) {
    items?.let {
        (adapter as? PieChartRVAdapter)?.setItemList(it)
    }
}