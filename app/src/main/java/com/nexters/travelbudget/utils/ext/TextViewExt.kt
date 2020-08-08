package com.nexters.travelbudget.utils.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nexters.travelbudget.R

@BindingAdapter("isSelected")
fun TextView.isSelected(b: Boolean) {
    if (b) {
        setTextColor(resources.getColor(R.color.fill_blue, null))
        setBackgroundResource(R.drawable.bg_blue_radius_20dp)
    } else {
        setTextColor(resources.getColor(R.color.fill_grey_1, null))
        background = null
    }
}