package com.nexters.travelbudget.utils.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

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
