package com.nexters.travelbudget.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivityMainBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import com.nexters.travelbudget.ui.detail.TripDetailActivity
import com.nexters.travelbudget.ui.detail.TripDetailAloneActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toTest.observe(this@MainActivity, Observer {
            if (it) {
                startActivity(Intent(applicationContext, TripDetailActivity::class.java))
            }
        })

        viewModel.toTestAlone.observe(this@MainActivity, Observer {
            if (it) {
                startActivity(Intent(applicationContext, TripDetailAloneActivity::class.java))
            }
        })
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
