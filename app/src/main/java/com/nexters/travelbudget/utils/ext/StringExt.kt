package com.nexters.travelbudget.utils.ext

import com.nexters.travelbudget.utils.getServerDateFormat
import com.nexters.travelbudget.utils.getViewDateFormat

/**
 * String 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */

const val DEFAULT_DATE = "2020.08.20"

fun String.convertToServerDate(): String {
    val formatIn = getViewDateFormat()
    val formatOut = getServerDateFormat()
    val date = formatIn.parse(this)
    return date?.let {
        formatOut.format(it)
    } ?: ""
}

fun String.convertToViewDate(): String {
    val formatIn = getServerDateFormat()
    val formatOut = getViewDateFormat()
    val date = formatIn.parse(this)
    return date?.let {
        formatOut.format(it)
    } ?: ""
}