package com.nexters.travelbudget.ui.record_spend

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityRecordSpendBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.record_spend.adapter.SpendCategoryRVAdapter
import com.nexters.travelbudget.utils.CustomItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class RecordSpendActivity : BaseActivity<ActivityRecordSpendBinding, RecordSpendViewModel>(
    R.layout.activity_record_spend
) {
    override val viewModel: RecordSpendViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSpendCategoryRV()
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