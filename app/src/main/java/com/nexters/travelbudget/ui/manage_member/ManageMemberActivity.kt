package com.nexters.travelbudget.ui.manage_member

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nexters.travelbudget.R
import com.nexters.travelbudget.data.remote.model.response.TripMemberResponse
import com.nexters.travelbudget.databinding.ActivityManageMemberBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.utils.Constant
import com.nexters.travelbudget.utils.CustomItemDecoration
import com.nexters.travelbudget.utils.ext.showToastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 여행 친구 관리 화면
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.15
 */
class ManageMemberActivity :
    BaseActivity<ActivityManageMemberBinding, ManageMemberViewModel>(R.layout.activity_manage_member),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: ManageMemberViewModel by viewModel {
        parametersOf(
            intent.getLongExtra(Constant.EXTRA_PLAN_ID, -1),
            intent.getStringExtra(Constant.EXTRA_ROOM_TITLE) ?: ""
        )
    }

    private val manageMemberRVAdapter by lazy {
        ManageMemberRVAdapter(
            onClickExportMember = { member ->
                OutMemberNoticeDialog.newInstance(member.nickname, member.memberId) { memberId ->
                    viewModel.deleteTripMember(memberId)
                }.show(supportFragmentManager, "OutMemberNoticeDialog")
            },
            onClickInviteMember = {
                viewModel.createDynamicLink()
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSwipeRefreshLayout()
        setManageMemberRV()
        viewModel.getTripMembers()

        viewModel.tripMemberResponse.observe(this, Observer {
            manageMemberRVAdapter.setItems(it.members, it.myAuthority)
        })

        viewModel.successDeleteMember.observe(this, Observer {
            viewModel.getTripMembers()
        })

        viewModel.failedDeleteMember.observe(this, Observer {
            showToastMessage(getString(R.string.request_fail))
        })

        viewModel.successCreateDeepLink.observe(this, Observer {
            val roomCode = viewModel.tripMemberResponse.value?.inviteCode ?: return@Observer
            val roomTitle = viewModel.roomTitle.value ?: return@Observer
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "'${roomTitle}'에 참여하여 친구와 함께 여행 예산을 관리해보세요!\n${it}\n(방코드 : $roomCode)"
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        })

        viewModel.failedCreateDeepLink.observe(this, Observer {
            showToastMessage(getString(R.string.invite_member_request_fail))
        })

        viewModel.backScreen.observe(this, Observer {
            onBackPressed()
        })
    }

    override fun onRefresh() {
        viewModel.getTripMembers()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(this@ManageMemberActivity)
            setColorSchemeColors(resources.getColor(R.color.fill_grey_1, theme))
        }
    }

    private fun setManageMemberRV() {
        binding.rvMembers.run {
            adapter = manageMemberRVAdapter
            addItemDecoration(object : CustomItemDecoration() {
                override fun setSpacingForDirection(
                    outRect: Rect,
                    layoutManager: RecyclerView.LayoutManager?,
                    position: Int,
                    itemCount: Int
                ) {
                    outRect.top = if (position == 0) {
                        resources.getDimensionPixelSize(R.dimen.spacing_size_28dp)
                    } else {
                        0
                    }
                }

            })
        }
    }

    companion object {
        fun getIntent(context: Context, planId: Long, roomTitle: String): Intent {
            return Intent(context, ManageMemberActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        Constant.EXTRA_PLAN_ID to planId,
                        Constant.EXTRA_ROOM_TITLE to roomTitle
                    )
                )
            }
        }
    }
}