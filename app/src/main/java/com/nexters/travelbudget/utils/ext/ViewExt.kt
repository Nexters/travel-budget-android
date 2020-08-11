package com.nexters.travelbudget.utils.ext

import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 * View 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.22
 */

@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {
    isVisible = value
}

@BindingAdapter("android:invisibleIf")
fun View.setInvisibleIf(value: Boolean) {
    isInvisible = value
}

@BindingAdapter("android:goneIf")
fun View.setGoneIf(value: Boolean) {
    isGone = value
}

@BindingAdapter("marginTop")
fun View.bindMarginTop(dimen: Float) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        topMargin = dimen.toInt()
    }
}

@BindingAdapter("marginBottom")
fun View.bindMarginBottom(dimen: Float) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        bottomMargin = dimen.toInt()
    }
}

@BindingAdapter("marginEnd")
fun View.bindMarginEnd(dimen: Float) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        rightMargin = dimen.toInt()
    }
}

@BindingAdapter("marginStart")
fun View.bindMarginStart(dimen: Float) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        leftMargin = dimen.toInt()
    }
}

@BindingAdapter("android:onCheckedChanged")
fun RadioGroup.setOnCheckedChangeListener(listener: RadioGroup.OnCheckedChangeListener) {
    setOnCheckedChangeListener(listener)
}