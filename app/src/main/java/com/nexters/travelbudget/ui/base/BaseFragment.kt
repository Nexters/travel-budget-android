package com.nexters.travelbudget.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nexters.travelbudget.BR
import com.nexters.travelbudget.utils.ext.showToastMessage

/**
 * Fragment Base 클래스
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.15
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutRes: Int) :
    Fragment(layoutRes) {

    protected val binding by lazy {
        DataBindingUtil.bind<B>(requireView())!!
    }

    protected abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.run {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.liveToastMessage.observe(viewLifecycleOwner, Observer { message ->
            context?.showToastMessage(message)
        })
    }
}