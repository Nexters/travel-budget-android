package com.nexters.travelbudget.model.enums

import androidx.annotation.DrawableRes
import com.nexters.travelbudget.R

enum class SpendCategoryEnum(
    val title: String,
    @DrawableRes val defaultRes: Int,
    @DrawableRes val selectedRes: Int
) {
    MOVE(
        "교통",
        R.drawable.ic_category_transportation_disable_32dp,
        R.drawable.ic_category_transportation_32dp
    ),
    STAY(
        "숙박",
        R.drawable.ic_category_hotel_disable_32dp,
        R.drawable.ic_category_hotel_32dp
    ),
    MEAL(
        "식비",
        R.drawable.ic_category_meal_disable_32dp,
        R.drawable.ic_category_meal_32dp
    ),
    SNACK(
        "간식",
        R.drawable.ic_category_snack_disable_32dp,
        R.drawable.ic_category_snack_32dp
    ),
    SHOPPING(
        "쇼핑",
        R.drawable.ic_category_shopping_disable_32dp,
        R.drawable.ic_category_shopping_32dp
    ),
    CULTURE(
        "문화",
        R.drawable.ic_category_culture_disable_32dp,
        R.drawable.ic_category_culture_32dp
    ),
    OTHERS(
        "기타",
        R.drawable.ic_category_etc_disable_32dp,
        R.drawable.ic_category_etc_32dp
    );
}