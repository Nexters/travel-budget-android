package com.nexters.travelbudget.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.updatePadding
import com.nexters.travelbudget.R

class TripEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {
    init {
        setPadding(
            0,
            0,
            0,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                12f,
                resources.displayMetrics
            ).toInt()
        )
        setBackgroundResource(R.drawable.bg_edit_text_underground_default)
        setHintTextColor(resources.getColor(R.color.colorTextHint, null))
        setTextColor(resources.getColor(R.color.colorTextBlack, null))
        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            with(v as EditText) {
                if (hasFocus) {
                    setBackgroundResource(R.drawable.bg_edit_text_underground_focused)
                } else {
                    setBackgroundResource(R.drawable.bg_edit_text_underground_default)
                }
            }
        }
    }
}