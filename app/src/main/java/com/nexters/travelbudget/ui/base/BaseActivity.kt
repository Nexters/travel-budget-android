package com.nexters.travelbudget.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.nexters.travelbudget.BR

/**
 * Activity Base 클래스
 * 모든 하위 Activity 의 DataBinding, ViewModel 설정등을 담당하는 클래스
 *
 * @author YKW93
 * @since v1.0.0 / 2020.07.15
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutRes: Int) :
    AppCompatActivity() {

    protected lateinit var binding: B

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)

        binding.run {
            setVariable(BR.vm, viewModel) // xml 에서 viewModel 설정 시 반드시 vm 명칭 사용!
            lifecycleOwner = this@BaseActivity
        }

        viewModel.liveToastMessage.observe(this, Observer { message ->
            Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
        })
    }
}