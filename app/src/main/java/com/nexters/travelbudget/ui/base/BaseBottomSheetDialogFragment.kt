package com.nexters.travelbudget.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutRes: Int
) : BottomSheetDialogFragment() {
    protected val binding by lazy {
        DataBindingUtil.bind<B>(requireView())!!
    }
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.run {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@BaseBottomSheetDialogFragment
        }

        viewModel.liveToastMessage.observe(viewLifecycleOwner, Observer { message ->
            context?.let { context ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        with (dialog as BottomSheetDialog) {
            behavior.isHideable = false
        }
    }
}