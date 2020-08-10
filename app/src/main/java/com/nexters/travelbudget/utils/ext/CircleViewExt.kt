package com.nexters.travelbudget.utils.ext

import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.nexters.travelbudget.utils.widget.CircleView

@BindingAdapter("bind:setCircleColor")
fun CircleView.setCircleColor(@ColorInt color: Int) {
    this.setCircleColor(color)
}