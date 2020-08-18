package com.nexters.travelbudget.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * 날짜 관련 유틸 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */
fun getViewDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
fun getServerDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
fun convertDateToMills(date: String, time: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val d = sdf.parse("$date $time:00") ?: Date()

    return d.time / 1000
}