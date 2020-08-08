package com.nexters.travelbudget.model

import androidx.annotation.DrawableRes

data class SpendCategoryModel(
    @DrawableRes val icon: Int,
    val title: String,
    val isSelect: Boolean
)