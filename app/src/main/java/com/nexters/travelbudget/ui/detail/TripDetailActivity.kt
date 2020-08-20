package com.nexters.travelbudget.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.model.enums.BudgetType
import com.nexters.travelbudget.model.enums.EditModeType
import com.nexters.travelbudget.model.enums.TravelRoomType
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.adapter.DetailVPAdapter
import com.nexters.travelbudget.ui.edit_trip_profile.EditTripProfileActivity
import com.nexters.travelbudget.ui.manage_member.ManageMemberActivity
import com.nexters.travelbudget.ui.record_spend.RecordSpendActivity
import com.nexters.travelbudget.ui.select_date.SelectDateBottomSheetDialog
import com.nexters.travelbudget.ui.statistics.StatisticsActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.ext.convertToViewDate
import com.nexters.travelbudget.utils.getNowDate
import com.nexters.travelbudget.utils.isBetweenDate
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailActivity :
    BaseActivity<ActivityDetailBinding, TripDetailViewModel>(R.layout.activity_detail) {
    override val viewModel: TripDetailViewModel by viewModel()

    private val fragmentManager = supportFragmentManager

    private var modifiesTripRoomInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTabLayout()

        initViewPager()
        observeViewModel()

        viewModel.getTripDetailData(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))

        val startDate = intent.getStringExtra(Constant.EXTRA_PLAN_START_DATE)!!
        val endDate = intent.getStringExtra(Constant.EXTRA_PLAN_END_DATE)!!


        if (getNowDate().isBetweenDate(startDate, endDate)) {
            viewModel.setFocusDate(getNowDate().convertToViewDate())
        }

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constant.RESULT_OK) {
            when (requestCode) {
                Constant.REQUEST_CODE_SPEND_CREATE -> viewModel.getTripDetailData(
                    intent.getLongExtra(
                        Constant.EXTRA_PLAN_ID,
                        -1L
                    )
                )
                Constant.REQUEST_CODE_EDIT_TRIP_PROFILE -> {
                    modifiesTripRoomInfo = true
                    viewModel.getTripDetailData(intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L))
                }
            }

        }
    }

    override fun finish() {
        if (modifiesTripRoomInfo) {
            setResult(Constant.RESULT_OK)
        }
        super.finish()
    }

    private fun checkFocusPosition() {
        with(viewModel) {
            when (binding.tlDetail.selectedTabPosition) {
                0 -> tabFocus.value = BudgetType.SHARED
                else -> tabFocus.value = BudgetType.PERSONAL
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            tripInfoData.observe(this@TripDetailActivity, Observer {
                checkFocusPosition()
            })

            tabFocus.observe(this@TripDetailActivity, Observer {
                updateBudgetDate()

            })

            focusDate.observe(this@TripDetailActivity, Observer {
                updateEachDateSpendList()
            })

            showDateDialogEvent.observe(this@TripDetailActivity, Observer {
                SelectDateBottomSheetDialog.newInstance(
                    focusDate.value!!,
                    ArrayList(tripInfoData.value!!.dates)
                ) {
                    setFocusDate(it)

                }.show(fragmentManager, "bottom_sheet")
            })

            startManageMember.observe(this@TripDetailActivity, Observer {
                val planId = intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L)
                val roomTitle = detailTitle.value ?: ""
                if (planId == -1L) return@Observer
                startActivity(
                    ManageMemberActivity.getIntent(
                        this@TripDetailActivity,
                        planId,
                        roomTitle
                    )
                )
            })

            goToPaymentScreen.observe(this@TripDetailActivity, Observer {
                val tripDetailResponse = tripInfoData.value ?: return@Observer
                val sharedBudgetId = tripDetailResponse.shared?.budgetId ?: -1L
                val personalBudgetId = tripDetailResponse.personal?.budgetId ?: -1L
                val focusDate = focusDate.value!!
                val roomType = TravelRoomType.SHARED
                val editMode = EditModeType.CREATE_MODE
                val focusType = tabFocus.value!!
                startActivityForResult(
                    Intent(
                        this@TripDetailActivity,
                        RecordSpendActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_EDIT_MODE, editMode)
                        putExtra(Constant.EXTRA_CURRENT_DATE, focusDate)
                        putExtra(Constant.EXTRA_FOCUS_TYPE, focusType)
                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(tripDetailResponse.dates)
                        )
                    }, Constant.REQUEST_CODE_SPEND_CREATE
                )
            })


            startRecordSpend.observe(this@TripDetailActivity, Observer {
                val detailDate = tripInfoData.value ?: return@Observer
                startActivity(
                    Intent(
                        this@TripDetailActivity,
                        RecordSpendActivity::class.java
                    ).apply {
                        putExtra(
                            Constant.EXTRA_PERSONAL_BUDGET_ID,
                            detailDate.personal?.budgetId ?: -1L
                        )
                        putExtra(
                            Constant.EXTRA_SHARED_BUDGET_ID,
                            detailDate.shared?.budgetId ?: -1L
                        )
                        putStringArrayListExtra(
                            Constant.EXTRA_PLAN_DATES,
                            ArrayList(detailDate.dates)
                        )
                        putExtra(Constant.EXTRA_ROOM_TYPE, TravelRoomType.SHARED)
                    }
                )
            })
            startEditTripProfile.observe(this@TripDetailActivity, Observer {
                goToEditTripProfileActivity()
            })

            goToPieScreen.observe(this@TripDetailActivity, Observer {
                val detailBudgetId = tripInfoData.value ?: return@Observer

                val sharedBudgetId = detailBudgetId.shared?.budgetId ?: -1L
                val personalBudgetId = detailBudgetId.personal?.budgetId ?: -1L
                val roomType = TravelRoomType.SHARED
                val focusType = tabFocus.value!!

                startActivity(
                    Intent(
                        this@TripDetailActivity,
                        StatisticsActivity::class.java
                    ).apply {
                        putExtra(Constant.EXTRA_SHARED_BUDGET_ID, sharedBudgetId)
                        putExtra(Constant.EXTRA_PERSONAL_BUDGET_ID, personalBudgetId)
                        putExtra(Constant.EXTRA_ROOM_TYPE, roomType)
                        putExtra(Constant.EXTRA_FOCUS_TYPE, focusType)
                    })
            })

        }
    }

    private fun setTabLayout() {
        binding.tlDetail.run {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpDetailPager.currentItem = tab.position

                    checkFocusPosition()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }

            })
        }
    }

    private fun initViewPager() {
        with(binding.vpDetailPager) {
            adapter = DetailVPAdapter(supportFragmentManager)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlDetail))
        }
    }



    fun goToEditTripProfileActivity() {
        val planId = intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1L)
        val memberId = viewModel.tripInfoData.value?.memberId ?: -1L
        startActivityForResult(
            Intent(
                EditTripProfileActivity.getIntent(
                    this@TripDetailActivity,
                    planId, memberId, TravelRoomType.SHARED.name
                )
            ), Constant.REQUEST_CODE_EDIT_TRIP_PROFILE
        )
    }


    companion object {
        private const val TAB_COUNT = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, TripDetailActivity::class.java)
        }
    }

}
