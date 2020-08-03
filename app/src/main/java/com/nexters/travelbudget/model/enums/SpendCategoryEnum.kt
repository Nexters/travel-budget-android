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
        R.drawable.ic_move_default_32dp,
        R.drawable.ic_move_selected_32dp
    ),
    STAY(
        "숙박",
        R.drawable.ic_stay_default_32dp,
        R.drawable.ic_stay_selected_32dp
    ),
    MEAL(
        "식비",
        R.drawable.ic_meal_default_32dp,
        R.drawable.ic_meal_selected_32dp
    ),
    SNACK(
        "간식",
        R.drawable.ic_snack_default_32dp,
        R.drawable.ic_snack_selected_32dp
    ),
    SHOPPING(
        "쇼핑",
        R.drawable.ic_shopping_default_32dp,
        R.drawable.ic_shopping_selected_32dp
    ),
    CULTURE(
        "문화",
        R.drawable.ic_culture_default_32dp,
        R.drawable.ic_culture_selected_32dp
    ),
    OTHERS(
        "기타",
        R.drawable.ic_others_default_32dp,
        R.drawable.ic_others_selected_32dp
    );
}