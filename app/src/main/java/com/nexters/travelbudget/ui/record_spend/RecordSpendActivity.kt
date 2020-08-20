package com.nexters.travelbudget.ui.record_spend

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityRecordSpendBinding
import com.nexters.travelbudget.model.enums.BudgetType
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.time_picker.TimePickerDialogFragment
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.MoneyStringTextWatcher
import com.nexters.travelbudget.utils.ext.showToastMessage
import com.nexters.travelbudget.utils.ext.toMoneyString
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
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
        val currentDate = intent.getStringExtra(Constant.EXTRA_CURRENT_DATE)
        val focusType = intent.getSerializableExtra(Constant.EXTRA_FOCUS_TYPE) as BudgetType
        val roomType = intent.getSerializableExtra(Constant.EXTRA_ROOM_TYPE)
        val editMode = intent.getSerializableExtra(Constant.EXTRA_EDIT_MODE)

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
        viewModel.setRoomType(roomType == TravelRoomType.SHARED)
        viewModel.setEditMode(editMode == EditModeType.EDIT_MODE)
        viewModel.setBudgetId(sharedBudgetId, personalBudgetId)
        viewModel.selectShared(focusType == BudgetType.SHARED)


        observeViewModel()
        setupSpendCategoryRV()
        setupTextWatcher()

        if (editMode == EditModeType.EDIT_MODE) {
            setupEditMode()
        }
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
                setResult(Constant.RESULT_OK)
                finish()
            })

            closeEvent.observe(this@RecordSpendActivity, Observer {
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

    private fun setupEditMode() {
        val paymentTitle = intent.getStringExtra(Constant.EXTRA_PAYMENT_TITLE) ?: ""
        val paymentCategory = intent.getStringExtra(Constant.EXTRA_PAYMENT_CATEGORY) ?: ""
        val paymentAmount = intent.getLongExtra(Constant.EXTRA_PAYMENT_AMOUNT, 0).toInt().toMoneyString()
        val paymentId = intent.getLongExtra(Constant.EXTRA_PAYMENT_ID, -1L)
        binding.etSpendAmount.setText(paymentAmount)
        viewModel.setSpendAmount(paymentAmount)
        viewModel.editModeInit(paymentTitle, paymentCategory, paymentId)
    }

    override fun onBackPressed() {
        setResult(Constant.RESULT_CANCEL)
        super.onBackPressed()
    }
}