package com.nexters.travelbudget.utils.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.ui.base.adapter.BaseRVAdapter

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