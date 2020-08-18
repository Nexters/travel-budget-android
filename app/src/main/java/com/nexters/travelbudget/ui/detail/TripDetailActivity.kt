package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripDetailResponse
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.DetailVPAdapter
import com.nexters.travelbudget.ui.manage_member.ManageMemberActivity
import com.nexters.travelbudget.ui.record_spend.RecordSpendActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.applySchedulers
import com.nexters.travelbudget.utils.observer.TripDisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class TripDetailActivity :
    BaseActivity<ActivityDetailBinding, TripDetailViewModel>(R.layout.activity_detail) {
    override val viewModel: TripDetailViewModel by viewModel {
        parametersOf(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))
    }
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isPersonal = true
        binding.isPersonal = isPersonal
        setTabLayout()
        observeViewModel()

        viewModel.getTripDetailData()
//        intent.getBundleExtra()
    }

    private fun observeViewModel() {
        with(viewModel) {
            tripDetail.observe(this@TripDetailActivity, Observer {
                setDetailTitle(it.name)
                setupViewPager(it.dates, it.shared, it.personal)
            })

            startManageMember.observe(this@TripDetailActivity, Observer {
                val planId = intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L)
                val roomTitle = viewModel.detailTitle.value ?: ""
                if (planId == -1L) return@Observer
                startActivity(
                    ManageMemberActivity.getIntent(
                        this@TripDetailActivity,
                        planId,
                        roomTitle
                    )
                )
            })
            startRecordSpend.observe(this@TripDetailActivity, Observer {

                val detailDate = tripDetail.value!!
                startActivity(
                    Intent(
                        this@TripDetailActivity,
                        RecordSpendActivity::class.java
                    ).apply {
//                        putExtra(Constant.EXTRA_PLAN_ID, planIdLiveData.value)
                        putExtra(
                            Constant.EXTRA_PERSONAL_BUDGET_ID,
                            detailDate.personal?.budgetId ?: -1L
                        )
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, detailDate.shared.budgetId)
                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(detailDate.dates)
                        )
                        putExtra(Constant.EXTRA_ROOM_TYPE, TravelRoomType.SHARED)
                    })
            })
        }
    }

    private fun setTabLayout() {
        binding.tlDetail.run {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpDetailPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }

            })
        }
    }

    private fun setupViewPager(
        dates: List<String>,
        sharedBudgetData: TripDetailResponse.Data,
        personalBudgetData: TripDetailResponse.Data?
    ) {
        binding.vpDetailPager.run {
            adapter = DetailVPAdapter(
                supportFragmentManager,
                TAB_COUNT,
                dates,
                sharedBudgetData,
                personalBudgetData
            )
            offscreenPageLimit = TAB_COUNT - 1
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlDetail))
        }
    }


    companion object {
        private const val TAB_COUNT = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailActivity::class.java)
        }
    }


}