package com.nexters.travelbudget.ui.record_spend

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.ActivityRecordSpendBinding
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.TripDetailSharedFragment
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.time_picker.TimePickerDialogFragment
import com.nexters.travelbudget.utils.*
import com.nexters.travelbudget.utils.ext.convertToServerDate
import com.nexters.travelbudget.utils.ext.showToastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round

class RecordSpendActivity : BaseActivity<ActivityRecordSpendBinding, RecordSpendViewModel>(
    R.layout.activity_record_spend
) {
    override val viewModel: RecordSpendViewModel by viewModel()
    private lateinit var dateList: ArrayList<String>

    private var day = ""
    private var time = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val date = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(Date())
        val st = StringTokenizer(date)

        val sharedBudgetId = intent.getLongExtra(Constant.EXTRA_SHARED_BUDGET_ID, -1L)
        val personalBudgetId = intent.getLongExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, -1L)
        val paymentId = intent.getLongExtra(Constant.EXTRA_PAYMENT_ID, -1L)
        val currentDate = intent.getStringExtra(Constant.EXTRA_CURRENT_DATE)

        intent.getStringArrayListExtra(Constant.EXTRA_PLAN_DATES)?.let {
            dateList = it
        }

        day = st.nextToken()
        time = st.nextToken()

        currentDate?.let {
            day = it
        }

        viewModel.setDate(day)
        viewModel.setTime(time)
        viewModel.setRoomType(intent.getSerializableExtra(Constant.EXTRA_ROOM_TYPE) == TravelRoomType.SHARED)
        viewModel.setEditMode(intent.getSerializableExtra(Constant.EXTRA_EDIT_MODE) == EditModeType.EDIT_MODE)
        viewModel.setBudgetId(sharedBudgetId, personalBudgetId)
        viewModel.setPaymentId(paymentId)

        observeViewModel()
        setupSpendCategoryRV()
        setupTextWatcher()
    }

    private fun observeViewModel() {
        with(viewModel) {
            selectDateEvent.observe(this@RecordSpendActivity, Observer {
                SelectDateBottomSheetDialog.newInstance(selectedDate.value ?: "준비", dateList) {
                    setDate(it)
                }.show(supportFragmentManager, "")
            })
            selectTimeEvent.observe(this@RecordSpendActivity, Observer {
                TimePickerDialogFragment.newInstance(time) {
                    setTime(it)
                }.show(supportFragmentManager, "")
            })
            spendExplain.observe(this@RecordSpendActivity, Observer {
                checkComplete()
            })

            spendAmount.observe(this@RecordSpendActivity, Observer {
                checkComplete()
            })

            recordSpendFinishEvent.observe(this@RecordSpendActivity, Observer {
                showToastMessage(it)
                finish()
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
                                16f,
                                resources.displayMetrics
                            )
                        } else {
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                16f,
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