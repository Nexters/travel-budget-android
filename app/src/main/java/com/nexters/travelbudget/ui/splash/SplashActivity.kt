package com.nexters.travelbudget.ui.splash

import android.os.Bundle
import android.view.View
import com.nexters.travelbudget.R
import com.nexters.travelbudget.databinding.ActivitySplashBinding
import com.nexters.travelbudget.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 스플래쉬 액티비티
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.30
 */

class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModel()

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 토큰 체크 및 화면 전환
    }
}
