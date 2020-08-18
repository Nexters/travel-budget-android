package com.nexters.travelbudget.model

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class DetailItemModel (
    val title: String,
    val money: String,
    @DrawableRes val icon: Int
)