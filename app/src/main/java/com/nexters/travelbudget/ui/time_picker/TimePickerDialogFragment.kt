package com.nexters.travelbudget.ui.time_picker

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialDialogs
import com.nexters.travelbudget.R
import java.util.*

class TimePickerDialogFragment(
    private val listener: (String) -> Unit
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_time_picker, container, false).also {
            val timePicker: TimePicker = it.findViewById(R.id.time_picker)
            timePicker.hour = 15
            timePicker.minute = 15

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                listener("$hourOfDay:$minute")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.setLayout(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                330f,
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                300f,
                resources.displayMetrics
            ).toInt()
        )

    }

}