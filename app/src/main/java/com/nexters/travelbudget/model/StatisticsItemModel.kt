package com.nexters.travelbudget.model

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class StatisticsItemModel (
    val tag: String,
    val moneyAmount: String,
    val percent: Int,
    @ColorInt val color: Int,
    @DrawableRes val icon: Int
)