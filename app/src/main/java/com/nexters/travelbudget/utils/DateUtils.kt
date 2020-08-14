package com.nexters.travelbudget.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 관련 유틸 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.13
 */
fun getViewDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
fun getServerDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())