package com.nexters.travelbudget.ui.base.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * Simple ListAdapter
 *
 * 데이터 변화가 많은 뷰에서 해당 어댑터 활용
 *
 * 아이템 클릭 처리가 필요 없는 경우 생성자에 무시
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */

abstract class BaseListAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClick: OnRecyclerViewItemClick<T>? = null
) : ListAdapter<T, BaseItemVH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemVH {
        return BaseItemVH(onCreateBinding(parent, viewType)).also {
            if (onItemClick != null) { // onItemClick 변수가 null 이 아닌 경우 자동으로 아이템 클릭 리스너 설정
                it.binding.root.setOnClickListener { _ ->
                    val currentItem = currentList.getOrNull(it.bindingAdapterPosition)
                        ?: return@setOnClickListener
                    onItemClick.invoke((currentItem))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseItemVH, position: Int) =
        onBindView(holder.binding, holder, getItem(position))

    protected abstract fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: T
    )

    protected abstract fun onCreateBinding(
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding
}