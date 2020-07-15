package com.nexters.travelbudget.ui.base.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple RecyclerView Adapter
 *
 * Multi View Type 이 아닌 경우에 사용
 *
 * 아이템 클릭 처리가 필요 없는 경우 생성자에 무시
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */

typealias OnRecyclerViewItemClick<T> = ((T) -> Unit)

abstract class BaseRVAdapter<T>(
    private val onItemClick: OnRecyclerViewItemClick<T>? = null
) : RecyclerView.Adapter<BaseItemVH>() {

    protected val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemVH {
        return BaseItemVH(onCreateBinding(parent, viewType)).also {
            if (onItemClick != null) { // onItemClick 변수가 null 이 아닌 경우 자동으로 아이템 클릭 리스너 설정
                it.binding.root.setOnClickListener { _ ->
                    val currentItem =
                        items.getOrNull(it.bindingAdapterPosition) ?: return@setOnClickListener
                    onItemClick.invoke((currentItem))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseItemVH, position: Int) =
        onBindView(holder.binding, holder, items[position])

    protected abstract fun onBindView(
        binding: ViewDataBinding,
        viewHolder: BaseItemVH,
        item: T
    )

    protected abstract fun onCreateBinding(
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding


    override fun getItemCount(): Int = items.size

    /** 아이템 초기화 */
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    /** 아이템 전체 대체  */
    fun replaceAll(replaceItems: List<T>?) {
        this.items.run {
            clear()
            replaceItems?.let {
                addAll(it)
            }
        }
        notifyDataSetChanged()
    }

    /** 아이템 추가 */
    fun add(item: T?) {
        this.items.run {
            item?.let {
                add(it)
                notifyItemInserted(items.size - 1)
            }
        }
    }

    /** 첫번째 위치에 아이템 추가 */
    fun addToBeginning(item: T?) {
        this.items.run {
            item?.let {
                add(0, it)
                notifyItemInserted(0)
            }
        }
    }

    /** 리스트 아이템 추가 */
    fun addAll(addItems: List<T>?) {
        this.items.run {
            addItems?.let {
                addAll(it)
                notifyItemRangeChanged(items.size - it.size, it.size)
            }
        }
    }

    /** 원하는 아이템 제거 */
    fun remove(item: T?) {
        val position = this.items.indexOf(item)
        if (position > -1) {
            this.items.remove(item)
            notifyItemRemoved(position)
        }
    }

    /** [fromIdx], [toIdx] 값에 따른 아이템 제거 */
    fun removeRange(fromIdx: Int, toIdx: Int) {
        this.items.subList(fromIdx, toIdx).clear()
        notifyItemRangeChanged(fromIdx, toIdx - fromIdx)
    }
}