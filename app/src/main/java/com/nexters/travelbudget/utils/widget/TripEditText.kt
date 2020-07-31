package com.nexters.travelbudget.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.nexters.travelbudget.R

class TripEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {
    init {
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