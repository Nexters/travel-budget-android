package com.nexters.travelbudget.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 전역적으로 뷰홀더를 생성 및 사용하기 위한 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */

class BaseItemVH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)