package com.nexters.travelbudget.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityDetailBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    override val viewModel: DetailViewModel by viewModel()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //앱 시작시 적용할 프래그먼트 설정
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_detail_fragment, DetailSharedFragment(DetailViewModel()))
        fragmentTransaction.commit()

        //버튼 클릭 이벤트 - 프래그먼트를 교체
        viewModel.toShared.observe(this@DetailActivity, Observer {
            if (it) {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fl_detail_fragment, DetailSharedFragment(DetailViewModel()))
                fragmentTransaction.commit()
            }
        })

        viewModel.toPersonal.observe(this@DetailActivity, Observer {
            if (it) {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fl_detail_fragment, DetailPersonalFragment(DetailViewModel()))
                fragmentTransaction.commit()
            }
        })


    }


}