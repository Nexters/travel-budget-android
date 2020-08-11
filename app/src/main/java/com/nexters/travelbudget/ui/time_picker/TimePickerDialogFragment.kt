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
import android.view.Window
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialDialogs
import com.nexters.travelbudget.R
import java.util.*

class TimePickerDialogFragment(
    private val listener: (String) -> Unit
) : DialogFragment() {
    private var time = ""

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
                val h = if (hourOfDay < 10) {
                    "0$hourOfDay"
                } else {
                    "$hourOfDay"
                }

                val m = if (minute < 10) {
                    "0$minute"
                } else {
                    "$minute"
                }
                time = "$h:$m"
            }

            val btnComplete: Button = it.findViewById(R.id.btn_complete)
            btnComplete.setOnClickListener {
                complete()
            }
        }
    }

    fun complete() {
        listener(time)
        dismiss()
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_dialog)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
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
                350f,
                resources.displayMetrics
            ).toInt()
        )
    }
}