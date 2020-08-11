package com.nexters.travelbudget.utils.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat
import com.nexters.travelbudget.R

/**
 * TextView 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.09
 */

@BindingAdapter("moneyForm")
fun TextView.bindMoneyForm(money: Int) {
    text = if (money == -1) {
        "- 원"
    } else {
        DecimalFormat("###,###,###원").format(money.toLong())
    }
}

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
