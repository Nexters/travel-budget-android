package com.nexters.travelbudget.utils.ext

import java.text.DecimalFormat

/**
 * String 관련 Extension methods
 *
 * @author AKM
 * @since v1.0.0 / 2020.07.27
 */

fun Int.toMoneyString(): String {
    return DecimalFormat("###,###,###").format(this)
}