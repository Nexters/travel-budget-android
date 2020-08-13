package com.nexters.travelbudget.ui.create_room

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.FragmentRoomPeriodInputBinding
import com.nexters.travelbudget.ui.base.BaseFragment
import com.nexters.travelbudget.utils.getViewDateFormat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

/**
 * 여행 기간 입력 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class RoomPeriodInputFragment :
    BaseFragment<FragmentRoomPeriodInputBinding, CreateRoomViewModel>(R.layout.fragment_room_period_input) {

    override val viewModel: CreateRoomViewModel by sharedViewModel()

    private val picker by lazy {
        MaterialDatePicker.Builder.dateRangePicker().apply {
            val now = Calendar.getInstance()
            setTheme(R.style.MaterialRangeDatePickerStyle)
            setTitleText("")
            setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)

            calendar.set(Calendar.YEAR, currentYear - 1)
            val start = calendar.timeInMillis

            calendar.set(Calendar.YEAR, currentYear + 2)
            val end = calendar.timeInMillis

            setCalendarConstraints(
                CalendarConstraints.Builder().setStart(start).setEnd(end).build()
            )
        }.build().apply {
            addOnPositiveButtonClickListener {
                val startTimeStamp = it.first ?: return@addOnPositiveButtonClickListener
                val endTimeStamp = it.second ?: return@addOnPositiveButtonClickListener
                viewModel.travelStartDate.value = getViewDateFormat().format(Date(startTimeStamp))
                viewModel.travelEndDate.value = getViewDateFormat().format(endTimeStamp)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openCalendar.observe(this, Observer {
            if (!picker.isAdded) {
                picker.show(childFragmentManager, picker.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val imm = (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    companion object {
        fun newInstance(): RoomPeriodInputFragment = RoomPeriodInputFragment()
    }
}