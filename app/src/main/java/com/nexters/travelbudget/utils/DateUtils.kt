package com.nexters.travelbudget.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 관련 유틸 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */

@SuppressLint("SimpleDateFormat")
private val sdf = SimpleDateFormat("yyyy-MM-dd")

fun getNowDate(): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

fun Date.isBeforeOrEqualsDate(targetDate: String): Boolean {
    val compareTo = this.compareTo(sdf.parse(targetDate))
    return when (compareTo) {
        -1, 0 -> true
        else -> false
    }
}

fun Date.isAfterOrEqualsDate(targetDate: String): Boolean {
    val compareTo = this.compareTo(sdf.parse(targetDate))
    return when (compareTo) {
        1, 0 -> true
        else -> false
    }
}

fun String.isBetweenDate(startDate: String, endDate: String): Boolean {
    val nowDate = sdf.parse(this)

    return nowDate.isAfterOrEqualsDate(startDate) && nowDate.isBeforeOrEqualsDate(endDate)
}


fun getViewDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
fun getServerDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
fun convertDateToMills(date: String, time: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val d = sdf.parse("$date $time:00") ?: Date()

    return d.time / 1000
}