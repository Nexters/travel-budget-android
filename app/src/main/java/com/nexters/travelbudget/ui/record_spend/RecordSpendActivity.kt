package com.nexters.travelbudget.ui.record_spend

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityRecordSpendBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.time_picker.TimePickerDialogFragment
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.DLog
import com.nexters.travelbudget.utils.MoneyStringTextWatcher
import com.nexters.travelbudget.utils.ext.toMoneyString
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder
import kotlin.math.round

class RecordSpendActivity : BaseActivity<ActivityRecordSpendBinding, RecordSpendViewModel>(
    R.layout.activity_record_spend
) {
    override val viewModel: RecordSpendViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setDate("2020.8.4")
        viewModel.setTime("15:14")
        observeViewModel()
        setupSpendCategoryRV()
        setupTextWatcher()
    }

    private fun observeViewModel() {
        with(viewModel) {
            selectDateEvent.observe(this@RecordSpendActivity, Observer {
                SelectDateBottomSheetDialog {
                    setDate(it)
                }.show(supportFragmentManager, "")
            })
            selectTimeEvent.observe(this@RecordSpendActivity, Observer {
                TimePickerDialogFragment {
                    setTime(it)
                }.show(supportFragmentManager, "")
            })
        }
    }

    private fun setupTextWatcher() {
        with(binding.etSpendAmount) {
            addTextChangedListener(
                MoneyStringTextWatcher(this) {
                    viewModel.setSpendAmount(it)
                }
            )
        }
    }

    private fun setupSpendCategoryRV() {
        with(binding.rvCategory) {
            adapter = SpendCategoryRVAdapter {
                viewModel.categoryItemClick(it)
            }.apply {
                addItemDecoration(object : CustomItemDecoration() {
                    override fun setSpacingForDirection(
                        outRect: Rect,
                        layoutManager: RecyclerView.LayoutManager?,
                        position: Int,
                        itemCount: Int
                    ) {
                        val dp = if (position != itemCount - 1) {
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                20f,
                                resources.displayMetrics
                            )
                        } else {
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                24f,
                                resources.displayMetrics
                            )
                        }
                        outRect.set(0, 0, round(dp).toInt(), 0)
                    }
                })
            }
        }
    }
}