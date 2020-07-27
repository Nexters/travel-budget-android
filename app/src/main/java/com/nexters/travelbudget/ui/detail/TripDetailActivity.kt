package com.nexters.travelbudget.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailActivity :
    BaseActivity<ActivityDetailBinding, TripDetailViewModel>(R.layout.activity_detail) {
    override val viewModel: TripDetailViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isPersonal = true
        binding.isPersonal = isPersonal


        //앱 시작시 적용할 프래그먼트 설정
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_detail_fragment, TripDetailSharedFragment.newInstance())
        fragmentTransaction.commit()

        //버튼 클릭 이벤트 - 프래그먼트를 교체
        viewModel.toShared.observe(this@TripDetailActivity, Observer {
            supportFragmentManager.beginTransaction().also {
                it.replace(R.id.fl_detail_fragment, TripDetailSharedFragment.newInstance())
                it.commit()
            }
        })

        viewModel.toPersonal.observe(this@TripDetailActivity, Observer {
            supportFragmentManager.beginTransaction().also {
                it.replace(R.id.fl_detail_fragment, TripDetailPersonalFragment.newInstance())
                it.commit()
            }
        })

    }


}