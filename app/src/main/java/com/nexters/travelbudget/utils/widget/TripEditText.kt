package com.nexters.travelbudget.utils.widget

import android.content.Context
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.updatePadding
import com.nexters.travelbudget.R
import java.lang.Exception

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
        inputType = InputType.TYPE_CLASS_TEXT

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field.set(this, R.drawable.cursor_trip_edit_text)
            } else {
                setTextCursorDrawable(R.drawable.cursor_trip_edit_text)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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