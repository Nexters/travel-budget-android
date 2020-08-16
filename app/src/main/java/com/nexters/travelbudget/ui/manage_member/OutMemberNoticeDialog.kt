package com.nexters.travelbudget.ui.manage_member

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.DialogOutMemberNoticeBinding
import com.nexters.travelbudget.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 친구 내보내기 클릭시 등장하는 안내문구 다이얼로그
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.16
 */
class OutMemberNoticeDialog(private val onClickDeleteMember: (Long) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogOutMemberNoticeBinding

    private val viewModel: OutMemberNoticeViewModel by viewModel {
        parametersOf(
            arguments?.getString(Constant.EXTRA_USER_NAME, ""),
            arguments?.getLong(Constant.EXTRA_MEMBER_ID, -1L)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_out_member_notice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        viewModel.deleteMember.observe(this, Observer {
            onClickDeleteMember.invoke(it)
        })

        viewModel.dismissDialog.observe(this, Observer {
            dismiss()
        })
    }

    override fun onResume() {
        super.onResume()
        dialog?.apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }?.window?.let { window ->
            val params = window.attributes
            params.width = (Resources.getSystem().displayMetrics.widthPixels * 0.833).toInt()
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes = params
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().let {
            it.add(this, tag)
            it.commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(
            username: String,
            memberId: Long,
            onClickDeleteMember: (Long) -> Unit
        ): OutMemberNoticeDialog {
            return OutMemberNoticeDialog(onClickDeleteMember).apply {
                arguments = bundleOf(
                    Constant.EXTRA_USER_NAME to username,
                    Constant.EXTRA_MEMBER_ID to memberId
                )
            }
        }
    }
}