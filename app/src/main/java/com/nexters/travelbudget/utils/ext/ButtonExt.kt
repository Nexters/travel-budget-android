package com.nexters.travelbudget.utils.ext

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.nexters.travelbudget.R

@BindingAdapter("isActivated")
fun Button.isActivated(activated: Boolean) {
    if (activated) {
        setTextColor(resources.getColor(R.color.colorWhite, null))
        setBackgroundResource(R.drawable.bg_button_round_blue)
    } else {
        setTextColor(resources.getColor(R.color.colorTextHint, null))
        setBackgroundResource(R.drawable.bg_button_round_gray)
    }
}