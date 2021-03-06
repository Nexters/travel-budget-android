package com.nexters.travelbudget.ui.manage_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import com.nexters.travelbudget.databinding.ItemInviteMemberBinding
import com.nexters.travelbudget.databinding.ItemManageMemberBinding
import com.nexters.travelbudget.model.enums.TripMemberType

/**
 * 여행 친구 목록 Recyclerview adapter
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageMemberRVAdapter(
    private val onClickExportMember: (TripMemberResponse.Member) -> Unit,
    private val onClickInviteMember: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<TripMemberResponse.Member> = mutableListOf()

    private var isOwner: Boolean = false

    fun setItems(items: List<TripMemberResponse.Member>, myAuthorityType: String) {
        this.items.clear()
        this.items.addAll(items)
        isOwner = myAuthorityType == TripMemberType.OWNER.name
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size.plus(1)

    override fun getItemViewType(position: Int): Int {
        return if (items.size == position) {
            INVITE_MEMBER_TYPE
        } else {
            MANAGE_MEMBER_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MANAGE_MEMBER_TYPE) {
            return ManageMemberVH(
                ItemManageMemberBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    tvExportMemger.setOnClickListener {
                        item?.let(onClickExportMember)
                    }
                }
            )
        } else {
            return InviteMemberVH(
                ItemInviteMemberBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    root.setOnClickListener {
                        onClickInviteMember.invoke()
                    }
                }
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ManageMemberVH -> holder.bind(items[position])
        }
    }

    inner class ManageMemberVH(private val binding: ItemManageMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TripMemberResponse.Member) {
            binding.item = item
            binding.isOwner = isOwner
        }
    }

    inner class InviteMemberVH(private val binding: ItemInviteMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val MANAGE_MEMBER_TYPE = 0
        private const val INVITE_MEMBER_TYPE = 1
    }
}