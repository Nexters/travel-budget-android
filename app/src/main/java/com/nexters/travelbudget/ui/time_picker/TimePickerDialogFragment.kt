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
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialDialogs
import com.nexters.travelbudget.R
import java.io.Serializable
import java.util.*

class TimePickerDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_time_picker, container, false).also {
            val timePicker: TimePicker = it.findViewById(R.id.time_picker)
            val date = arguments?.getString(BUNDLE_DATE) ?: "0000.00.00"
            val st = StringTokenizer(date, ":")
            val h = st.nextToken().toInt()
            val m = st.nextToken().toInt()

            var time = getTimeString(h, m)

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                time = getTimeString(hourOfDay, minute)
            }

            val btnComplete: Button = it.findViewById(R.id.btn_complete)
            btnComplete.setOnClickListener {
                @Suppress("UNCHECKED_CAST")
                val listener = arguments?.get(BUNDLE_LISTENER) as (String) -> Unit
                listener(time)
                dismiss()
            }
        }
    }

    private fun getTimeString(hour: Int, minute: Int): String {
        val h = if (hour < 10) {
            "0$hour"
        } else {
            "$hour"
        }

        val m = if (minute < 10) {
            "0$minute"
        } else {
            "$minute"
        }
        return "$h:$m"
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

    companion object {
        private const val BUNDLE_DATE = "bundle_date"
        private const val BUNDLE_LISTENER = "bundle_listener"

        fun newInstance(date: String, listener: (String) -> Unit): TimePickerDialogFragment {
            return TimePickerDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_DATE, date)
                    putSerializable(BUNDLE_LISTENER, listener as Serializable)
                }
            }
        }
    }
}