package com.nexters.travelbudget.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.nexters.travelbudget.utils.ext.toMoneyString

/**
 * EditText에 작성한 숫자를 DecimalFormat으로 변환
 *
 * author: AKMUNNY
 */

class MoneyStringTextWatcher(
    private val et: EditText,
    private val onReplaced: (String) -> Unit
) : TextWatcher {
    private var beforeValue = ""
    override fun afterTextChanged(s: Editable?) = Unit
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.isNullOrEmpty()) {
            beforeValue = ""
            onReplaced("")
            return
        }

        val number = s.toString().replace(",", "").toInt().toMoneyString()
        val bfvalue = beforeValue

        if (beforeValue == s.toString()) {
            return
        }
        beforeValue = number

        var bfComma = 0
        var currComma = 0

        for (i in bfvalue) {
            if (i == ',')
                bfComma++
        }

        for (i in number) {
            if (i == ',')
                currComma++
        }

        val gap = currComma - bfComma

        val currentPosition = et.selectionStart
        et.setText(number)
        onReplaced(number)
        val pos = if (currentPosition + gap < 0) {
            0
        } else {
            currentPosition + gap
        }
        et.setSelection(pos)
    }
}

@BindingAdapter("moneyStringTextWatcher")
fun EditText.bindMoneyStringTextWatcher(onReplaced: (String) -> Unit) {
    addTextChangedListener(MoneyStringTextWatcher(this) {
        onReplaced(it)
    })
}